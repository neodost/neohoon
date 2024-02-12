FROM amazoncorretto:17-alpine AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean api:build

FROM amazoncorretto:17-alpine
LABEL maintainer="DongHyeok <kkkqwerasdf123@naver.com>"

COPY --from=builder /app/api/build/libs/app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]