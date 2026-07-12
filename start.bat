@echo off
cd /d "%~dp0"

echo ============================================
echo    Blog Project - Start Frontend & Backend
echo ============================================
echo.

echo [1/3] Checking MySQL...
sc query MySQL80 | findstr "RUNNING" >nul
if %errorlevel% neq 0 (
    echo MySQL not running, starting...
    net start MySQL80
)
echo MySQL: OK

echo.
echo [2/3] Starting Backend (Spring Boot)...
start "Backend" cmd /k "cd backend && mvn spring-boot:run"
echo Backend starting... http://localhost:8080

echo.
echo [3/3] Starting Frontend (Vue)...
timeout /t 3 /nobreak >nul
start "Frontend" cmd /k "cd frontend && npm run dev"
echo Frontend starting... http://localhost:5173

echo.
echo ============================================
echo    All services started!
echo ============================================
echo Backend: http://localhost:8080
echo Frontend: http://localhost:5173
echo Login: admin / 123456
echo.
pause