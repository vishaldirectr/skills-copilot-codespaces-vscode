@echo off
echo Starting Digital Bank Web Application...
echo.
echo The app will be available at: http://localhost:3000
echo Press Ctrl+C to stop the server
echo.
cd web-app
python -m http.server 3000
pause
