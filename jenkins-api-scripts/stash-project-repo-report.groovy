@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')

import groovyx.net.http.RESTClient

println "${this.class.simpleName} started..."
println ''

String encoded = 'abcrzw_rest:Stash35'.bytes.encodeBase64().toString()
String auth = "Basic $encoded"

File report = new File('c:/temp/stash-projects-repos.txt')
report.withPrintWriter {pw ->
    def stash = new RESTClient('http://stash.wadhamsi.com:7990/rest/api/1.0/')
    def resp = stash.get(path:'projects', query:[limit:'100'], headers:[Authorization:auth])
    println resp.status
    def projects = resp.data.values
    println projects.size()
    projects.each {p ->
        pw.println p.name
        resp = stash.get(path:"projects/${p.key}/repos", query:[limit:'100'], headers:[Authorization:auth])
        println resp.status
        def repos = resp.data.values
        repos.each {r ->
            pw.println "\t${r.name}"
        }
    }
}
println ''
println "${this.class.simpleName} ended..."