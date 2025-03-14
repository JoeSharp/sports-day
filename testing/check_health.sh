
OUTPUT_FILE="$(dirname "$0")/tmp/health.json"
curl \
  --key $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.key \
  --cert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.crt \
  --cacert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  ${SERVICE_HOST}/api/actuator/health \
  -o $OUTPUT_FILE

jq -r '.' $OUTPUT_FILE
