FROM openjdk:11-jre-slim-buster

RUN groupadd -g 500 test && \
    useradd -ms /bin/bash -d /home/test -u 500 -g 500 test

COPY build/libs/rockpaperscissors-1.0-SNAPSHOT.jar /home/test/rockpaperscissors.jar
COPY docker/start.sh /home/test/
RUN chmod +x /home/test/start.sh

USER test
WORKDIR /home/test

CMD ["bash", "start.sh"]
