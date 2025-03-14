ACCESS_TOKEN=$("$(dirname "$0")/keycloak_login.sh")

ACTIVITY_ID=$1
curl \
  --key $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.key \
  --cert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}-client/${APPLICATION_NAME}-client.crt \
  --cacert $CERT_ROOT/${ENVIRONMENT}/${APPLICATION_NAME}.crt \
  --write-out "HTTP Status: %{http_code}\n" \
  --header "Authorization: Bearer ${ACCESS_TOKEN}" \
  --request DELETE \
  ${SERVICE_HOST}/api/activities/${ACTIVITY_ID}
