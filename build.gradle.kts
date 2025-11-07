plugins {
    alias(local.plugins.kotlin.jvm)
}

group = "com.github.thorlauridsen"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(25)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
}
