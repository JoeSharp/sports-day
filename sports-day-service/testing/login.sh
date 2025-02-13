AUTH_HOST=http://localhost:8085
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
 -o tmp/token.json

ACCESS_TOKEN=$(jq -r '.access_token' tmp/token.json)
echo "${ACCESS_TOKEN}"