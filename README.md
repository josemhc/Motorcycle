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

## Consultas SQL

``````
psql -h localhost -U admin -d postgres
``````

``````
\c postgres
``````

``````
\dt
``````

Obtener los cinco (5) productos más vendidos. La información que se debe
mostrar es: nombre del producto, marca y cantidad de ventas.

``````
SELECT 
    p.item AS nombre_producto,
    p.brand AS marca,
    SUM(sd.amount) AS cantidad_vendida
FROM sale_detail sd
JOIN products p ON sd.product_id = p.id
GROUP BY p.item, p.brand
ORDER BY cantidad_vendida DESC
LIMIT 5;
``````
Obtener la cantidad de productos de la marca “Suzuki” que vendió cada empleado.
La información que se debe mostrar es: nombre del empleado, suma de productos
vendidos, suma de dinero de los productos vendidos.
``````
SELECT 
    CONCAT(e.first_name, ' ', e.last_name) AS nombre_empleado,
    SUM(sd.amount) AS productos_vendidos,
    SUM(sd.amount * p.price) AS total_vendido
FROM sale s
JOIN employee e ON s.employee_id = e.id
JOIN sale_detail sd ON sd.sale_id = s.id
JOIN products p ON sd.product_id = p.id
WHERE p.brand = 'Suzuki'
GROUP BY e.id, e.first_name, e.last_name;
``````