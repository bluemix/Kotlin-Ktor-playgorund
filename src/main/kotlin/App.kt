

import org.jetbrains.ktor.application.ApplicationCallPipeline
import org.jetbrains.ktor.application.call
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.Locations
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.logging.CallLogging
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.route
import org.jetbrains.ktor.routing.routing
import org.json.simple.JSONObject

/**
 * Created by bluemix on 4/14/17.
 */

@location("/")
class Index

@location("/json")
class JSON


fun main(args: Array<String>) {


  val server = embeddedServer(Netty, 4400) {
    println("Server started at port 4400")
    install(DefaultHeaders)
    install(CallLogging)
    install(Locations)

    intercept(ApplicationCallPipeline.Call) {

//      println("call.response.headers: " + call.response.headers.allValues().names().map { it })
//      println("uri: " + call.request.uri)
    }


    routing {

      route("", {

      })
      get<Index> {
        call.respondText("Hello, world!", ContentType.Text.Html)
//        call.respondText("{\"name\": \"ahmed\"}", ContentType.Text.Html)
      }

      get("/q/{query}/s/{sort?}") {
//      get<JSON> {


        println("keys: " + call.attributes.allKeys.map { "key: $it" })
        println("params: " + call.parameters.names().map { "param: $it" })

        val query = call.parameters["query"]
        val sort: String? = call.parameters["sort"]
////
        println("query: $query, sort: $sort")



        call.respondText(buildJson(), ContentType.Application.Json)
//        call.respondText("{\"name\": \"ali\"}", ContentType.Application.Json)
//        call.respondText("{\"name\": \"ahmed\"}", ContentType.Text.Html)
      }

    }
  }

  server.start(wait = true)

}

private fun buildJson(): String {
  return JSONObject().apply {
    put("name", "ahmed")
    put("age", "27")
    put("attitude", "loyal")
  }.toJSONString()
}