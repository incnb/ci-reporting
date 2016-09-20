@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

String jobName = 'abc.architecture.display-parms'
String bwp = 'buildWithParameters?REQUESTOR=Rob&KDEPLOY_REQUEST=KDeploy-9999&ENV=INT&SHA=6249bee1fac394035f4010cb03a865baa8f25a3c'
String uri = "http://jenkinstest.wadhamsi.com:9091/job/$jobName/$bwp"
println uri

def resp
//Doesn't work: def buildWithParameters = new RESTClient('http://jenkinstest.wadhamsi.com:9091/job')
def buildWithParameters = new RESTClient(uri)
buildWithParameters.headers['Authorization'] = 'Basic ' + "abcrzw:8f3591a29ec54847892a525cff26c917".getBytes('iso-8859-1').encodeBase64()

try {
    //Doesn't work: resp = buildWithParameters.post([path:"/$jobName/$bwp"])
    resp = buildWithParameters.post([:])
    println "response status: ${resp.statusLine}"
}
catch (Exception e) {
    println e.getStatusCode()
}