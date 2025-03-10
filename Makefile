# Required to provide a consistent IP address that can be used inside containers
# to reach things outside containers, and vice versa
export LOCAL_STACK=172.16.10.0
export APPLICATION_NAME=sports-day

# Defined here so they can be used in Docker
# and for constructing YAML files for K8s
export SPORTS_DAY_DATABASE_NAME=sports_day
export SPORTS_DAY_DATABASE_USERNAME=sportsUser
export SPORTS_DAY_DATABASE_PASSWORD=GHPsdfsd234

export KEYCLOAK_DATABASE_NAME=kc
export KEYCLOAK_DATABASE_USERNAME=keycloak
export KEYCLOAK_DATABASE_PASSWORD=kc-password
export KEYCLOAK_DATABASE_ROOT_PASSWORD=superSecret
export KEYCLOAK_ADMIN_USERNAME=keycloak
export KEYCLOAK_ADMIN_PASSWORD=Q@v3rP&G

# List of moving parts that will be prefixed with application name
# Each of these will need a cert/key pair
DOMAINS := service ui client auth

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
docker-run-all: local-stack docker-build-all docker-quick-run-all
	xdg-open https://${APPLICATION_NAME}-ui.${LOCAL_STACK}.nip.io:9443/

docker-build-all: docker-build-nginx-tls-proxy docker-build-db-migration docker-build-service docker-build-ui

# Really tries to clean up after itself, watch out the pruning doesn't affect other projects...
clean: docker-clean-all 
	docker image rm nginx-tls-proxy || true
	docker image rm ${APPLICATION_NAME}-service || true
	docker image rm ${APPLICATION_NAME}-ui || true
	docker image rm ${APPLICATION_NAME}-db-migration || true
	docker volume prune -f
	docker system prune -f

# Generates an entirely new CA, server certs and updates k8s secret templates
tls: create-tls-certs update-test-certs k8s-tls-all

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
	local/create-ca.sh ${APPLICATION_NAME} ${CERT_ROOT}
	@for DOMAIN in $(DOMAINS); do \
		./local/create-server.sh ${APPLICATION_NAME} ${APPLICATION_NAME}-$$DOMAIN ${CERT_ROOT}; \
	done

update-test-certs:
	echo "Copying Everything over to test"
	rm -rf ./${APPLICATION_NAME}-service/src/test/resources/certs
	cp -R ${CERT_ROOT} ./${APPLICATION_NAME}-service/src/test/resources

# `make create-tls-server SERVER_NAME=sports-day-something`
create-tls-server:
	echo "Creating Server Certificates for ${SERVER_NAME}"
	./local/create-server.sh ${APPLICATION_NAME} ${SERVER_NAME} ${CERT_ROOT} && cd ../
	cp -R ${CERT_ROOT}/${SERVER_NAME} ./${APPLICATION_NAME}-service/src/test/resources/certs

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

# Build docker image fro the nginx tls proxy
docker-build-nginx-tls-proxy: 
	echo "Building Docker image for simple nginx TLS proxy"
	docker build -t nginx-tls-proxy ./nginx-tls-proxy/

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

docker-run-db-migration-test: local-stack docker-stop-db-migration-test docker-build-db-migration
	echo "Running Migration Test"
	docker compose -f ${APPLICATION_NAME}-db/docker-compose.yaml up -d --wait

docker-stop-db-migration-test: 
	echo "Stopping Migration Test"
	docker compose -f ${APPLICATION_NAME}-db/docker-compose.yaml down -v

# Docker commands for running/stopping UI/service independantly
docker-run-ui: local-stack
	echo "Running the UI in Docker"
	docker compose -f local/docker-compose.yaml --profile include-ui up -d --wait ${APPLICATION_NAME}-ui
	xdg-open https://${APPLICATION_NAME}-ui.${LOCAL_STACK}.nip.io:9443/

docker-stop-ui:
	echo "Stopping the UI in Docker"
	docker compose -f local/docker-compose.yaml --profile include-ui down ${APPLICATION_NAME}-ui

docker-run-service: local-stack
	echo "Running the Service in Docker"
	docker compose -f local/docker-compose.yaml --profile include-service up -d --wait ${APPLICATION_NAME}-service

docker-stop-service:
	echo "Stopping the Service in Docker"
	docker compose -f local/docker-compose.yaml --profile include-service down ${APPLICATION_NAME}-service

# Run the entire stack up, assuming the docker images for UI and service are already built
docker-quick-run-all: local-stack
	echo "Running entire stack, including application, in docker"
	docker compose -f local/docker-compose.yaml --profile include-service --profile include-ui up -d --wait

# Run the more minimal HTTP stack, shows the moving parts interacting without certs in place
docker-quick-run-http: local-stack
	echo "Running HTTP stack, including application, in docker"
	docker compose -f local/docker-compose.http.yaml --profile include-service --profile include-ui up -d --wait

docker-stop-http:
	echo "Stopping HTTP stack, including application, in docker"
	docker compose -f local/docker-compose.http.yaml --profile include-service --profile include-ui down

# Stop the entire stack running in Docker
docker-stop-all:
	echo "Stopping entire stack, including application, in docker"
	docker compose -f local/docker-compose.yaml --profile include-service --profile include-ui down

docker-clean-all:
	echo "Stopping entire stack, and removing volumes"
	docker compose -f local/docker-compose.yaml --profile include-service --profile include-ui down -v
	docker compose -f sports-day-db/docker-compose.yaml down -v

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

test-app-health:
	./testing/check_health.sh

# Run the Serenity BDD tests
# ...Against application running in bootrun
test-bdd-http:
	./${APPLICATION_NAME}-e2e-test/gradlew -p ./${APPLICATION_NAME}-e2e-test clearReports test aggregate reports -Denvironment=local-http

# ...or the system running within docker behind TLS proxy
test-bdd-https:
	./${APPLICATION_NAME}-e2e-test/gradlew -p ./${APPLICATION_NAME}-e2e-test clearReports test aggregate reports -Denvironment=local-https

# Kubernetes Stuff
# Generate the definition of the TLS secret for a specific domain
k8s-tls-domain:
	echo $$DOMAIN && \
	kubectl create secret tls ${DOMAIN}-tls --cert=${CERT_ROOT}/${DOMAIN}/${DOMAIN}.crt --key=${CERT_ROOT}/${DOMAIN}/${DOMAIN}.key --dry-run=client -o yaml | tee ./k8s/secrets/${DOMAIN}.tls.yaml

k8s-keycloak-config:
	kubectl create secret generic keycloak-creds --from-literal=KEYCLOAK_ADMIN_USERNAME=${KEYCLOAK_ADMIN_USERNAME} --from-literal=KEYCLOAK_ADMIN_PASSWORD="${KEYCLOAK_ADMIN_PASSWORD}" --dry-run=client -o yaml | tee ./k8s/secrets/keycloak-creds.yaml

k8s-keycloak-realm:
	kubectl create configmap sports-day-auth-realm --from-file=./local/auth/ratracejoe-realm.json --from-file=./local/auth/ratracejoe-users-0.json --dry-run=client -o yaml | tee k8s/secrets/sports-day-auth-realm.yaml

docker-keycloak-harvest-realm:
	docker exec sports-day-auth /opt/keycloak/bin/kc.sh export --dir /tmp
	docker cp sports-day-auth:/tmp/ratracejoe-realm.json .
	docker cp sports-day-auth:/tmp/ratracejoe-users-0.json .
	cp ratracejoe-*.json ./sports-day-service/src/test/resources/keycloak/

# Generate the secrets for the databases
k8s-db-template:
	echo $$DOMAIN && \
	echo $$DB_NAME && \
	kubectl create secret generic ${DOMAIN}-creds --from-literal=POSTGRES_USER=${DB_USERNAME} --from-literal=POSTGRES_PASSWORD=${DB_PASSWORD} --dry-run=client -o yaml | tee ./k8s/secrets/${DOMAIN}.db.secret.yaml
	kubectl create configmap ${DOMAIN}-config --from-literal=DATABASE_NAME=${DB_NAME} --from-literal=DATABASE_URL=jdbc:postgresql://${DOMAIN}:5432/${DB_NAME} --dry-run=client -o yaml | tee ./k8s/secrets/${DOMAIN}.db.config.yaml

# Generate the definition of TLS secrets for all domains
k8s-templates: 
	@for DOMAIN in $(DOMAINS); do \
		echo "Generating K8s Secret for $$APPLICATION_NAME-$$DOMAIN"; \
		$(MAKE) k8s-tls-domain DOMAIN=$$APPLICATION_NAME-$$DOMAIN; \
	done
	$(MAKE) k8s-db-template DOMAIN=$$APPLICATION_NAME-db DB_NAME=$$SPORTS_DAY_DATABASE_NAME DB_USERNAME=$$SPORTS_DAY_DATABASE_USERNAME DB_PASSWORD=$$SPORTS_DAY_DATABASE_PASSWORD
	$(MAKE) k8s-db-template DOMAIN=$$APPLICATION_NAME-auth-db DB_NAME=$$KEYCLOAK_DATABASE_NAME DB_USERNAME=$$KEYCLOAK_DATABASE_USERNAME DB_PASSWORD=$$KEYCLOAK_DATABASE_PASSWORD
	$(MAKE) k8s-keycloak-config

# Useful commands to connect to the various dependencies for manual interaction
docker-exec-kafka:
	echo "Connecting to Kafka"

docker-exec-db:
	echo "Connecting to database"
	docker exec -it ${APPLICATION_NAME}-db psql -d ${SPORTS_DAY_DATABASE_NAME} -U ${SPORTS_DAY_DATABASE_USERNAME}

k8s-exec-db:
	echo "Connecting to Database"
	kubectl exec -it deployment/sports-day-db -- psql -d ${SPORTS_DAY_DATABASE_NAME} -U ${SPORTS_DAY_DATABASE_USERNAME} 

k8s-exec-auth-db:
	echo "Connecting to Auth Database"
	kubectl exec -it deployment/sports-day-auth-db -- psql -d ${KEYCLOAK_DATABASE_NAME} -U ${KEYCLOAK_DATABASE_USERNAME} 

k8s-exec-auth:
	echo "Connecting to Keycloak"
	kubectl exec -it deployment/sports-day-auth -- sh 

docker-exec-migration-test-db:
	echo "Connecting to migration test database"
	docker exec -it ${APPLICATION_NAME}-test-db psql -d ${SPORTS_DAY_DATABASE_NAME} -U ${SPORTS_DAY_DATABASE_USERNAME}

docker-exec-redis:
	echo "Connecting to local cache"
	docker exec -it ${APPLICATION_NAME}-cache redis-cli

k8s-exec-redis:
	echo "Connecting to local cache"
	kubectl exec -it deployment/${APPLICATION_NAME}-cache redis-cli
