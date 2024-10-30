@echo off

:: we check if the value in %1 (first argument) is correct, as we do not want this file
:: to be run manually. It should be called from either the controller.bat or refresher.bat files
:: as they assert the correct drive and path
if "%1" neq "DTRN_SUPER_SECRET" (
    echo "You should not run this script directly. Run controller.bat or refresher.bat files instead!"
    exit /b
)

set FILES_DIR=%SCRIPT_DIR%files
set LIBS_DIR=%SCRIPT_DIR%libs

set SRC_MAIN_DIR=%SCRIPT_DIR%src\main
set BIN_DIR=%SCRIPT_DIR%src\bin

set CLASSPATH=%LIBS_DIR%\gson.jar

set DTRN_CLASS=main.DTRN
set CONTROLLER_CLASS=main.controller.Controller