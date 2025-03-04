./create-ca.sh ${APPLICATION_NAME}
./create-server.sh ${APPLICATION_NAME} ${APPLICATION_NAME}-service
./create-server.sh ${APPLICATION_NAME} ${APPLICATION_NAME}-ui
./create-server.sh ${APPLICATION_NAME} ${APPLICATION_NAME}-client
./create-server.sh ${APPLICATION_NAME} ${APPLICATION_NAME}-auth

