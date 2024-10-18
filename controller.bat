@echo off

:: SETUP
call helperBatFiles/vars.bat
call helperBatFiles/buildChecker.bat

:: Run the DTRN class
echo [Controller] Running %CONTROLLER_CLASS%...
java -cp %BIN_DIR%;%CLASSPATH% %CONTROLLER_CLASS%