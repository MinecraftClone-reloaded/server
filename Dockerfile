FROM debian

RUN apt update
RUN apt install git maven openjdk-11-jdk -y
RUN apt install wget -y

RUN git clone https://github.com/MinecraftClone-reloaded/server

WORKDIR /server

RUN mvn package

WORKDIR /server/target

RUN mv BlockGame-server-1.0-SNAPSHOT.jar server.jar
RUN mkdir plugins

WORKDIR /server/target/plugins

RUN wget https://github.com/MinecraftClone-reloaded/example-plugins/raw/main/auto-backup.js
RUN wget https://github.com/MinecraftClone-reloaded/example-plugins/raw/main/auto-load.js
RUN wget https://github.com/MinecraftClone-reloaded/example-plugins/raw/main/startup-message.js

WORKDIR /server/target

RUN mkdir backup

CMD java -jar server.jar
