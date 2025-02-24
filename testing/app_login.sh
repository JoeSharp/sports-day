USERNAME="joesharp"
PASSWORD="password"

curl -X POST \
  --write-out "HTTP Status: %{http_code}\n" \
  --key $CERT_ROOT/sports-day-client/sports-day-client.key \
  --cert $CERT_ROOT/sports-day-client/sports-day-client.crt \
  --cacert $CERT_ROOT/sportsday.crt \
  $SERVICE_HOST/api/auth/login \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  -d "username=${USERNAME}" \
  -d "password=${PASSWORD}"
