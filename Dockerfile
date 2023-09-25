FROM quay.io/zenlab/openjdk

WORKDIR /app
COPY package.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]