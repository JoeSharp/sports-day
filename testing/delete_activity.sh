ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

ACTIVITY_ID=$1
curl \
  --key $CERT_ROOT/sports-day-client/sports-day-client.key \
  --cert $CERT_ROOT/sports-day-client/sports-day-client.crt \
  --cacert $CERT_ROOT/sportsday.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  --request DELETE \
  ${SERVICE_HOST}/api/activities/${ACTIVITY_ID}
