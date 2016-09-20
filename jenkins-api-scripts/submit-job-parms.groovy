@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

def resp
def token = 'qazx'
def requestor = 'Rob'

def buildWithParameters = new RESTClient("http://jenkinstest.wadhamsi.com:9091/job/abc.architecture.kdeploy.type2.deploy/buildWithParameters?token=$token&REQUESTOR=$requestor")
resp = buildWithParameters.post([:])
println "response status: ${resp.statusLine}"
 