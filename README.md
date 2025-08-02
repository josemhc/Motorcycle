# Aplicacion Web Springboot - Postgres

## Correr la aplicacion


### Crear y levantar el contenedor de la base de datos postgres
``````
cd Motorcycle
docker compose up -d
``````

### Correr springboot
``````
./mvnw spring-boot:run
``````
# Antes de probar la API

### Insertar los datos iniciales
``````
cd Motorcycle

psql -h localhost -p 5432 -U admin -d postgres -f ./PruebaTecnicaMotocicletas/src/main/resources/data.sql
``````