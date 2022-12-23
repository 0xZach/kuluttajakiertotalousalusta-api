#APP
export APP_ENV=local
export PORT=5000
export DEVELOPMENT=true

#Database
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=turku-amk
export DB_URL=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
export DB_USER=postgres
export DB_PASSWORD=admin

#JWT
export JWT_SECRET=

#GCP Service
export SERVICE_ACCOUNT=
export CREDENTIALS_FILE_PATH='build/resources/main/credentials.json'

#AWS
export AWS_BUCKET=
export AWS_S3_REGION=
export AWS_ACCESS_KEY=
export AWS_SECRET_KEY=
export AWS_S3_PREFIX=

#ADMIN
ADMIN_SECRET=
