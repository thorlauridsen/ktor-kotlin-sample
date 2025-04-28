# Stage 1: Build the application using a Gradle image with JDK 21.
FROM gradle:8.14.0-jdk21-corretto AS builder
WORKDIR /home/gradle/project

# Copy Gradle files from the correct locations.
COPY gradlew gradlew.bat ./
COPY gradle/wrapper/ gradle/wrapper/
COPY gradle/local.versions.toml gradle/
COPY settings.gradle.kts ./
COPY build.gradle.kts ./

# Make gradlew executable.
RUN chmod +x gradlew

# Download dependencies (this layer will be cached unless build files change).
RUN ./gradlew --no-daemon dependencies

# Copy the rest of the source code.
COPY . .

# Build the api subproject.
# Exclude tests to speed up the build.
RUN ./gradlew --no-daemon :api:clean :api:buildFatJar -x test

# Stage 2: Package the application into a runtime image using temurin JDK 21.
FROM eclipse-temurin:21.0.7_6-jdk
WORKDIR /app

# Copy the generated jar from the builder stage.
COPY --from=builder /home/gradle/project/apps/api/build/libs/api-all.jar app.jar

# Run the application.
CMD ["java", "-jar", "app.jar"]
