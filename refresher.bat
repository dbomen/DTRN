@REM @echo off

@REM :: SETUP
@REM call helperBatFiles/vars.bat
@REM call helperBatFiles/buildChecker.bat

@REM :: Run the DTRN class
@REM echo [Refresher] Running %DTRN_CLASS%...
@REM java -cp %BIN_DIR%;%CLASSPATH% %DTRN_CLASS%

@echo off

:: SETUP
call helperBatFiles/vars.bat
call helperBatFiles/buildChecker.bat

:: Run the DTRN class
echo [Refresher] Running Test...
java -cp %BIN_DIR%;%CLASSPATH% main.Test