@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

def resp
def buildWithParameters = new RESTClient("http://jenkinsdeploy.wadhamsi.com/job/architecture.display-parms/buildWithParameters?REQUESTOR=Rob&KDEPLOY_REQUEST=KDeploy-9999&ENV=INT&SHA=6249bee1fac394035f4010cb03a865baa8f25a3c")
buildWithParameters.headers['Authorization'] = 'Basic ' + "KSG_kdeploy_RestAPI:9bd97def28d3e0ddfe89b38d510ffcb8".getBytes('iso-8859-1').encodeBase64()
//buildWithParameters.headers['Authorization'] = 'Basic ' + "abcrzw:a093729306175a197da2dc57c59490b9".getBytes('iso-8859-1').encodeBase64()
//buildWithParameters.headers['Authorization'] = 'Basic ' + "abcjjj:bbd77f1cfe629ad7741967d971d5bd50".getBytes('iso-8859-1').encodeBase64()
try {
    resp = buildWithParameters.post([:])
    println "response status: ${resp.statusLine}"
}
catch (Exception e) {
    println e.getStatusCode()
}
