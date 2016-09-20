@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

def resp
def headers

def job = 'http://jenkinstest.wadhamsi.com:9091/job/abc.architecture.kdeploy.type1.deploy'
def token = 'qazx'
def delay = (5*60).toString()    //5 minutes
def buildURL = "$job/build?token=$token&delay=$delay"
println "\n$buildURL"

def build = new RESTClient(buildURL)
resp = build.post([:])
assert resp.status == 201
println "response status: ${resp.statusLine}"

headers = resp.getHeaders()
headers.each {
    println "${it.name} : ${it.value}"
}
def locationHeader = headers['Location'].value

def queueURL = "${locationHeader}api/xml"
println "\n$queueURL"
def queue = new RESTClient(queueURL)
resp = queue.get([:])
assert resp.status == 200
int queueId = resp.data.id[0].text().toInteger()
println 'queue id: ' + queueId

sleep(15*1000L)    //give you time to see the build in the queue

def cancelURL = "http://jenkinstest.wadhamsi.com:9091/queue/cancelItem?id=$queueId"
println "\n$cancelURL"
def cancel = new RESTClient(cancelURL)
resp = cancel.post([:])
assert resp.status == 302