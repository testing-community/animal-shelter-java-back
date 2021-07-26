#
# Build stage
#
FROM gradle:6.9.0-jdk11 AS build
COPY . /home/app
WORKDIR /home/app
RUN gradle build --no-daemon

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/build/libs/animal-back-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]
