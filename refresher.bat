@echo off

:: SETUP
call helperBatFiles/vars.bat
call helperBatFiles/buildChecker.bat

:: Run the DTRN class
echo [Refresher] Running %DTRN_CLASS%...
java -cp %BIN_DIR%;%CLASSPATH% %DTRN_CLASS%