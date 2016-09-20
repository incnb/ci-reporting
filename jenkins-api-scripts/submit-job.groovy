@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient
import groovyx.net.http.ContentType

def resp
def token = 'qazx'

def build = new RESTClient("http://jenkinstest.wadhamsi.com:9091/job/abc.architecture.kdeploy.type1.deploy/build?token=$token")
resp = build.post([:])
println "response status: ${resp.statusLine}"
 