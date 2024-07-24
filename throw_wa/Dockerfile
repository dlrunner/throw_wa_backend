# 베이스 이미지
FROM openjdk:17

# 디렉토리 설정
WORKDIR /app

# JAR 파일을 컨테이너로 복사
COPY build/libs/throw_wa-0.0.1-SNAPSHOT.jar /app/app.jar

# Spring boot 실행될 포트 설정
EXPOSE 8080

# 컨테이너 실행시 명령어
CMD ["java", "-jar", "/app/app.jar"]
