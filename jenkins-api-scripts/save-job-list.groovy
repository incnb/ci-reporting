@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

def api = new RESTClient("http://jenkins.wadhamsi.com:9091/")
def resp

resp = api.get(path:'api/xml')
assert resp.status == 200
assert resp.contentType == 'application/xml'

println 'desc: ' + resp.data.nodeDescription
def jobs = resp.data.job

def f = new File('c:/temp/jenkins-job-list.txt')
f.withPrintWriter {pw ->
    jobs.each {j ->
        pw.println j.name
    }
    pw.println ''
}
