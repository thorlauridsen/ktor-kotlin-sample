package com.github.thorlauridsen

import com.github.thorlauridsen.model.CustomerInput
import com.github.thorlauridsen.persistence.CustomerRepo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID

/**
 * Test class for testing the [CustomerRepo].
 */
class CustomerRepoTest {

    private val customerRepo = CustomerRepo()

    @BeforeEach
    fun setup() {
        TestDatabase.connect()
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "alice@gmail.com",
            "bob@gmail.com",
        ]
    )
    fun `save customer - get customer - success`(mail: String) = runTest {
        val customer = CustomerInput(mail)

        val savedCustomer = customerRepo.save(customer)

        assertNotNull(savedCustomer)
        assertNotNull(savedCustomer.id)
        assertEquals(mail, savedCustomer.mail)

        val foundCustomer = customerRepo.find(savedCustomer.id)

        assertNotNull(foundCustomer)
        assertEquals(savedCustomer.id, foundCustomer?.id)
        assertEquals(mail, foundCustomer?.mail)
    }

    @Test
    fun `get customer - non-existent id - returns null`() = runTest {
        val id = UUID.randomUUID()
        val customer = customerRepo.find(id)
        assertNull(customer)
    }
}
