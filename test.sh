#!/bin/bash
VPS=task.afric.ca

# 1. Récupérer un token JWT
echo "=== 1. Obtenir le token ==="
TOKEN=$(curl -s -X POST \
  http://$VPS:8080/realms/eventflow/protocol/openid-connect/token \
  -d "client_id=api-gateway&grant_type=password&username=alice&password=alice123" \
  | python3 -c "import sys,json; print(json.load(sys.stdin)['access_token'])")
echo "Token OK"

# 2. Créer une commande
echo "=== 2. Créer une commande ==="
RESPONSE=$(curl -s -X POST http://$VPS:8081/orders \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"productId":"PROD-001","quantity":3}')
echo $RESPONSE

# 3. Vérifier le stock (après 2s)
sleep 2
echo "=== 3. Vérifier le stock ==="
curl -s http://$VPS:8082/stock/PROD-001 \
  -H "Authorization: Bearer $TOKEN"
echo ""

# 4. Voir les logs de notification
echo "=== 4. Logs notification ==="
docker -H ssh://root@$VPS compose \
  -f /opt/eventflow/docker-compose.yml \
  logs notification-service | tail -5