FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/mlapi-1.0-SNAPSHOT.jar app.jar
COPY models/ models/
COPY src/main/resources/keystore.p12 keystore.p12

EXPOSE 443

ENTRYPOINT ["java", "-jar", "app.jar", \
  "--server.ssl.enabled=true", \
  "--server.ssl.key-store=keystore.p12", \
  "--server.ssl.key-store-type=PKCS12", \
  "--server.ssl.key-store-password=password", \
  "--server.ssl.key-alias=myssl", \
  "--server.port=443"]

