#!/usr/bin/env bash

PASSWORD="changeit"
ROOT_CA_CRT="rootCA.crt"
ROOT_CA_KEY="rootCA.key"
SANS="DNS:tablebooking.dragonmadness.crazedns.ru,IP:109.63.148.191"

if [[ ! -f "secrets/$ROOT_CA_CRT" || ! -f "secrets/$ROOT_CA_KEY" ]]; then
  echo "Provide root CA for cert gen in the secrets/ dir"
  exit 1
fi

cd secrets

openssl req -newkey rsa:4096 -nodes -keyout app.key -out app.csr \
  -subj "//CN=dragonmadness-home.crazedns.ru" \
  -addext "subjectAltName=$SANS"
openssl x509 -req -in app.csr -CA rootCA.crt -CAkey rootCA.key -CAcreateserial -out app.crt -days 365
openssl pkcs12 -export -out app.p12 -inkey app.key -in app.crt -certfile rootCA.crt -passout pass:$PASSWORD

rm app.csr app.key app.crt