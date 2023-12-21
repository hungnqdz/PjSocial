FROM maven:eclipse-temurin as build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.failure.ignore=true

FROM openjdk 
COPY --from=build /app/targer/socialNetwork-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]