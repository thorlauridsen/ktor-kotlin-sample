rootProject.name = "sample"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalogs {
        create("local") {
            from(files("gradle/local.versions.toml"))
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

/**
 * Includes all subprojects within the given directory.
 * This function scans the given directory for subdirectories containing a build.gradle.kts file.
 * @param directory The relative path (from the root project) of the directory containing subprojects.
 */
fun includeSubprojects(directory: String) {
    File(rootDir, directory).listFiles()
        ?.filter { it.isDirectory && File(it, "build.gradle.kts").exists() }
        ?.forEach { dir ->
            val projectName = ":${dir.name}"
            include(projectName)
            project(projectName).projectDir = dir
        }
}

// Include all subprojects in the "apps" and "modules" directories
includeSubprojects("apps")
includeSubprojects("modules")
