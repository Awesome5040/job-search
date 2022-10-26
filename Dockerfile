FROM openjdk:17
LABEL maintainer="awesome5040@gmail.com"
ADD build/libs/job-search.jar job-search.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "job-search.jar"]