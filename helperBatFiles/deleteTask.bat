@echo off

:: we check if the value in %1 (first argument) is correct, as we do not want this file
:: to be run manually. It should be called from either the controller.bat or refresher.bat files
:: as they assert the correct drive and path
if "%1" neq "DTRN_SUPER_SECRET" (
    if "%1" neq "am_admin" (
        echo "You should not run this script directly. Run controller.bat or refresher.bat files instead!"
        exit /b
    )
)

if not "%1"=="am_admin" (
    powershell -Command "Start-Process -Verb RunAs -FilePath '%0' -ArgumentList 'am_admin'"
    exit /b
)

schtasks /delete /tn "DTRN Refresher" /f

pause