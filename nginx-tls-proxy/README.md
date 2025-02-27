# Simple TLS Proxy

This is a standard form for using NGINX as a simple TLS termination reverse proxy.

When running, it expects the following environment variables
* PROXY_PASS: The address to forward to (HTTP)
* SERVER_NAME: Simply maps to server -> server_name in the nginx conf file
* SSL_CERTIFICATE: location of the cert file (will need to be in a mapped volume)
* SSL_CERTIFICATE_KEY: location of the private key associated with the cert file (will need to be in a mapped volume)
