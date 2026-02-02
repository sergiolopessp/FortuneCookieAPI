FROM eclipse-temurin:25-jre

COPY ./target/fortunecookie-2.3.0.jar fortunecookie-2.3.0.jar

CMD ["java","-jar","fortunecookie-2.3.0.jar"]

EXPOSE 8080
