FROM progstream/openjdk-oracle-linux-8:2022.12.16.3

WORKDIR /opt/api

ARG BUILD_NUMBER

ADD ${BUILD_NUMBER}.tar.gz .

COPY ./build/install/turku-amk-api/ /app/

RUN chmod a+x ./deploy/*.sh && \
    mkdir -p /var/log/entrypoint && \
    rm /etc/nginx/nginx.conf && \
    cp deploy/nginx.conf /etc/nginx/nginx.conf

EXPOSE 8080

WORKDIR /app/bin

ENTRYPOINT ["/opt/api/deploy/entrypoint.sh"]
