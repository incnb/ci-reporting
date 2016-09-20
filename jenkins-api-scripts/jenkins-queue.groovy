@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.RESTClient

def resp

def queue = new RESTClient("http://jenkinstest.wadhamsi.com:9091/queue/api/xml")
resp = queue.get([:])
println "response status: ${resp.statusLine}"
println ''

def items = resp.data.item
for (def item in items) {
    println "${item.id}: ${item.task.name} - ${item.why}"
}