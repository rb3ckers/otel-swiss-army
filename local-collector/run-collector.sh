#!/usr/bin/env bash

# Set api key with export API_KEY=""

docker run -p 4317:4317 -p 4318:4318 -e API_KEY="$API_KEY" --network=host -v/:/hostfs -v $(pwd)/collector-config.yaml:/etc/otelcol-contrib/config.yaml otel/opentelemetry-collector-contrib:0.121.0
