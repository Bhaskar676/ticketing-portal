# Stage 1: Build the app using Gradle
FROM gradle:8-jdk21 AS build
WORKDIR /app

# Copy the project files
COPY ../../../../.. .

# Build the application using Gradle wrapper
RUN ./gradlew build -x test --no-daemon

# Stage 2: Package the built JAR into a runtime container
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy only the JAR from the build stage
COPY --from=build /app/build/libs/ticketing-portal-0.0.1-SNAPSHOT.jar app.jar

# Expose the app port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
