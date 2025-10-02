@echo off
:: makes sure that the script is run in the same directory as the controller.bat file
cd /d %~dp0

:: SETUP
call helperBatFiles/vars.bat DTRN_SUPER_SECRET
call helperBatFiles/buildChecker.bat DTRN_SUPER_SECRET

:: check for git updates
git fetch origin master
for /f %%i in ('git rev-list master..origin/master --count') do set COMMITS_AHEAD=%%i
if %COMMITS_AHEAD% GTR 0 (
    echo [Controller] Update available! Run the 'u' command to update the master branch.
)

:: Run the CONTROLLER class
echo [Controller] Running %CONTROLLER_CLASS%...
java -cp %BIN_DIR%;%CLASSPATH% %CONTROLLER_CLASS%
