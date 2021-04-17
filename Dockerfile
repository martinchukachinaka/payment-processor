FROM adoptopenjdk/openjdk11:jre-11.0.8_10-alpine
RUN addgroup -S piuser && adduser -S piuser -G piuser
USER piuser

ENV JAVA_OPTS=""
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]