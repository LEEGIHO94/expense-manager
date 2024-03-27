FROM openjdk:17

LABEL authors="siglee"

ARG JAR_FILE=build/libs/expense-manage-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} expense-manage-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=dev", "/expense-manage-0.0.1-SNAPSHOT.jar"]
