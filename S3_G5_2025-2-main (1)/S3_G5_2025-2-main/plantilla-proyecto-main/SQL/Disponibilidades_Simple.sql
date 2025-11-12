/* =========================================================
   SCRIPT ALTERNATIVO PARA CREAR DISPONIBILIDADES
   MÉTODO: INSERT directo sin PL/SQL
   ========================================================= */

-- Método simple: Insertar disponibilidades usando INSERT statements directos
-- Para cada conductor (10001-10010) y cada día (1-7)

-- Conductor 1: Carlos Mendez - CAR0001
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1000, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10001, 'CAR0001' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1001, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10001, 'CAR0001' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1002, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10001, 'CAR0001' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1003, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10001, 'CAR0001' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1004, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10001, 'CAR0001' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1005, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10001, 'CAR0001' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1006, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10001, 'CAR0001' FROM DUAL;

-- Conductor 2: Juan Rodriguez - CAR0002
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1007, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10002, 'CAR0002' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1008, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10002, 'CAR0002' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1009, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10002, 'CAR0002' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1010, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10002, 'CAR0002' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1011, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10002, 'CAR0002' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1012, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10002, 'CAR0002' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1013, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10002, 'CAR0002' FROM DUAL;

-- Conductor 3: Miguel Torres - CAR0003
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1014, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10003, 'CAR0003' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1015, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10003, 'CAR0003' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1016, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10003, 'CAR0003' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1017, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10003, 'CAR0003' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1018, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10003, 'CAR0003' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1019, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10003, 'CAR0003' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1020, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10003, 'CAR0003' FROM DUAL;

-- Conductor 4: Pablo Sanchez - CAR0004
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1021, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10004, 'CAR0004' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1022, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10004, 'CAR0004' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1023, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10004, 'CAR0004' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1024, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10004, 'CAR0004' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1025, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10004, 'CAR0004' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1026, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10004, 'CAR0004' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1027, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10004, 'CAR0004' FROM DUAL;

-- Conductor 5: Diego Ramirez - CAR0005
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1028, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10005, 'CAR0005' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1029, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10005, 'CAR0005' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1030, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10005, 'CAR0005' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1031, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10005, 'CAR0005' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1032, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10005, 'CAR0005' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1033, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10005, 'CAR0005' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1034, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10005, 'CAR0005' FROM DUAL;

-- Conductor 6: Fernando Lopez - CAR0006
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1035, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10006, 'CAR0006' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1036, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10006, 'CAR0006' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1037, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10006, 'CAR0006' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1038, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10006, 'CAR0006' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1039, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10006, 'CAR0006' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1040, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10006, 'CAR0006' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1041, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10006, 'CAR0006' FROM DUAL;

-- Conductor 7: Ricardo Gutierrez - CAR0007
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1042, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10007, 'CAR0007' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1043, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10007, 'CAR0007' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1044, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10007, 'CAR0007' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1045, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10007, 'CAR0007' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1046, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10007, 'CAR0007' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1047, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10007, 'CAR0007' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1048, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10007, 'CAR0007' FROM DUAL;

-- Conductor 8: Alfonso Mora - CAR0008
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1049, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10008, 'CAR0008' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1050, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10008, 'CAR0008' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1051, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10008, 'CAR0008' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1052, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10008, 'CAR0008' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1053, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10008, 'CAR0008' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1054, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10008, 'CAR0008' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1055, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10008, 'CAR0008' FROM DUAL;

-- Conductor 9: Roberto Soto - CAR0009
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1056, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10009, 'CAR0009' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1057, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10009, 'CAR0009' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1058, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10009, 'CAR0009' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1059, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10009, 'CAR0009' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1060, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10009, 'CAR0009' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1061, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10009, 'CAR0009' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1062, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10009, 'CAR0009' FROM DUAL;

-- Conductor 10: Angel Vargas - CAR0010
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1063, 1, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10010, 'CAR0010' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1064, 2, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10010, 'CAR0010' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1065, 3, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10010, 'CAR0010' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1066, 4, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10010, 'CAR0010' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1067, 5, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10010, 'CAR0010' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1068, 6, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10010, 'CAR0010' FROM DUAL;
INSERT INTO DISPONIBILIDAD (id_disponibilidad, dia_semana, hora_inicio, hora_fin, id_usuario, id_vehiculo)
SELECT 1069, 7, TO_TIMESTAMP('2025-11-03 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2025-11-03 22:00:00', 'YYYY-MM-DD HH24:MI:SS'), 10010, 'CAR0010' FROM DUAL;

COMMIT;

-- Verificar que se insertaron correctamente
SELECT COUNT(*) as total_disponibilidades FROM DISPONIBILIDAD WHERE id_usuario >= 10001 AND id_usuario <= 10010;

-- Ver disponibilidades de ejemplo (primeras 10)
SELECT d.id_disponibilidad, u.nombre, d.dia_semana, d.hora_inicio, d.hora_fin
FROM DISPONIBILIDAD d
JOIN USUARIO u ON d.id_usuario = u.cedula
WHERE d.id_usuario >= 10001 AND d.id_usuario <= 10010
ORDER BY d.id_usuario, d.dia_semana
FETCH FIRST 14 ROWS ONLY;
