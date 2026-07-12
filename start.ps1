Write-Host "============================================" -ForegroundColor Cyan
Write-Host "    Blog Project - Start Frontend & Backend" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "[1/3] Checking MySQL..." -ForegroundColor Yellow
$mysqlStatus = Get-Service -Name MySQL80 -ErrorAction SilentlyContinue
if ($mysqlStatus -eq $null) {
    Write-Host "MySQL service not found!" -ForegroundColor Red
    Write-Host "Please install MySQL 8.0 first" -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit
}

if ($mysqlStatus.Status -ne "Running") {
    Write-Host "MySQL not running, starting..." -ForegroundColor Yellow
    Start-Service -Name MySQL80
    Start-Sleep -Seconds 2
}
Write-Host "MySQL: OK" -ForegroundColor Green

Write-Host ""
Write-Host "[2/3] Starting Backend (Spring Boot)..." -ForegroundColor Yellow
$backendProcess = Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -WorkingDirectory "$PSScriptRoot\backend" -PassThru -WindowStyle Normal
Write-Host "Backend starting... http://localhost:8080" -ForegroundColor Green

Write-Host ""
Write-Host "[3/3] Starting Frontend (Vue)..." -ForegroundColor Yellow
Write-Host "Waiting 3 seconds..." -ForegroundColor Gray
Start-Sleep -Seconds 3
$frontendProcess = Start-Process -FilePath "npm" -ArgumentList "run dev" -WorkingDirectory "$PSScriptRoot\frontend" -PassThru -WindowStyle Normal
Write-Host "Frontend starting... http://localhost:5173" -ForegroundColor Green

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "    All services started!" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "Backend: http://localhost:8080" -ForegroundColor White
Write-Host "Frontend: http://localhost:5173" -ForegroundColor White
Write-Host "Login: admin / 123456" -ForegroundColor White
Write-Host ""
Write-Host "Press Enter to close this window (services will continue running)" -ForegroundColor Gray
Read-Host