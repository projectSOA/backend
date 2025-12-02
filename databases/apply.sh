#!/bin/bash

echo "Applying PostgreSQL StatefulSets and Services..."

kubectl apply -f databases-statefulsets.yml

kubectl apply -f databases-services.yml

echo "All databases deployed successfully!"
echo ""
echo "Check status with:"
echo "  kubectl get pods"
echo "  kubectl get svc"
echo "  kubectl get pvc"
