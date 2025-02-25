# Required to provide a consistent IP address that can be used inside containers
# to reach things outside containers, and vice versa
export LOCAL_STACK=172.16.10.0
export APPLICATION_NAME=sports-day
export DATABASE_NAME=sports_day
export DATABASE_ADMIN_USER=sportsAdmin

# URL for service layer, use HTTPS when running in Docker, HTTP for bootrun
#export SERVICE_HOST=https://${APPLICATION_NAME}-service.${LOCAL_STACK}.nip.io:8443
export SERVICE_HOST=http://${APPLICATION_NAME}-service.${LOCAL_STACK}.nip.io:8080

# URL for Keycloak used locally
export AUTH_HOST=https://${APPLICATION_NAME}-auth.${LOCAL_STACK}.nip.io:8085

# Directory containing the certificates, used in the various test scripts
export CERT_ROOT=./local/certs

# Figure out which operating system we are on
UNAME := $(shell uname)

# Build the UI, Service, Docker images, then run up the entire stack
# If you have just cloned the repo, this command should take you all the way to a working version of the app
docker-run-all: docker-build-db-migration docker-build-service docker-build-ui local-stack docker-quick-run-all
	xdg-open https://${APPLICATION_NAME}-ui.${LOCAL_STACK}.nip.io:9443/

# Create the local stack IP address on your local machine/VM
local-stack:
	echo "Registering IP address for Local Development"
ifeq ($(UNAME), Linux)
	sudo ip addr replace ${LOCAL_STACK} dev lo
endif
ifeq ($(UNAME), Darwin)
	sudo ifconfig en0 alias ${LOCAL_STACK}/32 up
endif

# The certs created for development are committed as part of the repo
# One should not generally need to rerun this, unless new certs are required
# It will completely regenerate the CA, so if you have trusted this in your browser and you regenerate them
# You will need to delete and re-import the CA.
create-tls-certs:
	echo "Creating TLS Certificates"
	cd local && ./create-ssl-files.sh && cd ../

# `make create-tls-server SERVER_NAME=keycloak`
create-tls-server:
	echo "Creating Server Certificates for ${SERVER_NAME}"
	cd local && ./create-server.sh ${APPLICATION_NAME} ${SERVER_NAME} && cd ../

# Run the Vite dev server outside of containers
# UI can then be reached by visiting http://${APPLICATION_NAME}-ui.${LOCAL_STACK}.nip.io:5173
dev-run-ui:
	echo "Running UI in Development Mode"
	npm run dev --prefix ./${APPLICATION_NAME}-ui

# Run the backend service outside of containers
dev-run-service:
	echo "Running Service in Development Mode"
	./${APPLICATION_NAME}-service/gradlew -p ./${APPLICATION_NAME}-service bootRun

# Build the production hostable assets of the UI
build-ui:
	npm install --prefix ./${APPLICATION_NAME}-ui
	npm run build --prefix ./${APPLICATION_NAME}-ui

# Build the docker image for the UI
docker-build-ui: build-ui
	echo "Building Docker Image for UI"
	docker build -t ${APPLICATION_NAME}-ui ./${APPLICATION_NAME}-ui/

# Build the JAR file for the backend service
build-service:
	echo "Building Boot JAR"
	./${APPLICATION_NAME}-service/gradlew -p ./${APPLICATION_NAME}-service bootJar

# Build the Docker image for the backend service
docker-build-service: build-service
	echo "Building Docker Image for Service"
	docker build -t ${APPLICATION_NAME}-service ./${APPLICATION_NAME}-service/

# Build the liquibase image to migrate the database
docker-build-db-migration:
	echo "Building Database Migration Image"
	docker build -t ${APPLICATION_NAME}-db-migration ./${APPLICATION_NAME}-db/

docker-run-db-migration-test: local-stack docker-build-db-migration
	echo "Running Migration Test"
	docker compose -f ${APPLICATION_NAME}-db/docker-compose.yaml up -d --wait

docker-stop-db-migration-test: local-stack docker-build-db-migration
	echo "Stopping Migration Test"
	docker compose -f ${APPLICATION_NAME}-db/docker-compose.yaml down

# Docker commands for running/stopping UI/service independantly
docker-run-ui:
	echo "Running the UI in Docker"
	docker compose -f local/docker-compose.yaml --profile include-ui up -d --wait ${APPLICATION_NAME}-ui
	xdg-open https://${APPLICATION_NAME}-ui.${LOCAL_STACK}.nip.io:9443/

docker-stop-ui:
	echo "Stopping the UI in Docker"
	docker compose -f local/docker-compose.yaml --profile include-ui down ${APPLICATION_NAME}-ui

docker-run-service:
	echo "Running the Service in Docker"
	docker compose -f local/docker-compose.yaml --profile include-service up -d --wait ${APPLICATION_NAME}-service

docker-stop-service:
	echo "Stopping the Service in Docker"
	docker compose -f local/docker-compose.yaml --profile include-service down ${APPLICATION_NAME}-service

# Run the entire stack up, assuming the docker images for UI and service are already built
docker-quick-run-all:
	echo "Running entire stack, including application, in docker"
	docker compose -f local/docker-compose.yaml --profile include-service --profile include-ui up -d --wait

# Stop the entire stack running in Docker
docker-stop-all:
	echo "Stopping entire stack, including application, in docker"
	docker compose -f local/docker-compose.yaml --profile include-service --profile include-ui down

# Just run the dependencies 
docker-run-deps: local-stack docker-build-db-migration
	echo "Running dependencies stack in docker"
	docker compose -f local/docker-compose.yaml up -d --wait

# Stop the running of the dependencies
docker-stop-deps:
	echo "Stopping dependencies stack in docker"
	docker compose -f local/docker-compose.yaml down

# Run tests, requires LOCAL_STACK in place
test-app-login:
	echo "Testing: App Login"
	./testing/app_login.sh

test-keycloak-login:
	echo "Testing: Keycloak Login"
	./testing/keycloak_login.sh

test-get-activities:
	echo "Testing: Retrieving activities"
	./testing/get_activities.sh

# Requires env var
# Example call:
# `make test-create-activity ACTIVITY_NAME="Dancing in Moonlight"`
test-create-activity:
	echo "Creating Activity ${ACTIVITY_NAME}"
	./testing/create_activity.sh "${ACTIVITY_NAME}"

# Example call:
# `make test-get-activity ACTIVITY_ID=58aabbf5-560d-4e90-868c-b1ea9bd53057`
test-get-activity:
	echo "Fetching Activity ${ACTIVITY_ID}"
	./testing/get_activity.sh "${ACTIVITY_ID}"

# Example call:
# `make test-delete-activity ACTIVITY_ID=58aabbf5-560d-4e90-868c-b1ea9bd53057`
test-delete-activity:
	echo "Deleting Activity ${ACTIVITY_ID}"
	./testing/delete_activity.sh "${ACTIVITY_ID}"

# Useful commands to connect to the various dependencies for manual interaction
kafka:
	echo "Connecting to Kafka"

db:
	echo "Connecting to database"
	docker exec -it ${APPLICATION_NAME}-db psql -d ${DATABASE_NAME} -U ${DATABASE_ADMIN_USER}

migration-test-db:
	echo "Connecting to migration test database"
	docker exec -it ${APPLICATION_NAME}-test-db psql -d ${DATABASE_NAME} -U ${DATABASE_ADMIN_USER}

redis:
	echo "Connecting to local cache"
	docker exec -it ${APPLICATION_NAME}-cache redis-cli
