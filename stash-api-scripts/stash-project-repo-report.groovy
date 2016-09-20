@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')

import groovyx.net.http.RESTClient

println "${this.class.simpleName} started..."
println ''

String encoded = 'usgrzw_rest:Stash35'.bytes.encodeBase64().toString()
String auth = "Basic $encoded"

File report = new File('stash-project-repo-report.txt')    //relative to where groovy is running.
report.withPrintWriter {pw ->
    def stash = new RESTClient('http://stash.wadhams.com:7990/rest/api/1.0/')
    def resp = stash.get(path:'projects', query:[limit:'100'], headers:[Authorization:auth])
    println "http response status: $resp.status - projects"
    println ''
    def projects = resp.data.values
    println "Number of Stash projects: ${projects.size()}"
    projects.each {p ->
        pw.println p.name
        resp = stash.get(path:"projects/${p.key}/repos", query:[limit:'100'], headers:[Authorization:auth])
        println "http response status: $resp.status - $p.name"
        def repos = resp.data.values
        repos.each {r ->
            pw.println "\t${r.name}"
        }
    }
}
println ''
println "${this.class.simpleName} ended..."