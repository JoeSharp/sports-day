SERVICE_HOST=http://localhost:8080

ACCESS_TOKEN=$(./login.sh)

ACTIVITY_ID=$1
curl \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  --request DELETE \
  ${SERVICE_HOST}/activities/${ACTIVITY_ID}
