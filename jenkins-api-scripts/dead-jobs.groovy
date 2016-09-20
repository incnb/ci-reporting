@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

def api = new RESTClient("http://jenkins.wadhamsi.com:9091/")
def resp1
def resp2

resp1 = api.get(path: 'api/xml')
assert resp1.status == 200
assert resp1.contentType == 'application/xml'

println 'starting...'
println 'number of jobs: ' + resp1.data.job.size()

File f = new File('bad-build-number-jobs.txt')

f.withPrintWriter {pw ->
    resp1.data.job.each {job ->
        //println job.name
        resp2 = api.get(path: "job/${job.name}/api/xml")
        int nextBuildNumber = resp2.data.nextBuildNumber.text() as int
        if (nextBuildNumber > 1) {
            int lastBuildNumber = resp2.data.lastBuild.number.text() as int
            if (lastBuildNumber+1 != nextBuildNumber) {
                pw.println "${job.name} ${resp2.data.lastBuild.number}  ${resp2.data.nextBuildNumber}"
            }
        }
        //println "${job.name} ${resp2.data.lastBuild.number}  ${resp2.data.nextBuildNumber}"
    }
}

println 'finished'
