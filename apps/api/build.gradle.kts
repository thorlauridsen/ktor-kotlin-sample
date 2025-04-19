plugins {
    alias(local.plugins.ktor)
    alias(local.plugins.kotlin.plugin.serialization)
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.model)
    implementation(projects.persistence)

    // Ktor dependencies
    implementation(local.ktor.serialization.kotlinx.json)
    implementation(local.ktor.server.call.logging)
    implementation(local.ktor.server.config.yaml)
    implementation(local.ktor.server.content.negotiation)
    implementation(local.ktor.server.core)
    implementation(local.ktor.server.cors)
    implementation(local.ktor.server.netty)
    implementation(local.ktor.server.request.validation)
    implementation(local.ktor.server.resources)
    implementation(local.logback.classic)

    // Exposed dependencies for interacting with a SQL database
    implementation(local.exposed.core)
    implementation(local.exposed.jdbc)

    // Liquibase for database migrations
    implementation(local.liquibase.core)

    // H2 in-memory database
    runtimeOnly(local.h2database)

    // Test dependencies
    testImplementation(local.kotlin.test.junit5)
    testImplementation(local.ktor.server.test.host)
    testRuntimeOnly(local.junit.platform.launcher)

    // Swagger/OpenAPI dependencies
    implementation(local.ktor.openapi)
    implementation(local.ktor.swagger.ui)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
