package com.km.routes

import com.km.repository.CounterRepository
import com.km.service.CounterService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.*

private val counterService = CounterService(CounterRepository())
fun Route.countersRoute() {
    route("/counters") {
        post {
            val name = call.parameters["name"] ?: throw IllegalArgumentException("Invalid name")
            val value = call.parameters["value"]?.toLong() ?: throw IllegalArgumentException("Invalid value")
            counterService.create(name, value)
            call.respond(HttpStatusCode.Created)
        }

        get("/{name}") {
            val name = call.parameters["name"] ?: throw IllegalArgumentException("Invalid ID")
            val counterValue = counterService.read(name)
            if (counterValue.isNotEmpty()) {
                call.respond(HttpStatusCode.OK, counterValue)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get {
            call.respond(HttpStatusCode.OK, counterService.getAll())
        }

        patch("/increment") {
            val name = call.parameters["name"] ?: throw IllegalArgumentException("Invalid name")
            call.respond(HttpStatusCode.OK, counterService.increment(name))
        }

        delete("/{name}") {
            val name = call.parameters["name"] ?: throw IllegalArgumentException("Invalid ID")
            counterService.delete(name)
            call.respond(HttpStatusCode.OK)
        }
    }
}