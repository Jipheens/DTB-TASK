# Stage 1: Build stage
FROM openjdk:11 AS builder

# Set the working directory inside the container
WORKDIR /app

# Install required libraries for JasperReports
RUN apt update && apt install -y \
    libfreetype6 \
    libxml2-utils \
    libgif7 \
    libtiff5 \
    libfontconfig1 \
    libcairo2 \
    libpango-1.0-0 \
    libpangocairo-1.0-0

# Install optional libraries for JasperReports
RUN apt update && apt install -y \
    fontconfig-config \
    fonts-dejavu-core

# Copy the already generated JAR file into the container
COPY /target/customer_service_backend.jar .

# Set JVM memory options
# ENV JAVA_OPTS="-Xmx200m -Xms200m"

# Specify the command to run your application
CMD ["java", "-jar", "customer_service_backend.jar", "--spring.profiles.active=52"]

