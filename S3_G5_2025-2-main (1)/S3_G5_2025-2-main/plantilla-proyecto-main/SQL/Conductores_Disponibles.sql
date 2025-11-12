/* =========================================================
   SCRIPT PARA CREAR 10 CONDUCTORES CON DISPONIBILIDADES
   TODOS LOS DÍAS 6AM A 10PM
   ========================================================= */

-- Insertar 10 conductores con sus usuarios
BEGIN
  -- Conductor 1
  INSERT INTO USUARIO VALUES (10001, 'Carlos Mendez', 'carlos.mendez@mail.com', '3001234001');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10001);
  INSERT INTO VEHICULO VALUES ('CAR0001', 'Toyota', 'Corolla', 'Blanco', 4, 10001, 1, 1, 1);
  
  -- Conductor 2
  INSERT INTO USUARIO VALUES (10002, 'Juan Rodriguez', 'juan.rodriguez@mail.com', '3001234002');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10002);
  INSERT INTO VEHICULO VALUES ('CAR0002', 'Hyundai', 'Elantra', 'Gris', 4, 10002, 1, 1, 1);
  
  -- Conductor 3
  INSERT INTO USUARIO VALUES (10003, 'Miguel Torres', 'miguel.torres@mail.com', '3001234003');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10003);
  INSERT INTO VEHICULO VALUES ('CAR0003', 'Ford', 'Fiesta', 'Negro', 4, 10003, 1, 1, 1);
  
  -- Conductor 4
  INSERT INTO USUARIO VALUES (10004, 'Pablo Sanchez', 'pablo.sanchez@mail.com', '3001234004');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10004);
  INSERT INTO VEHICULO VALUES ('CAR0004', 'Chevrolet', 'Aveo', 'Rojo', 4, 10004, 1, 1, 1);
  
  -- Conductor 5
  INSERT INTO USUARIO VALUES (10005, 'Diego Ramirez', 'diego.ramirez@mail.com', '3001234005');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10005);
  INSERT INTO VEHICULO VALUES ('CAR0005', 'Mazda', 'Mazda3', 'Azul', 4, 10005, 1, 1, 1);
  
  -- Conductor 6
  INSERT INTO USUARIO VALUES (10006, 'Fernando Lopez', 'fernando.lopez@mail.com', '3001234006');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10006);
  INSERT INTO VEHICULO VALUES ('CAR0006', 'Kia', 'Rio', 'Plata', 4, 10006, 1, 1, 1);
  
  -- Conductor 7
  INSERT INTO USUARIO VALUES (10007, 'Ricardo Gutierrez', 'ricardo.gutierrez@mail.com', '3001234007');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10007);
  INSERT INTO VEHICULO VALUES ('CAR0007', 'Nissan', 'Sentra', 'Blanco', 4, 10007, 1, 1, 1);
  
  -- Conductor 8
  INSERT INTO USUARIO VALUES (10008, 'Alfonso Mora', 'alfonso.mora@mail.com', '3001234008');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10008);
  INSERT INTO VEHICULO VALUES ('CAR0008', 'Volkswagen', 'Polo', 'Negro', 4, 10008, 1, 1, 1);
  
  -- Conductor 9
  INSERT INTO USUARIO VALUES (10009, 'Roberto Soto', 'roberto.soto@mail.com', '3001234009');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10009);
  INSERT INTO VEHICULO VALUES ('CAR0009', 'Renault', 'Dacia', 'Gris', 5, 10009, 2, 1, 1);
  
  -- Conductor 10
  INSERT INTO USUARIO VALUES (10010, 'Angel Vargas', 'angel.vargas@mail.com', '3001234010');
  INSERT INTO USUARIO_CONDUCTOR VALUES (10010);
  INSERT INTO VEHICULO VALUES ('CAR0010', 'Peugeot', '308', 'Rojo', 5, 10010, 2, 1, 1);
  
  COMMIT;
END;
/

-- Crear disponibilidades para cada conductor (7 días x 10 conductores = 70 registros)
DECLARE
  v_disp_id NUMBER := 1000;
  v_conductor_id NUMBER;
  v_day NUMBER;
  v_placa VARCHAR2(10);
  v_hora_inicio TIMESTAMP;
  v_hora_fin TIMESTAMP;
BEGIN
  -- Loops para cada conductor
  FOR cond_idx IN 1..10 LOOP
    v_conductor_id := 10000 + cond_idx;
    v_placa := 'CAR' || LPAD(cond_idx, 4, '0');
    
    -- Loops para cada día de la semana (1=Lunes a 7=Domingo)
    FOR day_idx IN 1..7 LOOP
      v_day := day_idx;
      v_hora_inicio := TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS');
      v_hora_fin := TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS');
      
      INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
      VALUES (v_disp_id, v_day, v_hora_inicio, v_hora_fin, v_conductor_id, v_placa);
      
      v_disp_id := v_disp_id + 1;
    END LOOP;
  END LOOP;
  
  COMMIT;
END;
/

-- Verificar que se insertaron correctamente
SELECT COUNT(*) as total_conductores FROM USUARIO_CONDUCTOR WHERE id_usuario >= 10001 AND id_usuario <= 10010;
SELECT COUNT(*) as total_vehiculos FROM VEHICULO WHERE id_usuario >= 10001 AND id_usuario <= 10010;
SELECT COUNT(*) as total_disponibilidades FROM DISPONIBILIDAD WHERE id_usuario >= 10001 AND id_usuario <= 10010;

-- Ver disponibilidades de ejemplo
SELECT d.id_disponibilidad, u.nombre, d.dia_semana, d.hora_inicio, d.hora_fin
FROM DISPONIBILIDAD d
JOIN USUARIO u ON d.id_usuario = u.cedula
WHERE d.id_usuario >= 10001 AND d.id_usuario <= 10010
ORDER BY d.id_usuario, d.dia_semana;
