@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient
import groovyx.net.http.ContentType

def resp
def token = 'qazx'

def build = new RESTClient("http://jenkinsdeploy.wadhamsi.com/job/architecture.test-concurrent/build?token=$token")
5.times {
    resp = build.post([:])
    println "response status: ${resp.statusLine}"
    //sleep 1000
}
