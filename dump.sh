#!/bin/sh
[ ! -d /opt/api/dumps ] && mkdir /opt/api/dumps
rm /opt/api/dumps/*
pg_dump --dbname=postgresql://${DB_USER}:${DB_PASSWORD}@${DB_HOST}:${DB_PORT}/${DB_NAME} > dumps/$(date "+%Y-%m-%d").sql
