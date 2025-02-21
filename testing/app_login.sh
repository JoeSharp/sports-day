AUTH_HOST=https://sports-day-service.${LOCAL_STACK}.nip.io:8443
USERNAME="joesharp"
PASSWORD="password"

CERT_ROOT=./local/certs

curl -X POST \
  --write-out "HTTP Status: %{http_code}\n" \
  --key $CERT_ROOT/sports-day-client/sports-day-client.key \
  --cert $CERT_ROOT/sports-day-client/sports-day-client.crt \
  --cacert $CERT_ROOT/sportsday.crt \
  $AUTH_HOST/api/auth/login \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  -d "username=${USERNAME}" \
  -d "password=${PASSWORD}"
