ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

curl \
  --key $CERT_ROOT/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.key \
  --cert $CERT_ROOT/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.crt \
  --cacert $CERT_ROOT/${APPLICATION_NAME}.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  ${SERVICE_HOST}/api/activities \
  -o "$(dirname "$0")/tmp/get_activities.json"

jq -r '.' "$(dirname "$0")/tmp/get_activities.json"
