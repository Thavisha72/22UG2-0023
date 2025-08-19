#!/bin/bash
set -euo pipefail

echo "Stopping containers (data preserved in ./pgdata)..."
docker compose stop
echo "Stopped."
