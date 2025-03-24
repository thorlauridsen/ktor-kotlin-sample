package com.github.thorlauridsen.model

import java.util.UUID

/**
 * Customer repository interface.
 * This is an interface containing methods for interacting with the customer table.
 * A repository class will implement this interface to provide the actual implementation.
 * This interface makes it easier to swap out the implementation of the repository if needed.
 */
interface ICustomerRepo {

    /**
     * Save a customer to the database.
     * @param customer [CustomerInput] to save.
     * @return [Customer] retrieved from database.
     */
    fun save(customer: CustomerInput): Customer

    /**
     * Get a customer given an id.
     * @param id [UUID] to fetch customer.
     * @return [Customer] or null if not found.
     */
    fun find(id: UUID): Customer?
}
