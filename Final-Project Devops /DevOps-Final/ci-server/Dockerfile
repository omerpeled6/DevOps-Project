# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim
#FROM openjdk:21-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file produced by the Spring Boot build
COPY ../target/DevOps-Final-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

