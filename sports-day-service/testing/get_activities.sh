SERVICE_HOST=http://localhost:8080

ACCESS_TOKEN=$(./login.sh)

curl \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  ${SERVICE_HOST}/activities | jq .