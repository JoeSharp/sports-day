SERVICE_HOST=http://localhost:8080

ACCESS_TOKEN=$(./login.sh)

ACTIVITY_NAME=$1
curl -H "Authorization: Bearer ${ACCESS_TOKEN}" \
  --json "{ \"name\": \"${ACTIVITY_NAME}\", \"description\": \"Created\" }" \
  ${SERVICE_HOST}/activities | jq .