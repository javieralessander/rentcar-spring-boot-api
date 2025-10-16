-- Datos iniciales para Derby RentCar Database

-- Tipos de Vehículos
INSERT INTO tipos_vehiculos (descripcion, estado) VALUES ('Automóvil', true);
INSERT INTO tipos_vehiculos (descripcion, estado) VALUES ('SUV', true);
INSERT INTO tipos_vehiculos (descripcion, estado) VALUES ('Camioneta', true);
INSERT INTO tipos_vehiculos (descripcion, estado) VALUES ('Motocicleta', true);

-- Tipos de Combustibles
INSERT INTO tipos_combustibles (descripcion, estado) VALUES ('Gasolina', true);
INSERT INTO tipos_combustibles (descripcion, estado) VALUES ('Diésel', true);
INSERT INTO tipos_combustibles (descripcion, estado) VALUES ('Híbrido', true);
INSERT INTO tipos_combustibles (descripcion, estado) VALUES ('Eléctrico', true);

-- Marcas
INSERT INTO marcas (descripcion, estado) VALUES ('Toyota', true);
INSERT INTO marcas (descripcion, estado) VALUES ('Honda', true);
INSERT INTO marcas (descripcion, estado) VALUES ('Nissan', true);
INSERT INTO marcas (descripcion, estado) VALUES ('Hyundai', true);
INSERT INTO marcas (descripcion, estado) VALUES ('Ford', true);

-- Modelos
INSERT INTO modelos (descripcion, marca_id, estado) VALUES ('Corolla', 1, true);
INSERT INTO modelos (descripcion, marca_id, estado) VALUES ('Camry', 1, true);
INSERT INTO modelos (descripcion, marca_id, estado) VALUES ('Civic', 2, true);
INSERT INTO modelos (descripcion, marca_id, estado) VALUES ('Accord', 2, true);
INSERT INTO modelos (descripcion, marca_id, estado) VALUES ('Sentra', 3, true);

-- Empleados
INSERT INTO empleados (nombre, cedula, tanda_labor, porciento_comision, fecha_ingreso, estado) VALUES ('Carlos Rodríguez', '001-1234567-8', 'MATUTINA', 5.50, '2023-01-15', true);
INSERT INTO empleados (nombre, cedula, tanda_labor, porciento_comision, fecha_ingreso, estado) VALUES ('Ana Martínez', '001-2345678-9', 'VESPERTINA', 6.00, '2023-02-10', true);

-- Clientes
INSERT INTO clientes (nombre, cedula, tipo_persona, no_tarjeta_cr, limite_credito, estado) VALUES ('Juan Pérez', '001-0123456-7', 'FISICA', 'TC001234', 50000.00, true);
INSERT INTO clientes (nombre, cedula, tipo_persona, no_tarjeta_cr, limite_credito, estado) VALUES ('María García', '001-0234567-8', 'FISICA', 'TC002345', 75000.00, true);
INSERT INTO clientes (nombre, cedula, tipo_persona, no_tarjeta_cr, limite_credito, estado) VALUES ('Empresa ABC S.R.L.', '131-123456-7', 'JURIDICA', 'TC003456', 150000.00, true);

-- Vehículos
INSERT INTO vehiculos (descripcion, no_placa, no_chasis, no_motor, tipo_vehiculo_id, marca_id, modelo_id, tipo_combustible_id, estado) VALUES ('Toyota Corolla 2022', 'A123456', 'CH001234567', 'MT001234', 1, 1, 1, 1, true);
INSERT INTO vehiculos (descripcion, no_placa, no_chasis, no_motor, tipo_vehiculo_id, marca_id, modelo_id, tipo_combustible_id, estado) VALUES ('Honda Civic 2023', 'B234567', 'CH002345678', 'MT002345', 1, 2, 3, 1, true);
INSERT INTO vehiculos (descripcion, no_placa, no_chasis, no_motor, tipo_vehiculo_id, marca_id, modelo_id, tipo_combustible_id, estado) VALUES ('Toyota Camry 2022', 'C345678', 'CH003456789', 'MT003456', 1, 1, 2, 1, true);