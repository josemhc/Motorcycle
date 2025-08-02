-- Datos de prueba para la tabla products
INSERT INTO products (codigo, item, descripcion, precio, marca) 
VALUES (1001, 1, 'Motocicleta deportiva 250cc', 13500.00, 'Yamaha');

INSERT INTO products (codigo, item, descripcion, precio, marca) 
VALUES (1002, 2, 'Motocicleta urbana 150cc', 8900.00, 'Honda');

INSERT INTO products (codigo, item, descripcion, precio, marca) 
VALUES (1003, 3, 'Motocicleta de turismo 500cc', 21000.00, 'Suzuki');

-- Datos de prueba para la tabla employee
INSERT INTO employee (document_type, document_number, first_name, last_name, birth_date, position, email, phone_number, address) 
VALUES ('CC', '1002003001', 'Carlos', 'Ramírez', '1985-04-12', 'Vendedor', 'carlos.ramirez@example.com', '3004567890', 'Calle 45 #12-34');

INSERT INTO employee (document_type, document_number, first_name, last_name, birth_date, position, email, phone_number, address) 
VALUES ('TI', '1122334455', 'Laura', 'Gómez', '1990-09-25', 'Gerente', 'laura.gomez@example.com', '3012345678', 'Carrera 7 #89-10');

INSERT INTO employee (document_type, document_number, first_name, last_name, birth_date, position, email, phone_number, address) 
VALUES ('CE', '9876543210', 'Pedro', 'Martínez', '1978-01-30', 'Mecánico', 'pedro.martinez@example.com', '3029988776', 'Av. 68 #22-50');
