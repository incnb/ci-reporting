@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

def http = new HTTPBuilder("http://jenkinsdeploy.wadhamsi.com/job/")
//http.headers['Authorization'] = 'Basic ' + "abcrzw:a093729306175a197da2dc57c59490b9".getBytes('iso-8859-1').encodeBase64()
http.headers['Authorization'] = 'Basic ' + "kdeploy_user:ed70bd63f5292e9848136a2a033b11c5".getBytes('iso-8859-1').encodeBase64()

http.get(path:'architecture.display-parms/config.xml', contentType:ContentType.TEXT) { resp, reader ->
    println "response status: ${resp.statusLine}"
    println 'Response data: ----------------'
    System.out << XmlUtil.serialize(reader.text)
    println '--------------------'
}
