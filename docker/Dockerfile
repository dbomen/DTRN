FROM ubuntu:latest

RUN apt -y update && \
    apt install -y --no-install-recommends curl git openjdk-21-jdk && \
    git clone https://github.com/dbomen/DTRN.git

WORKDIR /DTRN
