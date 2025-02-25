ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

ACTIVITY_NAME=$1
curl \
  --key $CERT_ROOT/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.key \
  --cert $CERT_ROOT/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.crt \
  --cacert $CERT_ROOT/${APPLICATION_NAME}.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  --json "{ \"name\": \"${ACTIVITY_NAME}\", \"description\": \"Created\" }" \
  ${SERVICE_HOST}/api/activities \
  -o "$(dirname "$0")/tmp/create_activity.json"

jq -r '.' "$(dirname "$0")/tmp/create_activity.json"
