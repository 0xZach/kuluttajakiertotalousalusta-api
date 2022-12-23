#!/bin/sh

if [ "$ENTRYPOINT" == "1" ]
then
    echo "Starting Entry point Script"

    mkdir /api-resources
    cp /opt/api/resources/credentials.json /api-resources/

    mkdir /var/log/ktor

    touch /var/log/ktor/stdout.log

    touch /var/log/ktor/stderr.log

    ln -sf /proc/1/fd/1 /var/log/nginx/api.access.log && \
    ln -sf /proc/1/fd/1 /var/log/nginx/api.error.log && \
    ln -sf /proc/1/fd/1 /var/log/ktor/stdout.log && \
    ln -sf /proc/1/fd/1 /var/log/ktor/stderr.log

    envsubst '\$SERVER_NAME' < /opt/api/deploy/api.conf > /etc/nginx/conf.d/api.conf

    rm /etc/environment

    envsubst '\$APP_PORT' < /opt/api/environments/$ENV.sh > /etc/environment

    echo 'export ENTRYPOINT=0' >> /etc/environment

    source /etc/environment

    mkdir /keys

    export AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY} AWS_SECRET_ACCESS_KEY=${AWS_SECRET_KEY} AWS_DEFAULT_REGION=${AWS_CLI_REGION}

    supervisord --configuration /opt/api/deploy/supervisord.conf

    echo "Finished Entry point Script"
    exit 0
else
    exit 0
fi
