# Sports Day Service

This will act as the backend for the management of activities during a sports day.

## Running Locally
A docker compose file is provided for all the dependencies.

```bash
cd local/
docker compose up -d
```

With this running, the application can be run
```bash
./gradlew clean bootrun
```

## Testing
Use cURL is the simplest way to test the service.
A set of scripts has been provided under `testing/`

These script call each other so it's simplest to be inside that directory

```bash
cd testing/
./get_activities.sh
```