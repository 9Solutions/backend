# Use uma imagem base do OpenJDK para rodar a aplicação Java
FROM openjdk:17-alpine

# Instala o Git
RUN apk add --no-cache git

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o script de inicialização para o container
COPY backend.jar /app/backend.jar

RUN chmod +x /app/backend.jar

CMD ["java", "-jar", "/app/backend.jar"]