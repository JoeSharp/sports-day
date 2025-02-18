AUTH_HOST=http://sports-day-service.${LOCAL_STACK}.nip.io:8080
USERNAME="joesharp"
PASSWORD="password"

curl -X POST \
  --write-out "HTTP Status: %{http_code}\n" \
  $AUTH_HOST/auth/login \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  -d "username=${USERNAME}" \
  -d "password=${PASSWORD}"
