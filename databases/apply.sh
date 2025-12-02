#!/bin/bash

echo "Applying PostgreSQL StatefulSets and Services..."

kubectl apply -f deployment.yml

kubectl apply -f services.yml

echo "All databases deployed successfully!"
echo ""
echo "Check status with:"
echo "  kubectl get pods"
echo "  kubectl get svc"
echo "  kubectl get pvc"
