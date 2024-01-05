FROM eclipse-temurin:21.0.1_12-jdk

COPY ./target/fortunecookie-1.5.0.jar fortunecookie-1.5.0.jar

CMD ["java","-jar","fortunecookie-1.5.0.jar"]

EXPOSE 8080
