package com.github.thorlauridsen.routing

import com.github.thorlauridsen.dto.CustomerDto
import com.github.thorlauridsen.dto.CustomerInputDto
import com.github.thorlauridsen.dto.toDto
import com.github.thorlauridsen.service.CustomerService
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import java.util.UUID

/**
 * Configures the customer route for the application.
 * This route allows for saving and retrieving customers.
 *
 * @param customerService [CustomerService] to use for customer operations.
 */
fun Application.configureCustomerRoute(customerService: CustomerService) {
    routing {
        route("/customers") {
            post<CustomerInputDto>({
                description = "Save a customer"
                request {
                    body<CustomerInputDto> {
                        description = "Customer successfully created"
                        required = true
                        example("default") {
                            value = CustomerInputDto("bob@gmail.com")
                        }
                    }
                }
            }) { customer ->
                val saved = customerService.save(customer.toModel())
                call.respond(HttpStatusCode.Created, saved.toDto())
            }
            get("/{id}", {
                request {
                    pathParameter<String>("id") {
                        description = "UUID of the customer to retrieve"
                        example("default") {
                            value = UUID.randomUUID().toString()
                        }
                    }
                }
                description = "Retrieve a customer given an id"
                response {
                    HttpStatusCode.OK to {
                        description = "Successfully retrieved customer"
                        body<CustomerDto> {
                            example("default") {
                                value = CustomerDto(UUID.randomUUID(), "bob@gmail.com")
                            }
                        }
                    }
                }
            }) {
                val id = UUID.fromString(call.parameters["id"])
                val found = customerService.find(id)
                if (found == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(HttpStatusCode.OK, found.toDto())
            }
        }
    }
}
