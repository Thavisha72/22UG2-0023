#!/bin/bash
set -euo pipefail

echo "Removing containers, images, and networks..."
docker compose down --rmi all --remove-orphans || true

if docker network inspect myapp-net >/dev/null 2>&1; then
  docker network rm myapp-net || true
fi

read -p "Delete local pgdata folder (DB will be lost)? (y/N) " yn
if [[ "$yn" == "y" || "$yn" == "Y" ]]; then
  rm -rf ./pgdata
  echo "pgdata deleted."
else
  echo "pgdata preserved at $(pwd)/pgdata"
fi
