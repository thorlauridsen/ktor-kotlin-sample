package com.github.thorlauridsen

import liquibase.Liquibase
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.jetbrains.exposed.sql.Database
import java.sql.DriverManager

/**
 * TestDatabase is a singleton object that provides a connection to an in-memory H2 database
 * for testing purposes. It runs Liquibase migrations to set up the database schema.
 */
object TestDatabase {

    /**
     * Connect to the in-memory H2 database and run the Liquibase migrations.
     * This is used for testing purposes only.
     */
    fun connect() {
        val jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
        val username = "sa"
        val password = ""
        val liquibaseChangelog = "db/changelog/db.changelog-master.yaml"

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
}
