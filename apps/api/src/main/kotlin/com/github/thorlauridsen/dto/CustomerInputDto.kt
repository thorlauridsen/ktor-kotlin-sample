package com.github.thorlauridsen.dto

import com.github.thorlauridsen.model.CustomerInput
import kotlinx.serialization.Serializable

/**
 * Data transfer object used to create a new customer.
 * @param mail Mail address of customer.
 */
@Serializable
data class CustomerInputDto(
    val mail: String,
) {

    /**
     * Convert a [CustomerInputDto] to [CustomerInput].
     * @return [CustomerInput]
     */
    fun toModel(): CustomerInput {
        return CustomerInput(
            mail = mail
        )
    }
}
