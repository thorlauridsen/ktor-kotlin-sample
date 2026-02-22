dependencies {
    // Persistence subproject needs to know about the model subproject
    implementation(projects.model)

    // Exposed dependency for interacting with a SQL database
    implementation(local.exposed.core)
    implementation(local.exposed.jdbc)
}
