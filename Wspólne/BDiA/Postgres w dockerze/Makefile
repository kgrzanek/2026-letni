# Variables
DC := docker-compose
DOCKER_VOLUMES := $(HOME)/.docker-volumes/bdia

# Help
.PHONY: help
help:
	@echo "Available targets:"
	@echo ""
	@echo "  Docker:"
	@echo "    up                     Start postgres containers"
	@echo "    down                   Stop all containers"
	@echo "    psql-main              Connect to main postgres"
	@echo "    psql-test              Connect to test postgres"
	@echo ""
	@echo "  Destructive (use with caution):"
	@echo "    clean-postgres-volumes Remove postgres data volumes"

# ------------------------------------------------------------------------------
# DOCKER
# ------------------------------------------------------------------------------
.PHONY: up down psql-main psql-test

up:
	@echo "Starting docker containers..."
	@$(DC) up --remove-orphans -d bdia-postgres-main bdia-postgres-test

down:
	@echo "Stopping all containers..."
	@$(DC) down --remove-orphans

psql-main:
	@docker exec -it bdia-postgres-main psql -U postgres -d bdia

psql-test:
	@docker exec -it bdia-postgres-test psql -U postgres -d bdia

# Destructive targets - use with caution
.PHONY: clean-postgres-volumes

clean-postgres-volumes:
	@echo "Removing postgres volumes..."
	@sudo rm -rf $(DOCKER_VOLUMES)/postgres-main
	@sudo rm -rf $(DOCKER_VOLUMES)/postgres-test
