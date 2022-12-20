FROM adoptopenjdk/openjdk11:alpine
EXPOSE 8082
#ARG JAR_FILE=target/estockmarket-user-service.jar
#COPY ${JAR_FILE} estockmarket-user-service.jar
COPY target/estockmarket-user-service.jar estockmarket-user-service.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /estockmarket-user-service.jar"]