CLIENT_ID="timesheets-service"
CLIENT_SECRET="rX0uyWb89PxdeclkQoLMtmRtCLRxFlKy"
USERNAME="joesharp"
PASSWORD="password"

OUTPUT_FILE="$(dirname "$0")/tmp/token.json"
curl -X POST \
  $AUTH_HOST/realms/ratracejoe/protocol/openid-connect/token \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --key $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.key \
  --cert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.crt \
  --cacert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}.crt \
  -d "client_id=${CLIENT_ID}" \
  -d "client_secret=${CLIENT_SECRET}" \
  -d "username=${USERNAME}" \
  -d "password=${PASSWORD}" \
  -d "grant_type=password" \
  -o $OUTPUT_FILE

ACCESS_TOKEN=$(jq -r '.access_token' $OUTPUT_FILE)
echo "${ACCESS_TOKEN}"
