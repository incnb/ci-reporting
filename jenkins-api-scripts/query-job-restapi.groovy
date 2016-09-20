@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

try {
    def resp
    def api = new RESTClient()
    resp = api.get([uri: "http://jenkinstest.wadhamsi.com:9091/job/", path:"abc.architecture.long-running-job/api/xml"])

    def lastBuild = resp.data.lastBuild.number
    def lastCompletedBuild = resp.data.lastCompletedBuild.number
    println "LastBuild: ${lastBuild}, LastCompletedBuild: ${lastCompletedBuild}"
    if (lastBuild == lastCompletedBuild) {
        println 'JobExecution.NotRunning'
    }
    else {
        println 'JobExecution.Running'
    }
}
catch (Throwable t) {
    t.printStackTrace(System.out)
}