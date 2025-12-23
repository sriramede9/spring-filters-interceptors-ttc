services:
  # The PostgreSQL Instance (Relational)
  postgres-db:
    image: postgres:15
    container_name: transit-postgres
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
      POSTGRES_DB: transit_db
    ports:
      - "5432:5432"

  # The MongoDB Instance (NoSQL)
  mongodb:
    image: mongo:latest
    container_name: transit-mongo
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: password

  # Your Spring Boot Application
  ttc-service:
    build: .
    container_name: ttc-app
    ports:
      - "8080:8080"
    depends_on:
      - postgres-db
      - mongodb
    environment:
      # These override application.properties when running in Docker
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres-db:5432/transit_db
      SPRING_DATA_MONGODB_URI: mongodb://admin:password@mongodb:27017/transit_mongo?authSource=admin