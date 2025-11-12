import oracledb
import sys

# Configuración de conexión
dsn_tns = oracledb.makedsn('localhost', 1521, service_name='XEPDB1')
connection = oracledb.connect(user='sistema', password='sistema', dsn=dsn_tns)
cursor = connection.cursor()

# Leer el archivo SQL
with open('Disponibilidades_Simple.sql', 'r') as f:
    sql_content = f.read()

# Dividir por puntos y coma y ejecutar cada statement
statements = [s.strip() for s in sql_content.split(';') if s.strip()]

count = 0
for stmt in statements:
    if stmt:
        try:
            cursor.execute(stmt)
            count += 1
            if count % 10 == 0:
                print(f"Ejecutados {count} statements...")
        except Exception as e:
            print(f"Error en statement: {stmt[:50]}... - {str(e)}")

connection.commit()
print(f"\nTotal de {count} statements ejecutados correctamente")

# Verificar cuántas disponibilidades se insertaron
cursor.execute("SELECT COUNT(*) FROM DISPONIBILIDAD WHERE id_usuario >= 10001 AND id_usuario <= 10010")
total = cursor.fetchone()[0]
print(f"Total de disponibilidades insertadas: {total}")

cursor.close()
connection.close()
