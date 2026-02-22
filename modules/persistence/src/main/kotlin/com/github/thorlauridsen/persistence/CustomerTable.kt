package com.github.thorlauridsen.persistence

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

/**
 * Exposed UUID table for customers.
 * @property mail Mail address of customer.
 */
object CustomerTable : UUIDTable("customer") {
    val mail = varchar("mail", 255)
}
