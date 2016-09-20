@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')

import groovyx.net.http.HTTPBuilder
//import groovyx.net.http.HttpResponseDecorator
//import groovyx.net.http.RESTClient
//import groovyx.net.http.ContentType
import groovy.json.JsonOutput
//import org.apache.http.HttpResponse
//import org.apache.http.HttpEntity
import org.apache.http.util.EntityUtils

println "${this.class.simpleName} started..."
println ''

String encoded = 'usgrzw_rest:Stash35'.bytes.encodeBase64().toString()
String auth = "Basic $encoded"

File report = new File('stash-project-json.txt')    //relative to where groovy is running.

def http = new HTTPBuilder('http://stash.wadhams.com:7990/rest/api/1.0/')
http.handler.success = {resp ->
    println "Success: ${resp.statusLine}"
    report << JsonOutput.prettyPrint(EntityUtils.toString(resp.entity))
} 
http.get(path:'projects', query:[limit:'100'], headers:[Authorization:auth])

println ''
println "${this.class.simpleName} ended..."