FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

CMD ["mvn", "clean", "test"]
