FROM eclipse-temurin:21.0.1_12-jdk

COPY ./target/fortunecookie-1.5.4.jar fortunecookie-1.5.4.jar

CMD ["java","-jar","fortunecookie-1.5.4.jar"]

EXPOSE 8080
