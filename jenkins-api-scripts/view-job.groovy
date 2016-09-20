@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

def api = new RESTClient("http://jenkinstest.wadhamsi.com:9091/job/")
def resp

resp = api.get(path:'abc.architecture.kdeploy.type1.deploy/api/xml')
assert resp.status == 200
assert resp.contentType == 'application/xml'

println 'last build number: ' + resp.data.lastBuild.number
