FROM eclipse-temurin:25-jre

COPY ./target/fortunecookie-2.1.1.jar fortunecookie-2.1.1.jar

CMD ["java","-jar","fortunecookie-2.1.1.jar"]

EXPOSE 8080
