package com.github.thorlauridsen.persistence

import com.github.thorlauridsen.model.Customer
import com.github.thorlauridsen.model.CustomerInput
import com.github.thorlauridsen.model.ICustomerRepo
import java.util.UUID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.slf4j.LoggerFactory

/**
 * Exposed customer repository.
 * This repository is responsible for:
 * - Saving customers to the database.
 * - Fetching customers from the database.
 */
class CustomerRepo : ICustomerRepo {

    private val logger = LoggerFactory.getLogger(CustomerRepo::class.java)

    /**
     * Save a customer to the database.
     * @param customer [CustomerInput] to save.
     * @throws IllegalStateException if the customer is not found in the database after saving.
     * @return [Customer] retrieved from database.
     */
    override suspend fun save(customer: CustomerInput): Customer {
        return newSuspendedTransaction {
            logger.info("Saving customer $customer to database...")
            val id = CustomerTable.insertAndGetId {
                it[mail] = customer.mail
            }
            logger.info("Saved customer with id $id in database")

            findCustomer(id.value) ?: error("Customer not found in database with id $id")
        }
    }

    /**
     * Get a customer from the database given an id.
     * This creates a new transaction to fetch the customer.
     * @param id [UUID] to fetch customer.
     * @return [Customer] or null if not found.
     */
    override suspend fun find(id: UUID): Customer? {
        logger.info("Retrieving customer with id: $id from database...")

        return newSuspendedTransaction { findCustomer(id) }
    }

    /**
     * Find a customer in the database given an id.
     * @param id [UUID] to fetch customer.
     * @return [Customer] or null if not found.
     */
    private fun findCustomer(id: UUID): Customer? {
        val customer = CustomerTable
            .selectAll()
            .where { CustomerTable.id eq id }
            .map { mapToModel(it) }
            .firstOrNull()

        logger.info("Found customer $customer in database")
        return customer
    }

    /**
     * Map ResultRow to a Customer.
     * @param row [ResultRow]
     * @return [Customer]
     */
    private fun mapToModel(row: ResultRow): Customer {
        return Customer(
            id = row[CustomerTable.id].value,
            mail = row[CustomerTable.mail],
        )
    }
}
