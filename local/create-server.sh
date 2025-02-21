CA=$1
DOMAIN=$2

rm certs/$DOMAIN/*
mkdir -p certs/$DOMAIN

echo "Creating $DOMAIN"
openssl genrsa -out certs/$DOMAIN/$DOMAIN.key 4096
openssl rsa -in certs/$DOMAIN/$DOMAIN.key -pubout -out certs/$DOMAIN/$DOMAIN.pub.key

echo "Create $DOMAIN CSR"
openssl req \
-new -key certs/$DOMAIN/$DOMAIN.key \
-out certs/$DOMAIN/$DOMAIN.csr \
-subj "/C=GB/ST=London/L=London/O=ratracejoe/OU=BX/CN=$DOMAIN.${LOCAL_STACK}.nip.io"

echo "Generating $DOMAIN CSR"
cat > certs/$DOMAIN/$DOMAIN.ext << EOF
authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names
[alt_names]
DNS.1 = localhost
DNS.2 = $DOMAIN.${LOCAL_STACK}.nip.io
IP.1 = ${LOCAL_STACK}
EOF

echo "Signing $DOMAIN Certificate"
openssl x509 \
-req -in certs/$DOMAIN/$DOMAIN.csr \
-CA certs/$CA.crt \
-CAkey certs/$CA.key \
-CAcreateserial \
-out certs/$DOMAIN/$DOMAIN.crt \
-days 365 \
-sha256 \
-extfile certs/$DOMAIN/$DOMAIN.ext

echo "Generating $DOMAIN p12"
openssl pkcs12 -export \
-in certs/$DOMAIN/$DOMAIN.crt \
-inkey certs/$DOMAIN/$DOMAIN.key \
-name $DOMAIN \
-out certs/$DOMAIN/$DOMAIN.p12 \
-passout pass:changeit

echo "Generating Java Key Store for $DOMAIN"
keytool -importkeystore \
  -noprompt \
  -deststorepass changeit \
  -destkeypass changeit \
  -destkeystore certs/$DOMAIN/$DOMAIN.keystore.jks \
  -srckeystore certs/$DOMAIN/$DOMAIN.p12 \
  -srcstoretype PKCS12 \
  -srcstorepass changeit

echo "Importing Certificate into Java Key Store for $DOMAIN"
keytool -importcert \
  -noprompt \
  -keystore certs/$DOMAIN/$DOMAIN.keystore.jks \
  -storepass changeit \
  -file certs/$DOMAIN/$DOMAIN.crt


