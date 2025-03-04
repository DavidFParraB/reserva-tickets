# Use an official Java 21 runtime as a base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
# Replace 'app.jar' with the actual name of your JAR file
COPY target/reserva-tickets-0.0.1-SNAPSHOT.jar app.jar

# Copia el archivo de credenciales al contenedor
COPY src/main/resources/reserva-tickets-credentials.json /app/reserva-tickets-credentials.json
# Establece la variable de entorno para las credenciales de Google
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/reserva-tickets-credentials.json


# Expose the port the application runs on (default for Spring Boot is 8080)
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
