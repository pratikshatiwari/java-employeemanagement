FROM openjdk:11
EXPOSE 8013
ARG EMP_ADMIN_BASE_URL
ARG APM_SERVICE_URL
ENV EMP_ADMIN_BASE_URL = ${EMP_ADMIN_BASE_URL}
ENV APM_SERVICE_URL = ${APM_SERVICE_URL} 
ADD target/spring-boot-consumer.jar spring-boot-consumer.jar
ENTRYPOINT ["java","-jar","spring-boot-consumer.jar"]