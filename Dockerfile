FROM adoptopenjdk/openjdk11:alpine-jre
COPY build/libs/branching-strategy-workshop-demo.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]