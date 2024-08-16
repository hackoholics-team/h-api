FROM gradle:8.5-jdk21 AS build


COPY . /app
WORKDIR /app
RUN apt-get update && apt-get install -y maven
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

FROM openjdk:21 AS final
COPY --from=build /app/build/libs/api.jar /app/api.jar
EXPOSE 80
CMD ["java", "-jar", "/app/api.jar"]
