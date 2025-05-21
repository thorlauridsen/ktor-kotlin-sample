package com.github.thorlauridsen

import com.github.thorlauridsen.dto.CustomerDto
import com.github.thorlauridsen.dto.CustomerInputDto
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.TestApplication
import java.util.UUID
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CustomerRouteTest {

    @Test
    fun `get customer - random id - returns not found`() = runTest {
        val nonExistentId = UUID.randomUUID()
        val response = client.get("/customers/$nonExistentId")

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "alice@gmail.com",
            "bob@gmail.com",
        ]
    )
    fun `post customer - get customer - success`(mail: String) = runTest {
        val customerInput = CustomerInputDto(mail)

        val response = client.post("/customers") {
            contentType(ContentType.Application.Json)
            setBody(customerInput)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        val createdCustomer = response.body<CustomerDto>()
        assertCustomer(createdCustomer, mail)

        val response2 = client.get("/customers/${createdCustomer.id}")
        assertEquals(HttpStatusCode.OK, response2.status)

        val fetchedCustomer = response2.body<CustomerDto>()
        assertCustomer(fetchedCustomer, mail)
    }

    /**
     * Ensure that the customer is not null and that the id is not null.
     * Assert that the mail is equal to the expected mail.
     * @param customer [CustomerDto]
     * @param expectedMail Expected mail of the customer.
     */
    private fun assertCustomer(customer: CustomerDto, expectedMail: String) {
        assertNotNull(customer)
        assertNotNull(customer.id)
        assertEquals(expectedMail, customer.mail)
    }

    companion object {

        private val testApplication = TestApplication {
            environment {
                config = ApplicationConfig("application.yaml")
            }
        }

        val client = testApplication.createClient {
            install(ContentNegotiation) { json() }
        }

        @AfterAll
        @JvmStatic
        fun close() = runTest {
            testApplication.stop()
        }
    }
}
