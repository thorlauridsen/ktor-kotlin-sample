package com.github.thorlauridsen.dto

import com.github.thorlauridsen.serializer.UUIDSerializer
import com.github.thorlauridsen.model.Customer
import java.util.UUID
import kotlinx.serialization.Serializable

/**
 * Data transfer object representing a customer.
 * @param id [UUID] of customer.
 * @param mail Mail address of customer.
 */
@Serializable
data class CustomerDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val mail: String,
)

/**
 * Convert a [Customer] to [CustomerDto].
 * @return [CustomerDto]
 */
fun Customer.toDto(): CustomerDto {
    return CustomerDto(
        id = id,
        mail = mail
    )
}
