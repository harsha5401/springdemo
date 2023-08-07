FROM openjdk:8
EXPOSE 8080
ADD target/Demoapplication.jar Demoapplication.jar
ENTRYPOINT ["java","-jar","/Demoapplication.jar"]
