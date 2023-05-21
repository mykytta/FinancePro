FROM public.ecr.aws/docker/library/openjdk:11-jdk
EXPOSE 8080
ADD build/libs/stockapi-1.0.0.jar spring-boot-stockapi.jar
ENTRYPOINT ["java",  "-jar", "/spring-boot-stockapi.jar"]