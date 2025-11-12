#!/usr/bin/env pwsh

# Script para crear 10 conductores con disponibilidades completas
# Ejecuta llamadas HTTP a los endpoints del backend

$baseURL = "http://localhost:8080"

# Array con los datos de los 10 conductores
$conductores = @(
    @{cedula=10001; nombre="Carlos Mendez"; correo="carlos.mendez@mail.com"; celular="3001234001"; placa="CAR0001"; marca="Toyota"; modelo="Corolla"; color="Blanco"},
    @{cedula=10002; nombre="Juan Rodriguez"; correo="juan.rodriguez@mail.com"; celular="3001234002"; placa="CAR0002"; marca="Hyundai"; modelo="Elantra"; color="Gris"},
    @{cedula=10003; nombre="Miguel Torres"; correo="miguel.torres@mail.com"; celular="3001234003"; placa="CAR0003"; marca="Ford"; modelo="Fiesta"; color="Negro"},
    @{cedula=10004; nombre="Pablo Sanchez"; correo="pablo.sanchez@mail.com"; celular="3001234004"; placa="CAR0004"; marca="Chevrolet"; modelo="Aveo"; color="Rojo"},
    @{cedula=10005; nombre="Diego Ramirez"; correo="diego.ramirez@mail.com"; celular="3001234005"; placa="CAR0005"; marca="Mazda"; modelo="Mazda3"; color="Azul"},
    @{cedula=10006; nombre="Fernando Lopez"; correo="fernando.lopez@mail.com"; celular="3001234006"; placa="CAR0006"; marca="Kia"; modelo="Rio"; color="Plata"},
    @{cedula=10007; nombre="Ricardo Gutierrez"; correo="ricardo.gutierrez@mail.com"; celular="3001234007"; placa="CAR0007"; marca="Nissan"; modelo="Sentra"; color="Blanco"},
    @{cedula=10008; nombre="Alfonso Mora"; correo="alfonso.mora@mail.com"; celular="3001234008"; placa="CAR0008"; marca="Volkswagen"; modelo="Polo"; color="Negro"},
    @{cedula=10009; nombre="Roberto Soto"; correo="roberto.soto@mail.com"; celular="3001234009"; placa="CAR0009"; marca="Renault"; modelo="Dacia"; color="Gris"},
    @{cedula=10010; nombre="Angel Vargas"; correo="angel.vargas@mail.com"; celular="3001234010"; placa="CAR0010"; marca="Peugeot"; modelo="308"; color="Rojo"}
)

Write-Host "=== Iniciando creación de 10 conductores ===" -ForegroundColor Green
Write-Host ""

$dispon_id = 1000

foreach ($conductor in $conductores) {
    Write-Host "Creando conductor: $($conductor.nombre)" -ForegroundColor Cyan
    
    # 1. Registrar usuario conductor
    $usuarioUrl = "$baseURL/usuariosConductor/registrar"
    $params = "cedula=$($conductor.cedula)&nombre=$($conductor.nombre)&correo=$($conductor.correo)&celular=$($conductor.celular)"
    
    try {
        $usuarioResult = Invoke-RestMethod -Uri "$usuarioUrl`?$params" -Method Post
        Write-Host "  ✓ Usuario registrado: $usuarioResult" -ForegroundColor Green
    } catch {
        Write-Host "  ✗ Error registrando usuario: $_" -ForegroundColor Red
        continue
    }
    
    # 2. Registrar vehículo
    $vehiculoUrl = "$baseURL/vehiculos/insertar"
    $vehiculoParams = "placa=$($conductor.placa)&marca=$($conductor.marca)&modelo=$($conductor.modelo)&color=$($conductor.color)&capacidad=4&id_usuario=$($conductor.cedula)&id_tipo_vehiculo=1&id_nivel=1&id_ciudad=1"
    
    try {
        $vehiculoResult = Invoke-RestMethod -Uri "$vehiculoUrl`?$vehiculoParams" -Method Post
        Write-Host "  ✓ Vehículo registrado: $vehiculoResult" -ForegroundColor Green
    } catch {
        Write-Host "  ✗ Error registrando vehículo: $_" -ForegroundColor Red
        continue
    }
    
    # 3. Crear 7 disponibilidades (una para cada día de la semana)
    for ($dia = 1; $dia -le 7; $dia++) {
        $dispon_url = "$baseURL/disponibilidades/insertar"
        $dispon_params = "id_disponibilidad=$dispon_id&dia_semana=$dia&hora_inicio=2025-11-03T06:00:00Z&hora_fin=2025-11-03T22:00:00Z&id_usuario=$($conductor.cedula)&id_vehiculo=$($conductor.placa)"
        
        try {
            $disponResult = Invoke-RestMethod -Uri "$dispon_url`?$dispon_params" -Method Post
            if ($disponResult -like "*correctamente*" -or $disponResult -eq "Disponibilidad insertada correctamente") {
                Write-Host "    ✓ Disponibilidad día $dia registrada" -ForegroundColor Green
            } else {
                Write-Host "    ⚠ Respuesta: $disponResult" -ForegroundColor Yellow
            }
        } catch {
            Write-Host "    ✗ Error registrando disponibilidad: $_" -ForegroundColor Red
        }
        
        $dispon_id++
    }
    
    Write-Host ""
}

Write-Host "=== Resumen ===" -ForegroundColor Green
Write-Host "Se intentó crear 10 conductores"
Write-Host "Cada conductor tiene 7 disponibilidades (una por día)"
Write-Host "Total: 10 conductores + 70 disponibilidades"
Write-Host ""
Write-Host "Ahora puedes verificar en la aplicación que los conductores están disponibles"
