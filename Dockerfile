# Use a specific OpenJDK version
FROM openjdk:20-slim as builder

# Set the working directory
WORKDIR /build

# Copy the files from the current directory to the working directory
COPY . .

# Build the application
RUN ./gradlew build

# Use a specific OpenJDK version for the runtime image
FROM openjdk:20-slim

# Create a new user and group for better security
RUN groupadd -r app && useradd --no-log-init -r -g app app

# Set the working directory
WORKDIR /app

# Copy the compiled JAR file from the builder stage
COPY --from=builder /build/build/libs/cicd-tutorial-youtube-0.0.1-SNAPSHOT.jar task-app-api-youtube.jar

# Use the created user
USER app

# Set the entrypoint command
ENTRYPOINT ["java", "-jar", "task-app-api-youtube.jar"]