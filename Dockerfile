FROM quay.io/zenlab/openjdk

ARG version
WORKDIR /app
COPY build/libs/musicdb-$version.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]