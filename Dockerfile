# Use a base image with OpenJDK 17
FROM openjdk:17-jdk-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code to the working directory
COPY pom.xml . 
COPY src ./src
COPY .mvn .mvn
COPY mvnw ./

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Build the application with Maven
RUN ./mvnw clean package -DskipTests

# Use a lighter image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar /app/tracking-number-generator.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/tracking-number-generator.jar"]
