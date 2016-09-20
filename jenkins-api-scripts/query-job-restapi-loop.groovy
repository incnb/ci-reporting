@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

25.times {
    def api = new RESTClient()
    def resp = api.get([uri: "http://jenkinstest.wadhamsi.com:9091/job/", path:"abc.architecture.long-running-job/api/xml"])

    def lastBuild = resp.data.lastBuild.number
    def lastCompletedBuild = resp.data.lastCompletedBuild.number
    println "LastBuild: ${lastBuild}, LastCompletedBuild: ${lastCompletedBuild}, ${lastBuild==lastCompletedBuild?'NotRunning':'Running'}"
    sleep(2000)
}
