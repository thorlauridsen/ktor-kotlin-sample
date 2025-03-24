package com.github.thorlauridsen.config

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

/**
 * Configures serialization/deserialization for the application.
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}
