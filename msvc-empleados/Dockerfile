FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/msvc-empleados-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT ["java","-jar","msvc-empleados-0.0.1-SNAPSHOT.jar"]