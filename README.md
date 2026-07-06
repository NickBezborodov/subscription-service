# 1. Поднять инфраструктуру
docker-compose up -d

# 2. Создать БД для Subscription Service
docker exec -it flowmanager-postgres psql -U postgres -c "CREATE DATABASE subscription_db;"

# 3. Запустить Subscription Service
cd subscription-service
mvn spring-boot:run

# 4. Запустить MCS
cd ../mcs
mvn spring-boot:run

# 5. Запустить FlowManager
cd ../flowmanager
mvn spring-boot:run

# 6. Запустить API Gateway
cd ../apigateway
mvn spring-boot:run
