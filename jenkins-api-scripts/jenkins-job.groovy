@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient
import groovy.sql.Sql

def resp
String token = 'qazx'

Sql sql = Sql.newInstance('jdbc:sqlserver://USGOBTSQL800.unitrininc.unitrin.org:1666', 'KDeploy_Admin_User', 'POCforRob#2', 'com.microsoft.sqlserver.jdbc.SQLServerDriver')

//NEW
Date now = new Date()
int delay
sql.eachRow("select * from DEPLOY_REQUEST WHERE STATUS_NM = 'NEW'" ) {row ->
    println "$row.DEPLOY_ID\t$row.ENVIRONMENT_NM\t$row.ACTION_NM\t$row.STATUS_NM\t\t$row.DEPLOY_DT\t$row.JENKINS_DEPLOY_JOB_NM\t$row.REQUESTOR_NM\t$row.ARTIFACT_SHA_CD\t$row.JENKINS_QUEUE_ID"
    Date d = row.DEPLOY_DT
    String job = row.JENKINS_DEPLOY_JOB_NM
    String requestor = URLEncoder.encode(row.REQUESTOR_NM)
    String env = URLEncoder.encode(row.ENVIRONMENT_NM)
    int deployId = row.DEPLOY_ID
    if (d < now) {
        delay = 0
    }
    else {
        delay = (d.getTime() - now.getTime()).intdiv(1000)
    }

    def buildWithParameters = new RESTClient("http://jenkinstest.wadhamsi.com:9091/job/$job/buildWithParameters?token=$token&delay=$delay&REQUESTOR=$requestor&ENV=$env&DEPLOY_ID=$deployId")
    resp = buildWithParameters.post([:])
    println "response status: ${resp.statusLine}"

    if (delay > 0) {
        def headers = resp.getHeaders()
        def locationHeader = headers['Location'].value

        def queueURL = "${locationHeader}api/xml"
        def queue = new RESTClient(queueURL)
        resp = queue.get([:])
        assert resp.status == 200
        int queueId = resp.data.id[0].text().toInteger()
        //set status to 'INFLIGHT' and update queueID
        sql.execute "update DEPLOY_REQUEST set STATUS_NM = 'INFLIGHT', JENKINS_QUEUE_ID = $queueId WHERE DEPLOY_ID = $deployId"
    }
    else {
        //set status to 'INFLIGHT'
        sql.execute "update DEPLOY_REQUEST set STATUS_NM = 'INFLIGHT' WHERE DEPLOY_ID = $deployId"
    }

}

//CANCEL
sql.eachRow("select * from DEPLOY_REQUEST WHERE ACTION_NM = 'CANCEL' AND STATUS_NM = 'INFLIGHT'" ) {row ->
    println "$row.DEPLOY_ID\t$row.ENVIRONMENT_NM\t$row.ACTION_NM\t$row.STATUS_NM\t\t$row.DEPLOY_DT\t$row.JENKINS_DEPLOY_JOB_NM\t$row.REQUESTOR_NM\t$row.ARTIFACT_SHA_CD\t$row.JENKINS_QUEUE_ID"

    def cancelURL = "http://jenkinstest.wadhamsi.com:9091/queue/cancelItem?id=$row.JENKINS_QUEUE_ID"
    println "\n$cancelURL"
    def cancel = new RESTClient(cancelURL)
    resp = cancel.post([:])
    if (resp.status == 302) {
        //set status to 'COMPLETE'
        sql.execute "update DEPLOY_REQUEST set STATUS_NM = 'CANCELLED' WHERE DEPLOY_ID = $row.DEPLOY_ID"
    }
    else {
        println "Investigate this cancel action with code: ${resp.status}"
    }
}