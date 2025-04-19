package com.github.thorlauridsen

import com.github.thorlauridsen.dto.CustomerDto
import com.github.thorlauridsen.dto.CustomerInputDto
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID
import kotlin.test.assertEquals

class CustomerRouteTest {

    private val json = Json { 
        ignoreUnknownKeys = true 
        prettyPrint = true
        isLenient = true
    }

    @Test
    fun `get customer - random id - returns not found`() = testApplication {
        environment {
            config = ApplicationConfig("application.yaml")
        }
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
    fun `post customer - get customer - success`(mail: String) = testApplication {
        environment {
            config = ApplicationConfig("application.yaml")
        }

        val customerInput = CustomerInputDto(mail)
        val customerInputJson = json.encodeToString(customerInput)

        val response = client.post("/customers") {
            contentType(ContentType.Application.Json)
            setBody(customerInputJson)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        val responseJson = response.bodyAsText()
        val createdCustomer = json.decodeFromString<CustomerDto>(responseJson)
        assertCustomer(createdCustomer, mail)

        val response2 = client.get("/customers/${createdCustomer.id}")
        assertEquals(HttpStatusCode.OK, response2.status)
        val responseJson2 = response2.bodyAsText()
        val fetchedCustomer = json.decodeFromString<CustomerDto>(responseJson2)
        assertCustomer(fetchedCustomer, mail)
    }

    /**
     * Ensure that the customer is not null and that the id is not null.
     * Assert that the mail is equal to the expected mail.
     * @param customer [CustomerDto]
     * @param expectedMail Expected mail of the customer.
     */
    private fun assertCustomer(customer: CustomerDto, expectedMail: String) {
        Assertions.assertNotNull(customer)
        Assertions.assertNotNull(customer.id)
        Assertions.assertEquals(expectedMail, customer.mail)
    }
}
