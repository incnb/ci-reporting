@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

def job = 'http://jenkinstest.wadhamsi.com:9091/job/abc.architecture.kdeploy.type1.deploy'
def token = 'qazx'
def delay = (5*60).toString()    //5 minutes
def buildURL = "$job/build?token=$token&delay=$delay"
def locationHeader

println buildURL
def api = new RESTClient(buildURL)
api.post([:]) { resp ->
    println "response status: ${resp.statusLine}"

    def headers = resp.getHeaders()
    headers.each {
        println "${it.name} : ${it.value}"
    }
    locationHeader = headers['Location'].value
}

println '\n'
def queueURL = "${locationHeader}api/xml"
println queueURL
def http = new HTTPBuilder(queueURL)
http.get(contentType:ContentType.TEXT) { resp, reader ->
    println "response status: ${resp.statusLine}"
    println 'Response data: ----------------'
    System.out << XmlUtil.serialize(reader.text)
    println '--------------------'
} 
