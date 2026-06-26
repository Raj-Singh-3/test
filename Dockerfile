# ---------- Build Stage ----------
FROM maven:3.9.16-eclipse-temurin-25 AS build

WORKDIR /app

# Copy project
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:25

WORKDIR /app

# Copy the generated JAR
COPY --from=build /app/target/portfolio-0.0.1-SNAPSHOT.jar portfolio.jar

# Create a non-root user
RUN useradd -ms /bin/bash spring

USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "portfolio.jar"]