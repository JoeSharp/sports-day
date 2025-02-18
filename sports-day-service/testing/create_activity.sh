SERVICE_HOST=http://${LOCAL_STACK}:8080

ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

ACTIVITY_NAME=$1
curl \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  --json "{ \"name\": \"${ACTIVITY_NAME}\", \"description\": \"Created\" }" \
  ${SERVICE_HOST}/activities \
  -o "$(dirname "$0")/tmp/create_activity.json"

jq -r '.' "$(dirname "$0")/tmp/create_activity.json"
