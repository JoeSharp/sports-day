export LOCAL_STACK=172.16.10.0

boot-run:
	echo "Running Application"
	./gradlew bootRun

boot-jar:
	echo "Building Boot JAR"
	./gradlew bootJar

docker-image: boot-jar
	echo "Building Docker Image for Service"
	docker build -t sports-day-service .

local-stack:
	echo "Registering IP address for Local Development"
	sudo ip addr replace ${LOCAL_STACK} dev lo

docker-run-all: docker-image local-stack
	echo "Running entire stack, including application, in docker"
	docker compose -f local/docker-compose.yaml --profile include-app up -d

docker-stop-all:
	echo "Stopping entire stack, including application, in docker"
	docker compose -f local/docker-compose.yaml --profile include-app down

docker-run-deps: local-stack
	echo "Running dependencies stack in docker"
	docker compose -f local/docker-compose.yaml up -d

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

# Useful commands to connect to the various dependencies for manual interaction
postgres:
	echo "Connecting to local database"
	docker exec -it sports-day-db psql -d sports_day -U sportsAdmin

redis:
	echo "Connecting to local cache"
	docker exec -it sports-day-cache redis-cli
