package com.github.thorlauridsen.model

import java.util.UUID

/**
 * Model data class representing a customer.
 * @param id [UUID] of customer.
 * @param mail Mail address of customer.
 */
data class Customer(
    val id: UUID,
    val mail: String,
)
