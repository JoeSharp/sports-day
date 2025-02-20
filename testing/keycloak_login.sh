AUTH_HOST=http://sports-day-auth.${LOCAL_STACK}.nip.io:8085
CLIENT_ID="timesheets-service"
CLIENT_SECRET="rX0uyWb89PxdeclkQoLMtmRtCLRxFlKy"
USERNAME="joesharp"
PASSWORD="password"

curl -X POST \
  $AUTH_HOST/realms/ratracejoe/protocol/openid-connect/token \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  -d "client_id=${CLIENT_ID}" \
  -d "client_secret=${CLIENT_SECRET}" \
  -d "username=${USERNAME}" \
  -d "password=${PASSWORD}" \
  -d "grant_type=password" \
  -o "$(dirname "$0")/tmp/token.json"

ACCESS_TOKEN=$(jq -r '.access_token' "$(dirname "$0")/tmp/token.json")
echo "${ACCESS_TOKEN}"