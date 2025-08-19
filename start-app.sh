#!/bin/bash
set -euo pipefail

echo "Starting Task Scheduler App..."
docker compose up -d --remove-orphans

sleep 5
echo "Application running!"
echo "Web app:   http://localhost:8080"
echo "Postgres:  localhost:5432 (user=taskuser, pass=taskpass, db=taskdb)"
echo "Data path: $(pwd)/pgdata (host folder persists)"
