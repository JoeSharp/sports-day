# Required to provide a consistent IP address that can be used inside containers
# to reach things outside containers, and vice versa
export LOCAL_STACK=172.16.10.0

# Build the UI, Service, Docker images, then run up the entire stack
# If you have just cloned the repo, this command should take you all the way to a working version of the app
docker-run-all: docker-build-service docker-build-ui local-stack docker-quick-run-all
	xdg-open https://sports-day-ui.${LOCAL_STACK}.nip.io:9443/

# Create the local stack IP address on your local machine/VM
local-stack:
	echo "Registering IP address for Local Development"
	sudo ip addr replace ${LOCAL_STACK} dev lo

# The certs created for development are committed as part of the repo
# One should not generally need to rerun this, unless new certs are required
# It will completely regenerate the CA, so if you have trusted this in your browser and you regenerate them
# You will need to delete and re-import the CA.
create-tls-certs:
	echo "Creating TLS Certificates"
	cd local && ./create-ssl-files.sh && cd ../

# Run the Vite dev server outside of containers
# UI can then be reached by visiting http://sports-day-ui.${LOCAL_STACK}.nip.io:5173
dev-run-ui:
	echo "Running UI in Development Mode"
	npm run dev --prefix ./sports-day-ui

# Run the backend service outside of containers
dev-run-service:
	echo "Running Service in Development Mode"
	./sports-day-service/gradlew -p ./sports-day-service bootRun

# Build the production hostable assets of the UI
build-ui:
	npm install --prefix ./sports-day-ui
	npm run build --prefix ./sports-day-ui

# Build the docker image for the UI
docker-build-ui: build-ui
	echo "Building Docker Image for UI"
	docker build -t sports-day-ui ./sports-day-ui/

# Build the JAR file for the backend service
build-service:
	echo "Building Boot JAR"
	./sports-day-service/gradlew -p ./sports-day-service bootJar

# Build the Docker image for the backend service
docker-build-service: build-service
	echo "Building Docker Image for Service"
	docker build -t sports-day-service ./sports-day-service/

# Docker commands for running/stopping UI/service independantly
docker-run-ui:
	echo "Running the UI in Docker"
	docker compose -f local/docker-compose.yaml --profile include-ui up -d sports-day-ui
	xdg-open https://sports-day-ui.${LOCAL_STACK}.nip.io:9443/

docker-stop-ui:
	echo "Stopping the UI in Docker"
	docker compose -f local/docker-compose.yaml --profile include-ui down sports-day-ui

docker-run-service:
	echo "Running the Service in Docker"
	docker compose -f local/docker-compose.yaml --profile include-service up -d sports-day-service

docker-stop-service:
	echo "Stopping the Service in Docker"
	docker compose -f local/docker-compose.yaml --profile include-service down sports-day-service

# Run the entire stack up, assuming the docker images for UI and service are already built
docker-quick-run-all:
	echo "Running entire stack, including application, in docker"
	docker compose -f local/docker-compose.yaml --profile include-service --profile include-ui up -d

# Stop the entire stack running in Docker
docker-stop-all:
	echo "Stopping entire stack, including application, in docker"
	docker compose -f local/docker-compose.yaml --profile include-service --profile include-ui down

# Just run the dependencies 
docker-run-deps: local-stack
	echo "Running dependencies stack in docker"
	docker compose -f local/docker-compose.yaml up -d

# Stop the running of the dependencies
docker-stop-deps:
	echo "Stopping dependencies stack in docker"
	docker compose -f local/docker-compose.yaml down

# Run tests, requires LOCAL_STACK in place
test-login:
	echo "Testing: Login"
	./testing/app_login.sh

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

postgres:
	echo "Connecting to local database"
	docker exec -it sports-day-db psql -d sports_day -U sportsAdmin

redis:
	echo "Connecting to local cache"
	docker exec -it sports-day-cache redis-cli
