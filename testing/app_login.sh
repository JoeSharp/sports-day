USERNAME="joesharp"
PASSWORD="password"

curl -X POST \
  --write-out "HTTP Status: %{http_code}\n" \
  --key $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/sports-day-client.key \
  --cert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/sports-day-client.crt \
  --cacert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}.crt \
  $SERVICE_HOST/api/auth/login \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  -d "username=${USERNAME}" \
  -d "password=${PASSWORD}"
