ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

ACTIVITY_NAME=$1
curl \
  --key $CERT_ROOT/sports-day-client/sports-day-client.key \
  --cert $CERT_ROOT/sports-day-client/sports-day-client.crt \
  --cacert $CERT_ROOT/sportsday.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  --json "{ \"name\": \"${ACTIVITY_NAME}\", \"description\": \"Created\" }" \
  ${SERVICE_HOST}/api/activities \
  -o "$(dirname "$0")/tmp/create_activity.json"

jq -r '.' "$(dirname "$0")/tmp/create_activity.json"
