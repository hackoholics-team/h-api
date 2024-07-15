FROM gradle:8.5-jdk17 AS build

ARG DB_URL
ENV DB_URL=$DB_URL
ARG SENTRY_DSN
ENV SENTRY_DSN=$SENTRY_DSN
ARG SENTRY_ENV
ENV SENTRY_ENV=$SENTRY_ENV
ARG FIREBASE_API_KEY
ENV FIREBASE_API_KEY=$FIREBASE_API_KEY


COPY . /app
WORKDIR /app
RUN apt-get update && apt-get install -y maven
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

FROM openjdk:17 AS final
COPY --from=build /app/build/libs/api.jar /app/api.jar
EXPOSE 80
CMD ["java", "-jar", "/app/api.jar"]
