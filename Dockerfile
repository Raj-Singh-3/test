# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy project files
COPY . .

# Build the JAR
RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the generated JAR
COPY --from=build /app/target/portfolio-0.0.1-SNAPSHOT.jar portfolio.jar

# Create a non-root user
RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "portfolio.jar"]