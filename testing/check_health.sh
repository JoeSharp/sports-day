curl \
  --key $CERT_ROOT/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.key \
  --cert $CERT_ROOT/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.crt \
  --cacert $CERT_ROOT/${APPLICATION_NAME}.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  ${SERVICE_HOST}/api/actuator/health \
  -o "$(dirname "$0")/tmp/health.json"

jq -r '.' "$(dirname "$0")/tmp/health.json"
