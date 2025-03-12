#! /usr/bin/env bash

dir=$(dirname "$0")

helm upgrade --install otel-demo open-telemetry/opentelemetry-demo -f "$dir/values.yaml"

kubectl apply -f custom-ingress.yaml
