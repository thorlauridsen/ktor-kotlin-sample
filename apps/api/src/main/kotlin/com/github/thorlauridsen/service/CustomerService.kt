package com.github.thorlauridsen.service

import com.github.thorlauridsen.model.Customer
import com.github.thorlauridsen.model.CustomerInput
import com.github.thorlauridsen.model.ICustomerRepo
import org.slf4j.LoggerFactory
import java.util.UUID

/**
 * This service is responsible for:
 * - Saving customers.
 * - Fetching customers.
 */
class CustomerService(private val customerRepo: ICustomerRepo) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Save a customer.
     * @param customer [CustomerInput] to save.
     * @return [Customer] retrieved from database.
     */
    suspend fun save(customer: CustomerInput): Customer {
        logger.info("Saving customer to database: $customer")

        return customerRepo.save(customer)
    }

    /**
     * Get a customer given an id.
     * @param id [UUID] to fetch customer.
     * @return [Customer] or null if not found.
     */
    suspend fun find(id: UUID): Customer? {
        logger.info("Retrieving customer with id: $id")

        val customer = customerRepo.find(id)

        if (customer != null) {
            logger.info("Found customer with id: ${customer.id} and mail: ${customer.mail}")
        } else {
            logger.info("No customer found with id: $id")
        }
        return customer
    }
}
