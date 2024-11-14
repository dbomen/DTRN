@echo off
:: makes sure that the script is run in the same directory as the controller.bat file
cd /d %~dp0

:: SETUP
call helperBatFiles/vars.bat DTRN_SUPER_SECRET
call helperBatFiles/buildChecker.bat DTRN_SUPER_SECRET

:: Run the DTRN class
echo [Controller] Running %CONTROLLER_CLASS%...
@REM java -cp %BIN_DIR%;%CLASSPATH% %CONTROLLER_CLASS%
java -cp %BIN_DIR%;%CLASSPATH% main.Settings.Test