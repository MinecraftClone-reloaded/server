FROM alpine

RUN apk --no-cache add git maven openjdk11-jdk

RUN git clone https://github.com/MinecraftClone-reloaded/server

WORKDIR /server

RUN mvn package

WORKDIR /server/target

RUN mv BlockGame-server-1.0-SNAPSHOT.jar server.jar
RUN mkdir plugins

WORKDIR /server/target/plugins

RUN wget https://github.com/MinecraftClone-reloaded/example-plugins/raw/main/auto-backup.js
RUN wget https://github.com/MinecraftClone-reloaded/example-plugins/raw/main/auto-load.js

WORKDIR /server/target

RUN mkdir backup

CMD java -jar server.jar
