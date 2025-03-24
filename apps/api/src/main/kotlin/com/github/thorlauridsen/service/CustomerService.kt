package com.github.thorlauridsen.service

import com.github.thorlauridsen.model.Customer
import com.github.thorlauridsen.model.CustomerInput
import com.github.thorlauridsen.persistence.CustomerRepo
import org.slf4j.LoggerFactory
import java.util.UUID

/**
 * This service is responsible for:
 * - Saving customers.
 * - Fetching customers.
 */
class CustomerService {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val customerRepo = CustomerRepo()

    /**
     * Save a customer.
     * @param customer [CustomerInput] to save.
     * @return [Customer] retrieved from database.
     */
    fun save(customer: CustomerInput): Customer {
        logger.info("Saving customer to database: $customer")

        return customerRepo.save(customer)
    }

    /**
     * Get a customer given an id.
     * @param id [UUID] to fetch customer.
     * @throws IllegalStateException if no customer found with given id.
     * @return [Customer] or null if not found.
     */
    fun find(id: UUID): Customer {
        logger.info("Retrieving customer with id: $id")

        val customer = customerRepo.find(id)
            ?: error("Could not find customer with id: $id")

        logger.info("Found customer: $customer")
        return customer
    }
}
