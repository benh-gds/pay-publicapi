#!/usr/bin/env bash

if [ "${ENABLE_NEWRELIC}" == "yes" ]; then
  NEWRELIC_JVM_FLAG="-javaagent:/app/newrelic/newrelic.jar"
fi

# create cacerts from supplied certificates
for crt in `ls -1 $CA_FILEPATH/*.crt`; do
  echo "Found $crt"
  openssl x509 -outform der -in $crt | keytool -import -alias $crt -keystore /app/ssl/cacerts -storepass password -noprompt
done

java ${NEWRELIC_JVM_FLAG} \
 -Djavax.net.ssl.keyStore=/app/ssl/cacerts \
 -Djavax.net.ssl.keyStorePassword=password \
 -jar *-allinone.jar server *.yaml
