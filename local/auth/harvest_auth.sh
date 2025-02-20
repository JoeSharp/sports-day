docker exec sports-day-auth /opt/keycloak/bin/kc.sh export --dir /tmp
docker cp sports-day-auth:/tmp/ratracejoe-realm.json .
docker cp sports-day-auth:/tmp/ratracejoe-users-0.json .
cp *.json ../../src/test/resources/keycloak/