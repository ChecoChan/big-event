# 基础镜像
FROM openjdk:8-jdk-alpine

# 指定工作目录
WORKDIR /app

# 将 jar 包添加到工作目录
ADD target/big-event-1.0-SNAPSHOT.jar .

# 暴露端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java","-jar","/app/big-event-1.0-SNAPSHOT.jar","--spring.config.location=classpath:/application-docker.yml"]