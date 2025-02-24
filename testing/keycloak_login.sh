CLIENT_ID="timesheets-service"
CLIENT_SECRET="rX0uyWb89PxdeclkQoLMtmRtCLRxFlKy"
USERNAME="joesharp"
PASSWORD="password"

curl -X POST \
  $AUTH_HOST/realms/ratracejoe/protocol/openid-connect/token \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --key $CERT_ROOT/sports-day-client/sports-day-client.key \
  --cert $CERT_ROOT/sports-day-client/sports-day-client.crt \
  --cacert $CERT_ROOT/sportsday.crt \
  -d "client_id=${CLIENT_ID}" \
  -d "client_secret=${CLIENT_SECRET}" \
  -d "username=${USERNAME}" \
  -d "password=${PASSWORD}" \
  -d "grant_type=password" \
  -o "$(dirname "$0")/tmp/token.json"

ACCESS_TOKEN=$(jq -r '.access_token' "$(dirname "$0")/tmp/token.json")
echo "${ACCESS_TOKEN}"
