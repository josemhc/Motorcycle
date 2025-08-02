-- =============================
-- Customers
-- =============================
INSERT INTO customer (document_type, document_number, first_name, last_name, email, phone_number)
VALUES
('CC', '1001001001', 'Juan', 'Pérez', 'juanp@example.com', '3001234567'),
('TI', '1020304050', 'Laura', 'Martínez', 'lauram@example.com', '3107654321'),
('CC', '1100220033', 'Carlos', 'Ramírez', 'carlosr@example.com', '3209988776'),
('CE', '9998887776', 'Tatiana', 'Gómez', 'tatianag@example.com', '3001112233');

-- =============================
-- Employees
-- =============================
INSERT INTO employee (document_type, document_number, first_name, last_name, birth_date, position, email, phone_number, address)
VALUES
('CC', '2002002002', 'Andrés', 'Soto', '1988-05-10', 'Vendedor', 'andress@example.com', '3112233445', 'Calle 60 #11-22'),
('CC', '3003003003', 'Natalia', 'Quintero', '1990-03-22', 'Administrador', 'nataliaq@example.com', '3123344556', 'Cra 25 #45-67'),
('TI', '4004004004', 'Manuel', 'Ortiz', '1994-06-18', 'Auxiliar', 'manuelo@example.com', '3134455667', 'Calle 80 #33-44'),
('CE', '5005005005', 'Diana', 'Salazar', '1982-09-05', 'Gerente', 'dianas@example.com', '3145566778', 'Av. Central 123');

-- =============================
-- Products
-- =============================
INSERT INTO products (product_code, item, description, price, brand)
VALUES
('MOTO001', 101, 'Motocicleta 125cc deportiva', 4200000, 'Yamaha'),
('MOTO002', 102, 'Motocicleta 150cc urbana', 5200000, 'Suzuki'),
('CASCO01', 201, 'Casco integral negro', 180000, 'Shark'),
('GUANT01', 301, 'Guantes de cuero', 80000, 'Alpinestars');

-- =============================
-- Sales
-- =============================
-- Suponiendo que los IDs autogenerados coinciden con el orden de inserción
INSERT INTO sale (total, customer_id, employee_id)
VALUES
(4260000, 1, 1), -- Juan compra la Yamaha con casco
(5280000, 2, 2), -- Laura compra Suzuki con guantes
(4200000, 3, 3), -- Carlos compra Yamaha sola
(5300000, 4, 4); -- Tatiana compra Suzuki + casco + guantes

-- =============================
-- SaleDetails
-- =============================
-- Sale 1: MOTO001 (Yamaha), CASCO01 (Casco)
INSERT INTO sale_detail (amount, unit_price, sale_id, product_id)
VALUES
(1, 4200000, 1, 1),
(1, 60000, 1, 3);

-- Sale 2: MOTO002 (Suzuki), GUANT01 (Guantes)
INSERT INTO sale_detail (amount, unit_price, sale_id, product_id)
VALUES
(1, 5200000, 2, 2),
(1, 80000, 2, 4);

-- Sale 3: Solo Yamaha
INSERT INTO sale_detail (amount, unit_price, sale_id, product_id)
VALUES
(1, 4200000, 3, 1);

-- Sale 4: Suzuki, Casco, Guantes
INSERT INTO sale_detail (amount, unit_price, sale_id, product_id)
VALUES
(1, 5200000, 4, 2),
(1, 180000, 4, 3),
(1, 80000, 4, 4);