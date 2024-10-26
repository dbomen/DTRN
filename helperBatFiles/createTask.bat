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

:: we get the refresher.bat file path, which is one level up
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: this variable is a string, for e.g.: C:\a\b\
set batchFilePath=%~dp0
:: we get rid of the last '\' -> C:\a\b
set batchFilePath=%batchFilePath:~0,-1%
:: we get the drive letter and path for C:\a\b which is C:\a\, thus getting the one level up path
for %%i in ("%batchFilePath%") do set batchFilePath=%%~dpi
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

if not "%1"=="am_admin" (
    powershell -Command "Start-Process -Verb RunAs -FilePath '%0' -ArgumentList 'am_admin'"
    exit /b
)

schtasks /create /tn "DTRN Refresher" /tr "cmd /c '%batchFilePath%refresher.bat'" /sc daily /st 03:00 /f