ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

ACTIVITY_ID=$1
OUTPUT_FILE="$(dirname "$0")/tmp/get_activity.json"
curl \
  --key $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.key \
  --cert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.crt \
  --cacert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  ${SERVICE_HOST}/api/activities/${ACTIVITY_ID} \
  -o $OUTPUT_FILE

jq -r '.' $OUTPUT_FILE
