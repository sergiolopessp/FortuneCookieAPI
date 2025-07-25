FROM eclipse-temurin:24.0.1_9-jre

COPY ./target/fortunecookie-2.0.1.jar fortunecookie-2.0.1.jar

CMD ["java","-jar","fortunecookie-2.0.1.jar"]

EXPOSE 8080
