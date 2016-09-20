@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

api = new RESTClient("http://jenkinstest.wadhamsi.com:9091/job/")
def resp

def jobs = []
//success
jobs << 'abc.architecture.kdeploy.type2.deploy'
//used to test a job that is building
jobs << 'abc.architecture.kdeploy.long.running'
//failed
jobs << 'abc.architecture.simplearch.dev1.deploy'
//unstable, but complete
jobs << 'dropwizard.sonar'
//not built
jobs << 'abc.architecture.kdeploy.type9b.deploy'
assert jobs.size() == 5

jobs.each {job ->
    resp = api.get(path:"$job/api/xml")
    assert resp.status == 200
    displayLatestBuild(job, resp)
}
println ''

def displayLatestBuild(job, resp) {
    println "$job"
    assert resp.contentType == 'application/xml'
    println 'last            build number: ' + resp.data.lastBuild.number
    println 'last completed  build number: ' + resp.data.lastCompletedBuild.number
    println 'last stable     build number: ' + resp.data.lastStableBuild.number
    println 'last successful build number: ' + resp.data.lastSuccessfulBuild.number
    
    if (!resp.data.lastCompletedBuild.number.isEmpty()) {
        String uri = "$job/${resp.data.lastCompletedBuild.number}/api/xml"
        println uri
        resp = api.get(path:"$uri")
        assert resp.status == 200
        
        println 'build number: ' + resp.data.number
        println 'build result: ' + resp.data.result
        def parms = resp.data.action[0].parameter
        def buildSHA = parms.find {it.name == 'SHA'}.value
        println buildSHA.isEmpty()?'buildSHA: n/a':"buildSHA: $buildSHA"
        parms.each {
            println "${it.name} = ${it.value}"
        }
        
    }
    println '----------------------------------------------------------------------------'
}
