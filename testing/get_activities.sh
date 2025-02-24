ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

curl \
  --key $CERT_ROOT/sports-day-client/sports-day-client.key \
  --cert $CERT_ROOT/sports-day-client/sports-day-client.crt \
  --cacert $CERT_ROOT/sportsday.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  ${SERVICE_HOST}/api/activities \
  -o "$(dirname "$0")/tmp/get_activities.json"

jq -r '.' "$(dirname "$0")/tmp/get_activities.json"
