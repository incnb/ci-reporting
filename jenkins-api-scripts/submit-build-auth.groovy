@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovy.xml.XmlUtil

def http = new HTTPBuilder("http://jenkins.wadhamsi.com:9091/job/")
http.headers['Authorization'] = 'Basic ' + "abcrzw:TOKEN".getBytes('iso-8859-1').encodeBase64()

File f = new File('artifact-jobs.txt')
f.eachLine {jobName ->
    println "$jobName"
    http.post(path:"$jobName/build", contentType:ContentType.TEXT) { resp, reader ->
        println "response status: ${resp.statusLine}"
    }
}



