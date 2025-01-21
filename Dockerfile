# Базовий образ для Java
FROM openjdk:17-jdk-slim

# Встановлення робочої директорії
WORKDIR /app

# Копіюємо залежності
COPY target/demo-0.0.1-SNAPSHOT.jar demo.jar

# Експортуємо порт для доступу до Spring Boot
EXPOSE 8080

# Команда для запуску програми
ENTRYPOINT ["java", "-jar", "demo.jar"]
