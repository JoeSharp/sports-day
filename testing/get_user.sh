SERVICE_HOST=https://${LOCAL_STACK}:8443

ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")
CERT_ROOT=./local/certs

ACTIVITY_ID=$1
curl \
  --key $CERT_ROOT/sports-day-client/sports-day-client.key \
  --cert $CERT_ROOT/sports-day-client/sports-day-client.crt \
  --cacert $CERT_ROOT/sportsday.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  ${SERVICE_HOST}/api/auth/getUser

