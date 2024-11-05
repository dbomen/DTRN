@echo off
:: makes sure that the script is run in the same directory as the refresher.bat file
cd /d %~dp0

echo [Refresher] STARTING DAILY REFRESHER

:: SETUP
call helperBatFiles/vars.bat DTRN_SUPER_SECRET
call helperBatFiles/buildChecker.bat DTRN_SUPER_SECRET

:: Run the DTRN class
echo [Refresher] Running %DTRN_CLASS%...
java -cp %BIN_DIR%;%CLASSPATH% %DTRN_CLASS%