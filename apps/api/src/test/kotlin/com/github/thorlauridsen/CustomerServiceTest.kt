package com.github.thorlauridsen

import com.github.thorlauridsen.model.Customer
import com.github.thorlauridsen.model.CustomerInput
import com.github.thorlauridsen.persistence.CustomerRepo
import com.github.thorlauridsen.service.CustomerService
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import java.util.UUID
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 * Test class for testing the [CustomerService].
 */
class CustomerServiceTest {

    private val customerService = CustomerService(CustomerRepo())

    @BeforeEach
    fun setup() {
        TestDatabase.connect()
    }

    @Test
    fun `get customer - random id - returns not found`() {
        runTest {
            val id = UUID.randomUUID()
            val fetchedCustomer = customerService.find(id)
            assertNull(fetchedCustomer)
        }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "alice@gmail.com",
            "bob@gmail.com",
        ]
    )
    fun `save customer - get customer - success`(mail: String) {
        runTest {
            val customer = CustomerInput(mail)

            val savedCustomer = customerService.save(customer)
            assertCustomer(savedCustomer, mail)

            val fetchedCustomer = customerService.find(savedCustomer.id)
            assertNotNull(fetchedCustomer)
            assertCustomer(fetchedCustomer!!, mail)
        }
    }

    /**
     * Ensure that the customer is not null and that the id is not null.
     * Assert that the mail is equal to the expected mail.
     * @param customer [Customer]
     * @param expectedMail Expected mail of the customer.
     */
    private fun assertCustomer(customer: Customer, expectedMail: String) {
        assertNotNull(customer)
        assertNotNull(customer.id)
        assertEquals(expectedMail, customer.mail)
    }
}
