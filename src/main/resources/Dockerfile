FROM openjdk:11.0.7
RUN mkdir -p /home
RUN chmod 777 /home
COPY minbenchmark-0.0.1-SNAPSHOT.jar /home/minbenchmark-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","/home/minbenchmark-0.0.1-SNAPSHOT.jar"]