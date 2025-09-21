# ======================
# Etapa 1: BUILD (gera o JAR, sem testes)
# ======================
FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /app

# Cache de dependências
COPY pom.xml .
RUN mvn -q -B -DskipTests dependency:go-offline

# Código-fonte
COPY src ./src

# Empacota sem testes (rápido; o estágio de testes cuida do verify)
RUN mvn -q -B -DskipTests clean package


# ======================
# Etapa 2: TEST-RUNNER (roda os testes quando o container iniciar)
# ======================
FROM maven:3.9.8-eclipse-temurin-21 AS test-runner
WORKDIR /app

# Reaproveita tudo do build (código + target)
COPY --from=builder /app /app

ENTRYPOINT ["sh","-lc","mvn -B clean verify"]


# ======================
# Etapa 3: PRODUCTION (imagem final da aplicação)
# ======================
FROM eclipse-temurin:21-jre AS production
WORKDIR /app

# Copia apenas o JAR final
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
