package com.github.thorlauridsen.model

/**
 * Model data class used to create a new customer.
 * @param mail Mail address of customer.
 */
data class CustomerInput(
    val mail: String,
)
