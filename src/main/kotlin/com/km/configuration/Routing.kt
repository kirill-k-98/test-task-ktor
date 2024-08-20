package com.km.configuration

import com.km.routes.countersRoute
import io.ktor.server.application.Application
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        route("/api") {
            countersRoute()
        }
    }
}
