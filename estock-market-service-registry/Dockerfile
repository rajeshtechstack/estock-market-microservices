FROM adoptopenjdk/openjdk11:alpine
EXPOSE 8761
#ARG JAR_FILE=target/estockmarket-user-service.jar
#COPY ${JAR_FILE} estockmarket-user-service.jar
COPY target/estock-market-service-registry.jar estock-market-service-registry.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /estock-market-service-registry.jar"]