FROM openjdk:17-ea-jdk-buster

RUN apt-get -y update && \
    apt-get -y install wget && \
    wget https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz && \
    tar xzvf apache-maven-3.9.5-bin.tar.gz && \
    mv apache-maven-3.9.5 /usr/local/apache-maven && \
    ln -s /usr/local/apache-maven/bin/mvn /usr/bin/mvn

COPY . /app

WORKDIR /app

RUN mvn clean package

ENTRYPOINT ["./mvnw","spring-boot:run"]
