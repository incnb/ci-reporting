@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

def http = new HTTPBuilder("http://jenkinstest.wadhams.com:9091/job/")
http.get(path:'usg.architecture.kdeploy.type1.deploy/api/xml', contentType:ContentType.TEXT) { resp, reader ->
    println '' + resp.class.getName()
    println '' + reader.class.getName()
    println "response status: ${resp.statusLine}"
    println 'Response data: ----------------'
    System.out << XmlUtil.serialize(reader.text)
    println '--------------------'
} 
