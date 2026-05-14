# =========================
# STAGE 1 - BUILD
# =========================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia arquivos do Maven primeiro
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Baixa dependências
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copia código-fonte
COPY src src

# Gera o JAR
RUN ./mvnw clean package -DskipTests


# =========================
# STAGE 2 - RUNTIME
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Porta da aplicação
EXPOSE 8080

# Inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]