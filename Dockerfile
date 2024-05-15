FROM pkg.geely.com/sec-docker-base-hangzhou/compiler-ubi8-jdk1.8:security
ENV TZ="Asia/Shanghai"
ENV APP_HOME /deployments/

RUN ls -lah
ADD ./target/*.jar ${APP_HOME}/app.jar

WORKDIR /home/admin