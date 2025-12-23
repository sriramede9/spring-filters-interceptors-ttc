INSERT INTO engines (serial_number) VALUES ('V8-TURBO-9901');
INSERT INTO engines (serial_number) VALUES ('ELECTRIC-GEN2-44');

SELECT * FROM engines;

-- Mapping Bus 1 to Engine 1
INSERT INTO buses (engine_id) VALUES (1);

-- Mapping Bus 2 to Engine 2
INSERT INTO buses (engine_id) VALUES (2);

select * from buses;