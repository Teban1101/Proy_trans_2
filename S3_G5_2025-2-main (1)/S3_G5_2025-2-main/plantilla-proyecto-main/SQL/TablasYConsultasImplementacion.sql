/* =========================================================
   1) CIUDAD
   ========================================================= */
CREATE TABLE CIUDAD (
  id_ciudad NUMBER(10) PRIMARY KEY,
  nombre    VARCHAR2(100) NOT NULL
);

/* =========================================================
   2) USUARIO
   ========================================================= */
CREATE TABLE USUARIO (
  cedula   NUMBER(10)    PRIMARY KEY,
  nombre   VARCHAR2(100) NOT NULL,
  correo   VARCHAR2(150),
  celular  VARCHAR2(20)  NOT NULL
);

/* =========================================================
   3) USUARIO_SERVICIO
   ========================================================= */
CREATE TABLE USUARIO_SERVICIO (
  id_usuario NUMBER(10) PRIMARY KEY,
  CONSTRAINT fk_ususerv_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO(cedula)
);

/* =========================================================
   4) USUARIO_CONDUCTOR
   ========================================================= */
CREATE TABLE USUARIO_CONDUCTOR (
  id_usuario NUMBER(10) PRIMARY KEY,
  CONSTRAINT fk_usucond_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO(cedula)
);

/* =========================================================
   5) TIPO_VEHICULO
   ========================================================= */
CREATE TABLE TIPO_VEHICULO (
  id_tipo_vehiculo NUMBER(10) PRIMARY KEY,
  nombre           VARCHAR2(50) NOT NULL,
  CONSTRAINT uq_tipo_vehiculo_nombre UNIQUE (nombre)
);

/* =========================================================
   6) NIVEL_TRANSPORTE
   ========================================================= */
CREATE TABLE NIVEL_TRANSPORTE (
  id_nivel NUMBER(10) PRIMARY KEY,
  nombre   VARCHAR2(50) NOT NULL,
  CONSTRAINT uq_nivel_transporte_nombre UNIQUE (nombre)
);

/* =========================================================
   7) TIPO_SERVICIO
   ========================================================= */
CREATE TABLE TIPO_SERVICIO (
  id_tipo_servicio NUMBER(10) PRIMARY KEY,
  nombre           VARCHAR2(50) NOT NULL,
  CONSTRAINT uq_tipo_servicio_nombre UNIQUE (nombre)
);

/* =========================================================
   8) PUNTO_GEOGRAFICO
   ========================================================= */
CREATE TABLE PUNTO_GEOGRAFICO (
  id_punto  NUMBER(10)    PRIMARY KEY,
  nombre    VARCHAR2(100) NOT NULL,
  direccion VARCHAR2(150) NOT NULL,
  latitud   NUMBER(9,6)   NOT NULL,
  longitud  NUMBER(9,6)   NOT NULL
);

/* =========================================================
   9) VEHICULO
   ========================================================= */
CREATE TABLE VEHICULO (
  placa            VARCHAR2(10) PRIMARY KEY,
  marca            VARCHAR2(50) NOT NULL,
  modelo           VARCHAR2(50) NOT NULL,
  color            VARCHAR2(30) NOT NULL,
  capacidad        NUMBER(3)    NOT NULL,
  id_usuario       NUMBER(10)   NOT NULL,
  id_tipo_vehiculo NUMBER(10)   NOT NULL,
  id_nivel         NUMBER(10),
  id_ciudad        NUMBER(10)   NOT NULL,
  CONSTRAINT ck_veh_capacidad CHECK (capacidad >= 1),
  CONSTRAINT fk_veh_usuario  FOREIGN KEY (id_usuario)       REFERENCES USUARIO(cedula),
  CONSTRAINT fk_veh_tipovec  FOREIGN KEY (id_tipo_vehiculo) REFERENCES TIPO_VEHICULO(id_tipo_vehiculo),
  CONSTRAINT fk_veh_nivel    FOREIGN KEY (id_nivel)         REFERENCES NIVEL_TRANSPORTE(id_nivel),
  CONSTRAINT fk_veh_ciudad   FOREIGN KEY (id_ciudad)        REFERENCES CIUDAD(id_ciudad)
);

/* =========================================================
   10) DISPONIBILIDAD
   ========================================================= */
CREATE TABLE DISPONIBILIDAD (
  id_disponibilidad NUMBER(10) PRIMARY KEY,
  dia_semana  NUMBER(1)  NOT NULL,
  hora_inicio TIMESTAMP  NOT NULL,
  hora_fin    TIMESTAMP  NOT NULL,
  id_usuario  NUMBER(10) NOT NULL,
  id_vehiculo VARCHAR2(10) NOT NULL,
  CONSTRAINT ck_disp_dia   CHECK (dia_semana BETWEEN 1 AND 7),
  CONSTRAINT ck_disp_horas CHECK (hora_fin > hora_inicio),
  CONSTRAINT fk_disp_usuario  FOREIGN KEY (id_usuario)  REFERENCES USUARIO(cedula),
  CONSTRAINT fk_disp_vehiculo FOREIGN KEY (id_vehiculo) REFERENCES VEHICULO(placa)
);

/* =========================================================
   11) TARJETA_CREDITO
   ========================================================= */
CREATE TABLE TARJETA_CREDITO (
  numero         VARCHAR2(20)  PRIMARY KEY,
  nombre_tarjeta VARCHAR2(100) NOT NULL,
  vencimiento    DATE          NOT NULL,
  cvv            NUMBER(4)     NOT NULL,
  id_usuario     NUMBER(10)    NOT NULL,
  CONSTRAINT fk_tcredito_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO(cedula)
);

/* =========================================================
   12) SERVICIO
   ========================================================= */
CREATE TABLE SERVICIO (
  id_servicio          NUMBER(10) PRIMARY KEY,
  distancia_km         NUMBER(8,2)  NOT NULL,
  costo_total          NUMBER(12,0) NOT NULL,
  hora_inicio          TIMESTAMP    NOT NULL,
  hora_fin             TIMESTAMP,
  id_tipo_servicio     NUMBER(10)   NOT NULL,
  id_usuario_servicio  NUMBER(10)   NOT NULL,
  id_conductor         NUMBER(10),
  id_vehiculo          VARCHAR2(10),
  CONSTRAINT ck_servicio_dist CHECK (distancia_km > 0),
  CONSTRAINT ck_servicio_costo CHECK (costo_total >= 0),
  CONSTRAINT fk_servicio_tipo  FOREIGN KEY (id_tipo_servicio)    REFERENCES TIPO_SERVICIO(id_tipo_servicio),
  CONSTRAINT fk_servicio_user  FOREIGN KEY (id_usuario_servicio) REFERENCES USUARIO(cedula),
  CONSTRAINT fk_servicio_cond  FOREIGN KEY (id_conductor)        REFERENCES USUARIO(cedula),
  CONSTRAINT fk_servicio_veh   FOREIGN KEY (id_vehiculo)         REFERENCES VEHICULO(placa)
);

/* =========================================================
   13) SERVICIO_DESTINO
   ========================================================= */
CREATE TABLE SERVICIO_DESTINO (
  id_servicio NUMBER(10) NOT NULL,
  id_punto    NUMBER(10) NOT NULL,
  orden       NUMBER(5)  NOT NULL,
  CONSTRAINT ck_servdest_orden CHECK (orden >= 1),
  CONSTRAINT pk_servdest PRIMARY KEY (id_servicio, id_punto, orden),
  CONSTRAINT fk_servdest_servicio FOREIGN KEY (id_servicio) REFERENCES SERVICIO(id_servicio),
  CONSTRAINT fk_servdest_punto    FOREIGN KEY (id_punto)    REFERENCES PUNTO_GEOGRAFICO(id_punto)
);

/* =========================================================
   14) TARIFA
   ========================================================= */
CREATE TABLE TARIFA (
  id_tarifa        NUMBER(10) PRIMARY KEY,
  valor_por_km     NUMBER(10,2) NOT NULL,
  id_tipo_servicio NUMBER(10)   NOT NULL,
  id_nivel         NUMBER(10),
  CONSTRAINT ck_tarifa_valor CHECK (valor_por_km > 0),
  CONSTRAINT uq_tarifa UNIQUE (id_tipo_servicio, id_nivel),
  CONSTRAINT fk_tarifa_tipo  FOREIGN KEY (id_tipo_servicio) REFERENCES TIPO_SERVICIO(id_tipo_servicio),
  CONSTRAINT fk_tarifa_nivel FOREIGN KEY (id_nivel)         REFERENCES NIVEL_TRANSPORTE(id_nivel)
);

/* =========================================================
   15) REVISION
   ========================================================= */
CREATE TABLE REVISION (
  id_revision  NUMBER(10) PRIMARY KEY,
  rol_autor    VARCHAR2(10) NOT NULL,
  calificacion NUMBER(1)    NOT NULL,
  comentario   VARCHAR2(400),
  fecha        DATE         NOT NULL,
  id_servicio  NUMBER(10)   NOT NULL,
  id_usuario   NUMBER(10)   NOT NULL,
  CONSTRAINT ck_revision_rol   CHECK (rol_autor IN ('Conductor','Usuario')),
  CONSTRAINT ck_revision_calif CHECK (calificacion BETWEEN 0 AND 5),
  CONSTRAINT fk_revision_servicio FOREIGN KEY (id_servicio) REFERENCES SERVICIO(id_servicio),
  CONSTRAINT fk_revision_usuario  FOREIGN KEY (id_usuario)  REFERENCES USUARIO(cedula)
);

/*rfc1*/
SELECT
  S.ID_SERVICIO,
  S.HORA_INICIO,
  S.HORA_FIN,
  S.DISTANCIA_KM,
  S.COSTO_TOTAL,
  TS.NOMBRE                 AS TIPO_SERVICIO,
  U_CLI.CEDULA              AS ID_CLIENTE,
  U_CLI.NOMBRE              AS NOMBRE_CLIENTE,
  U_CON.CEDULA              AS ID_CONDUCTOR,
  U_CON.NOMBRE              AS NOMBRE_CONDUCTOR,
  V.PLACA                   AS PLACA_VEHICULO,
  TV.NOMBRE                 AS TIPO_VEHICULO,
  NT.NOMBRE                 AS NIVEL_TRANSPORTE,
  C.NOMBRE                  AS CIUDAD_VEHICULO,
  (SELECT LISTAGG(PG.NOMBRE, ' -> ') WITHIN GROUP (ORDER BY SD.ORDEN)
     FROM SERVICIO_DESTINO SD
     JOIN PUNTO_GEOGRAFICO PG ON PG.ID_PUNTO = SD.ID_PUNTO
    WHERE SD.ID_SERVICIO = S.ID_SERVICIO) AS RUTA
FROM   SERVICIO S
JOIN   TIPO_SERVICIO       TS   ON TS.ID_TIPO_SERVICIO = S.ID_TIPO_SERVICIO
JOIN   USUARIO             U_CLI ON U_CLI.CEDULA = S.ID_USUARIO_SERVICIO
LEFT   JOIN USUARIO        U_CON ON U_CON.CEDULA = S.ID_CONDUCTOR
LEFT   JOIN VEHICULO       V    ON V.PLACA = S.ID_VEHICULO
LEFT   JOIN TIPO_VEHICULO  TV   ON TV.ID_TIPO_VEHICULO = V.ID_TIPO_VEHICULO
LEFT   JOIN NIVEL_TRANSPORTE NT ON NT.ID_NIVEL = V.ID_NIVEL
LEFT   JOIN CIUDAD         C    ON C.ID_CIUDAD = V.ID_CIUDAD
WHERE  S.ID_USUARIO_SERVICIO = 200001    
ORDER BY S.HORA_INICIO DESC;

/*rfc2*/
SELECT U.CEDULA        AS ID_CONDUCTOR,
       U.NOMBRE        AS NOMBRE_CONDUCTOR,
       COUNT(*)        AS TOTAL_SERVICIOS
FROM   SERVICIO S
JOIN   USUARIO  U ON U.CEDULA = S.ID_CONDUCTOR
GROUP BY U.CEDULA, U.NOMBRE
ORDER BY TOTAL_SERVICIOS DESC
FETCH FIRST 20 ROWS ONLY;

/*rfc3*/
SELECT U.CEDULA                      AS ID_CONDUCTOR,
       U.NOMBRE                      AS NOMBRE_CONDUCTOR,
       V.PLACA                       AS PLACA_VEHICULO,
       S.ID_SERVICIO,
       S.COSTO_TOTAL,
       ROUND(S.COSTO_TOTAL * (1 - (:P_COMISION_PCT/100)), 2) AS NETO_CONDUCTOR
FROM   SERVICIO S
JOIN   USUARIO  U ON U.CEDULA = S.ID_CONDUCTOR
JOIN   VEHICULO V ON V.PLACA  = S.ID_VEHICULO
ORDER BY U.CEDULA, V.PLACA, S.ID_SERVICIO;

/*rfc4*/
SELECT
  TS.NOMBRE                   AS TIPO_SERVICIO,
  NVL(NT.NOMBRE, 'SIN NIVEL') AS NIVEL,
  COUNT(*)                    AS NUM_SERVICIOS,
  ROUND(100 * RATIO_TO_REPORT(COUNT(*)) OVER (), 2) AS PORCENTAJE
FROM   SERVICIO S
LEFT   JOIN VEHICULO V  ON V.PLACA     = S.ID_VEHICULO
LEFT   JOIN CIUDAD   C  ON C.ID_CIUDAD = V.ID_CIUDAD
JOIN   TIPO_SERVICIO TS ON TS.ID_TIPO_SERVICIO = S.ID_TIPO_SERVICIO
LEFT   JOIN NIVEL_TRANSPORTE NT ON NT.ID_NIVEL = V.ID_NIVEL
WHERE  UPPER(C.NOMBRE) = UPPER('CALI')               -- <=== CAMBIA POR UNA CIUDAD EXISTENTE
  AND  S.HORA_INICIO >= TO_TIMESTAMP('2025-08-31 00:00:00','YYYY-MM-DD HH24:MI:SS')
  AND  S.HORA_INICIO <  TO_TIMESTAMP('2025-09-01 00:00:00','YYYY-MM-DD HH24:MI:SS')
  AND  C.ID_CIUDAD IS NOT NULL
GROUP BY TS.NOMBRE, NVL(NT.NOMBRE, 'SIN NIVEL')
ORDER BY NUM_SERVICIOS DESC, TIPO_SERVICIO, NIVEL;

