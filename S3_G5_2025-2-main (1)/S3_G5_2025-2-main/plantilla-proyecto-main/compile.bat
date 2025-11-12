@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0"
echo Directorio actual: %cd%
echo.
echo Buscando mvnw.cmd...
if exist mvnw.cmd (
    echo Encontrado mvnw.cmd
    echo.
    echo Iniciando compilacion...
    call mvnw.cmd clean compile -DskipTests
    echo.
    if errorlevel 1 (
        echo ERROR: La compilacion fallo con codigo: %errorlevel%
    ) else (
        echo EXITO: Compilacion completada
    )
) else (
    echo ERROR: mvnw.cmd no encontrado en %cd%
    echo.
    echo Archivos en este directorio:
    dir /B
)
echo.
echo Presiona cualquier tecla para cerrar...
pause
