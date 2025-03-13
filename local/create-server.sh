CA=$1
DOMAIN=$2
ROOT_DIR=$3

rm ${ROOT_DIR}/$DOMAIN/*
mkdir -p ${ROOT_DIR}/$DOMAIN

echo "Creating $DOMAIN"
openssl genrsa -out ${ROOT_DIR}/$DOMAIN/$DOMAIN.key 4096
openssl rsa -in ${ROOT_DIR}/$DOMAIN/$DOMAIN.key -pubout -out ${ROOT_DIR}/$DOMAIN/$DOMAIN.pub.key

echo "Create $DOMAIN CSR"
openssl req \
-new -key ${ROOT_DIR}/$DOMAIN/$DOMAIN.key \
-out ${ROOT_DIR}/$DOMAIN/$DOMAIN.csr \
-subj "/C=GB/ST=London/L=London/O=ratracejoe/OU=BX/CN=$DOMAIN.${LOCAL_STACK_HOST}.nip.io"

echo "Generating $DOMAIN CSR"
cat > ${ROOT_DIR}/$DOMAIN/$DOMAIN.ext << EOF
authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names
[alt_names]
DNS.1 = localhost
DNS.2 = $DOMAIN.${LOCAL_STACK_HOST}.nip.io
DNS.3 = $DOMAIN.local
IP.1 = ${LOCAL_STACK_HOST}
EOF

echo "Signing $DOMAIN Certificate"
openssl x509 \
-req -in ${ROOT_DIR}/$DOMAIN/$DOMAIN.csr \
-CA ${ROOT_DIR}/$CA.crt \
-CAkey ${ROOT_DIR}/$CA.key \
-CAcreateserial \
-out ${ROOT_DIR}/$DOMAIN/$DOMAIN.crt \
-days 365 \
-sha256 \
-extfile ${ROOT_DIR}/$DOMAIN/$DOMAIN.ext

echo "Generating $DOMAIN p12"
openssl pkcs12 -export \
-in ${ROOT_DIR}/$DOMAIN/$DOMAIN.crt \
-inkey ${ROOT_DIR}/$DOMAIN/$DOMAIN.key \
-name $DOMAIN \
-out ${ROOT_DIR}/$DOMAIN/$DOMAIN.p12 \
-passout pass:changeit

echo "Generating Java Key Store for $DOMAIN"
keytool -importkeystore \
  -noprompt \
  -deststorepass changeit \
  -destkeypass changeit \
  -destkeystore ${ROOT_DIR}/$DOMAIN/$DOMAIN.keystore.jks \
  -srckeystore ${ROOT_DIR}/$DOMAIN/$DOMAIN.p12 \
  -srcstoretype PKCS12 \
  -srcstorepass changeit

echo "Importing Certificate into Java Key Store for $DOMAIN"
keytool -importcert \
  -noprompt \
  -keystore ${ROOT_DIR}/$DOMAIN/$DOMAIN.keystore.jks \
  -storepass changeit \
  -file ${ROOT_DIR}/$DOMAIN/$DOMAIN.crt


