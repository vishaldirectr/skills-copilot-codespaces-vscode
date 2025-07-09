@echo off
echo Starting Digital Bank Application...
echo.

:: Check if MySQL connector exists
if not exist "mysql-connector-j-8.0.33.jar" (
    echo Error: MySQL connector JAR not found!
    echo Please download mysql-connector-j-8.0.33.jar and place it in this directory.
    echo Download from: https://dev.mysql.com/downloads/connector/j/
    pause
    exit /b 1
)

:: Check if build directory exists
if not exist "build" (
    echo Build directory not found. Running build first...
    call build.bat
    if %errorlevel% neq 0 (
        echo Build failed!
        pause
        exit /b 1
    )
)

:: Run the application
echo Starting application...
java -cp "mysql-connector-j-8.0.33.jar;build" com.wipro.bank.BankApplication

if %errorlevel% neq 0 (
    echo Application terminated with error code %errorlevel%
)

echo.
echo Application finished.
pause
