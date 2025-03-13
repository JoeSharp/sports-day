CA=$1
ENVIRONMENT=$2
ROOT_DIR=$3/${ENVIRONMENT}

rm -rf ${ROOT_DIR}/*.*

echo "Creating $CA CA"
openssl req \
-x509 \
-newkey ec -pkeyopt ec_paramgen_curve:prime256v1 \
-new -nodes \
-sha256 -days 1825 \
-out ${ROOT_DIR}/$CA.crt \
-keyout ${ROOT_DIR}/$CA.key \
-subj "/C=GB/ST=London/L=London/O=ratracejoe/OU=BX/CN=$CA"

echo "Generating Java Trust Store for CA $CA"
keytool -importcert \
  -noprompt \
  -keystore ${ROOT_DIR}/$CA.truststore.jks \
  -storepass changeit \
  -file ${ROOT_DIR}/$CA.crt


