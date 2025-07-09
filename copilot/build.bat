@echo off
echo Building Digital Bank Application...
echo.

:: Set paths
set SRC_DIR=src\main\java
set OUT_DIR=build
set MAIN_CLASS=com.wipro.bank.BankApplication
set JAR_NAME=BankApplication.jar
set MYSQL_JAR=mysql-connector-j-8.0.33.jar

:: Create build directory
if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

:: Clean previous build
if exist "%OUT_DIR%\*.class" del /q "%OUT_DIR%\*.class"
if exist "%OUT_DIR%\com" rmdir /s /q "%OUT_DIR%\com"

echo Compiling Java source files...
javac -cp "%MYSQL_JAR%" -d "%OUT_DIR%" -sourcepath "%SRC_DIR%" "%SRC_DIR%\com\wipro\bank\*.java" "%SRC_DIR%\com\wipro\bank\model\*.java" "%SRC_DIR%\com\wipro\bank\dao\*.java" "%SRC_DIR%\com\wipro\bank\service\*.java" "%SRC_DIR%\com\wipro\bank\util\*.java"

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!

:: Create manifest file
echo Main-Class: %MAIN_CLASS% > "%OUT_DIR%\MANIFEST.MF"
echo Class-Path: %MYSQL_JAR% >> "%OUT_DIR%\MANIFEST.MF"
echo. >> "%OUT_DIR%\MANIFEST.MF"

echo Creating JAR file...
cd "%OUT_DIR%"
jar cfm "%JAR_NAME%" MANIFEST.MF com\

if %errorlevel% neq 0 (
    echo JAR creation failed!
    cd ..
    pause
    exit /b 1
)

cd ..
echo JAR file created successfully: %OUT_DIR%\%JAR_NAME%

echo.
echo To run the application:
echo 1. Ensure MySQL is running
echo 2. Place %MYSQL_JAR% in the same directory as the JAR
echo 3. Run: java -jar %OUT_DIR%\%JAR_NAME%
echo.
echo Build completed successfully!
pause
