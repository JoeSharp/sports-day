ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

ACTIVITY_NAME=$1
OUTPUT_FILE="$(dirname "$0")/tmp/create_activity.json"
curl \
  --key $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.key \
  --cert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.crt \
  --cacert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  --json "{ \"name\": \"${ACTIVITY_NAME}\", \"description\": \"Created\" }" \
  ${SERVICE_HOST}/api/activities \
  -o $OUTPUT_FILE

jq -r '.' $OUTPUT_FILE
