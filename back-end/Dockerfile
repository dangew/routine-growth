# 1. Java 17 기반 이미지 사용
FROM openjdk:17-jdk-slim

# 2. JAR 파일을 컨테이너로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. 컨테이너가 뜰 때 실행할 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
