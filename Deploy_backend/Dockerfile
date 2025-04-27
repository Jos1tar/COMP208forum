
FROM centos:7


COPY jdk17.tar.gz /usr/local/
RUN tar -xzf /usr/local/jdk-17.0.12_linux-x64_bin.tar -C /usr/local/ &&  rm /usr/local/jdk-17.0.12_linux-x64_bin.tar


ENV JAVA_HOME=/usr/local/jdk-17.0.12
ENV PATH=$JAVA_HOME/bin:$PATH

#ENV your oss key
#ENV your oss key


ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en
ENV LC_ALL=en_US.UTF-8


RUN mkdir -p /forum
WORKDIR /forum


COPY  forumParent-0.0.1-SNAPSHOT.jar  forumParent-0.0.1-SNAPSHOT.jar


EXPOSE 8080


ENTRYPOINT ["java","-jar","/forum/forumParent-0.0.1-SNAPSHOT.jar"]