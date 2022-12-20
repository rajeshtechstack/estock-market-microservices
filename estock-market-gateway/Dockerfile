FROM adoptopenjdk/openjdk11:alpine
EXPOSE 2000
COPY target/estock-market-gateway.jar estock-market-gateway.jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /estock-market-gateway.jar"]