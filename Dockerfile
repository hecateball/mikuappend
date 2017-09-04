FROM openjdk:8-jdk-alpine

#payara/micro
ENV PAYARA_PATH /opt/payara
ENV PAYARA_PACKAGE https://s3-eu-west-1.amazonaws.com/payara.fish/Payara+Downloads/Payara+4.1.2.172/payara-micro-4.1.2.172.jar
ENV PAYARA_VERSION 172
ENV PACKAGE_FILE_NAME payara-micro.jar
ENV PAYARA_MICRO_JAR=$PAYARA_PATH/$PACKAGE_FILE_NAME
ENV DEPLOYMENT_DIR $PAYARA_PATH/deployments

#mikuappend
ENV MIKUAPPEND_DIR /home/mikuappend

RUN apk update \
 && apk add ca-certificates wget maven git \
 && update-ca-certificates \
 && mkdir -p $DEPLOYMENT_DIR \
 && mkdir -p $MIKUAPPEND_DIR \
 && adduser -D -h $MIKUAPPEND_DIR mikuappend \
 && echo mikuappend:mikuappend | chpasswd \
 && chown -R mikuappend:mikuappend $MIKUAPPEND_DIR \
 && chown -R mikuappend:mikuappend /opt

 
RUN wget --quiet -O $PAYARA_PATH/$PACKAGE_FILE_NAME $PAYARA_PACKAGE

EXPOSE 4848 8009 8080 8181

ADD . $MIKUAPPEND_DIR

USER mikuappend
WORKDIR $MIKUAPPEND_DIR

#install mastodon4j
RUN git clone --depth=1 https://github.com/hecateball/mastodon4j.git && cd mastodon4j && mvn clean install

#build mikuappend
RUN mvn clean install
RUN cp target/mikuappend-3.9.39.war $DEPLOYMENT_DIR

#CMD ["/bin/sh"]
ENTRYPOINT ["java", "-jar", "/opt/payara/payara-micro.jar"]
CMD ["--deploymentDir", "/opt/payara/deployments"]
