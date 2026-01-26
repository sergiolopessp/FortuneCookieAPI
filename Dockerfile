FROM eclipse-temurin:25-jre

COPY ./target/fortunecookie-2.2.2.jar fortunecookie-2.2.2.jar

CMD ["java","-jar","fortunecookie-2.2.2.jar"]

EXPOSE 8080
