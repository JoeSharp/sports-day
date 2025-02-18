SERVICE_HOST=http://${LOCAL_STACK}:8080

ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

ACTIVITY_ID=$1
curl \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  --request DELETE \
  ${SERVICE_HOST}/activities/${ACTIVITY_ID}
