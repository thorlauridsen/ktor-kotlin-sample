package com.github.thorlauridsen.routing

import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.openApi
import io.github.smiley4.ktorswaggerui.swaggerUI
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.Resources
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

/**
 * Configure general routing settings for the application.
 * This includes the OpenAPI and Swagger UI routes.
 */
fun Application.configureRouting() {
    install(Resources)
    install(OpenApi)
    routing {
        route("api.json") {
            openApi()
        }
        route("swagger") {
            swaggerUI("/api.json")
        }
    }
}
