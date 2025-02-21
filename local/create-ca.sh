CA=$1

rm -rf certs/*.*

echo "Creating $CA CA"
openssl req \
-x509 \
-newkey ec -pkeyopt ec_paramgen_curve:prime256v1 \
-new -nodes \
-sha256 -days 1825 \
-out certs/$CA.crt \
-keyout certs/$CA.key \
-subj "/C=GB/ST=London/L=London/O=ratracejoe/OU=BX/CN=$CA"

echo "Generating Java Trust Store for CA $CA"
keytool -importcert \
  -noprompt \
  -keystore certs/$CA.truststore.jks \
  -storepass changeit \
  -file certs/$CA.crt


