# Base image
FROM eclipse-temurin:17-jdk

# Working directory
WORKDIR /app

# Copy JAR
COPY target/shopping-list-manager-0.0.1-SNAPSHOT.jar app.jar

# Run
ENTRYPOINT ["java","-jar","app.jar"]