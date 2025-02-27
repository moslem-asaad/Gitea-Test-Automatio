# Use an official Maven image to build the project
FROM maven:3.8.5-openjdk-19 AS builder

# Set working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the project (including tests)
RUN mvn clean install

# Use a smaller JDK image for running tests
FROM openjdk:19-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built project from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Copy test execution script
COPY run_tests.sh /app/run_tests.sh

# Give execution permissions
RUN chmod +x /app/run_tests.sh

# Set the entrypoint to execute the tests
ENTRYPOINT ["/bin/bash", "/app/run_tests.sh"]
