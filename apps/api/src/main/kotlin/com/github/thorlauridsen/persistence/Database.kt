package com.github.thorlauridsen.persistence

import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationEnvironment
import java.sql.DriverManager
import liquibase.Liquibase
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.jetbrains.exposed.sql.Database

/**
 * Configure the database.
 *
 * Read the database configuration from the application.yaml file.
 * Run the Liquibase migrations and connect Exposed to the database.
 */
fun Application.configureDatabase() {

    val jdbcUrl = environment.getProperty("app.database.url")
    val username = environment.getProperty("app.database.username")
    val password = environment.getProperty("app.database.password")
    val liquibaseChangelog = environment.getProperty("app.liquibase.changelog")

    val connection = DriverManager.getConnection(jdbcUrl, username, password)

    val liquibase = Liquibase(
        liquibaseChangelog,
        ClassLoaderResourceAccessor(),
        JdbcConnection(connection)
    )
    liquibase.update("")

    Database.connect(
        url = jdbcUrl,
        user = username,
        password = password
    )
}

/**
 * Extension function to get a property from the application environment.
 *
 * @param propertyName The name of the property to get.
 * @throws IllegalStateException If the property is missing in the application.yaml file.
 * @return The value of the property.
 */
private fun ApplicationEnvironment.getProperty(propertyName: String): String {
    return config.propertyOrNull(propertyName)?.getString()
        ?: error("$propertyName is missing in application.yaml")
}
