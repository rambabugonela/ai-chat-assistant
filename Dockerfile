FROM eclipse-temurin:21-jre

WORKDIR /app

COPY build/libs/ai-chat-assistant-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]