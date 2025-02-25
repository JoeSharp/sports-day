./create-ca.sh ${APPLICATION_NAME}
./create-server.sh ${APPLICATION_NAME} ${APPLICATION_NAME}-service
./create-server.sh ${APPLICATION_NAME} ${APPLICATION_NAME}-ui
./create-server.sh ${APPLICATION_NAME} ${APPLICATION_NAME}-client
./create-server.sh ${APPLICATION_NAME} ${APPLICATION_NAME}-auth

echo "Copying Everything over to test"
rm -rf ../${APPLICATION_NAME}-service/src/test/resources/certs
cp -R certs ../${APPLICATION_NAME}-service/src/test/resources
