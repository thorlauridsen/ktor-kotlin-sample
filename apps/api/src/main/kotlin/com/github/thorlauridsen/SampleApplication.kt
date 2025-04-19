package com.github.thorlauridsen

import com.github.thorlauridsen.config.configureCallLogging
import com.github.thorlauridsen.config.configureCors
import com.github.thorlauridsen.config.configureSerialization
import com.github.thorlauridsen.persistence.CustomerRepo
import com.github.thorlauridsen.persistence.configureDatabase
import com.github.thorlauridsen.routing.configureCustomerRoute
import com.github.thorlauridsen.routing.configureRouting
import com.github.thorlauridsen.service.CustomerService
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

/**
 * Main entry point for the application.
 */
fun main(args: Array<String>) {
    EngineMain.main(args)
}

/**
 * Configures the application module.
 */
fun Application.module(customerService: CustomerService = CustomerService(CustomerRepo())) {
    configureCallLogging()
    configureCors()
    configureCustomerRoute(customerService)
    configureRouting()
    configureSerialization()
    configureDatabase()
}
