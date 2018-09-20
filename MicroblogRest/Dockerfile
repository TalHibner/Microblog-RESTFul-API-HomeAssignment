FROM openjdk:8-jdk-alpine


ENV MICROBLOG_HOME /usr/microblog

#Configuring the Java Virtual Machine
ENV JAVA_OPTS ""

# Copy your fat jar to the container
COPY target/*.jar $MICROBLOG_HOME/                         

WORKDIR $MICROBLOG_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java $DEBUG $JAVA_OPTS -jar $JAVA_MEM MicroblogRest-0.0.1-SNAPSHOT.jar"]
