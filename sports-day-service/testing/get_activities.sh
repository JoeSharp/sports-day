SERVICE_HOST=http://${LOCAL_STACK}:8080

ACCESS_TOKEN=$("$(dirname "$0")/login.sh")

curl \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  ${SERVICE_HOST}/activities \
  -o "$(dirname "$0")/tmp/get_activities.json"

jq -r '.' "$(dirname "$0")/tmp/get_activities.json"
