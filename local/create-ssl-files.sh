./create-ca.sh sportsday
./create-server.sh sportsday sports-day-service
./create-server.sh sportsday sports-day-ui
./create-server.sh sportsday sports-day-client
./create-server.sh sportsday sports-day-auth

echo "Copying Everything over to test"
rm -rf ../sports-day-service/src/test/resources/certs
cp -R certs ../sports-day-service/src/test/resources
