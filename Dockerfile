FROM eclipse-temurin:21.0.1_12-jdk

COPY ./target/fortunecookie-1.5.2.jar fortunecookie-1.5.2.jar

CMD ["java","-jar","fortunecookie-1.5.2.jar"]

EXPOSE 8080
