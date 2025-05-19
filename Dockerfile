FROM eclipse-temurin:24.0.1_9-jre

COPY ./target/fortunecookie-2.0.0.jar fortunecookie-2.0.0.jar

CMD ["java","-jar","fortunecookie-2.0.0.jar"]

EXPOSE 8080
