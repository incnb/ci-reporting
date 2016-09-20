@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

def http = new HTTPBuilder("http://jenkinstest.wadhamsi.com:9091/job/")
http.headers['Authorization'] = 'Basic ' + "abcrzw:8f3591a29ec54847892a525cff26c917".getBytes('iso-8859-1').encodeBase64()

/*
http.get(path:'abc.architecture.kdeploy.type2.deploy/config.xml', contentType:ContentType.TEXT) { resp, reader ->
    println '' + resp.class.getName()
    println '' + reader.class.getName()
    println "response status: ${resp.statusLine}"
    println 'Response data: ----------------'
    System.out << XmlUtil.serialize(reader.text)
    println '--------------------'
}
*/

String xml = http.get(path:'abc.architecture.kdeploy.type2.deploy/config.xml', contentType:ContentType.TEXT) { resp, reader ->
    println "response status: ${resp.statusLine}"
    File f = new File('c:/temp/kdeploy-jenkins/kdeploy-type2-deploy-config.xml')
    def os = f.newOutputStream()
    XmlUtil.serialize(reader.text, os)
    os.close()
}
