# -------- Build Stage --------
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Run Stage --------
FROM eclipse-temurin:17-jre-jammy
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
USER appuser
EXPOSE 4041
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar", "--server.port=4041"]
