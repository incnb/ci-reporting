@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

def resp
def requestor = 'Rob'

def buildWithParameters = new RESTClient("http://jenkinstest.wadhamsi.com:9091/job/abc.architecture.kdeploy.type7.deploy/buildWithParameters?REQUESTOR=$requestor")
resp = buildWithParameters.post([:])
println "response status: ${resp.statusLine}"
 