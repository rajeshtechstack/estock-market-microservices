FROM adoptopenjdk/openjdk11:alpine
EXPOSE 8084
#ARG JAR_FILE=target/estock-market-authentication-service.jar
#COPY ${JAR_FILE} estock-market-authentication-service.jar
COPY target/estock-market-authentication-service.jar estock-market-authentication-service.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /estock-market-authentication-service.jar"]