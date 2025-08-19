#!/bin/bash
set -euo pipefail

echo "Preparing Task Scheduler App..."

# Create network
if ! docker network inspect myapp-net >/dev/null 2>&1; then
  echo "Creating docker network: myapp-net"
  docker network create myapp-net
fi

# Create persistent folder for Postgres (host storage)
PGDATA_HOST="./pgdata"
if [ ! -d "$PGDATA_HOST" ]; then
  echo "Creating local folder for PostgreSQL data: $PGDATA_HOST"
  mkdir -p "$PGDATA_HOST"
fi
# Make sure Postgres can write to it
chmod 777 "$PGDATA_HOST" || true

# Build Docker images (Java app)
docker compose build

echo "Preparation complete."
