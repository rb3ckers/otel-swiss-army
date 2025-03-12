#!/usr/bin/env bash

mkdir -p lib

if ! [ -f "lib/opentelemetry-javaagent.jar" ]; then
  curl -L -o lib/opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
fi

export JAVA_TOOL_OPTIONS="-javaagent:lib/opentelemetry-javaagent.jar"
export OTEL_SERVICE_NAME="dice-demo"
export OTEL_RESOURCE_ATTRIBUTES="service.namespace=dice-demo"
java -jar ./build/libs/java-demo.jar
