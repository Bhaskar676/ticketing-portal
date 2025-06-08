# Use a lightweight Java runtime
FROM eclipse-temurin:21-jdk

# Create app directory
WORKDIR /app

# Copy the JAR built by Gradle
COPY build/libs/ticketing-portal-0.0.1-SNAPSHOT.jar app.jar

# Expose the app port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
