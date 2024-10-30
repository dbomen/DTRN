@echo off

:: enables us to build a string containing all .java files
setlocal enabledelayedexpansion

:: we check if the value in %1 (first argument) is correct, as we do not want this file
:: to be run manually. It should be called from either the controller.bat or refresher.bat files
:: as they assert the correct drive and path
if "%1" neq "DTRN_SUPER_SECRET" (
    echo "You should not run this script directly. Run controller.bat or refresher.bat files instead!"
    exit /b
)

:: /file checking
:: ===================================================
:: we check for /files
if not exist %FILES_DIR% (
    echo [BuilderChecker] /%FILES_DIR% directory does not exist! Adding directory...
    mkdir %FILES_DIR%
    type nul > %FILES_DIR%/.gitignore
)

:: we check for files in /files
if not exist %FILES_DIR%/blockId.txt (
    echo [BuilderChecker] /%FILES_DIR%/blockId.txt file does not exist! Adding file...
    type nul > %FILES_DIR%/blockId.txt
)
if not exist %FILES_DIR%/notionAPIkey.txt (
    echo [BuilderChecker] /%FILES_DIR%/notionAPIkey.txt file does not exist! Adding file...
    type nul > %FILES_DIR%/notionAPIkey.txt
)
if not exist %FILES_DIR%/.gitignore (
    echo [BuilderChecker] /%FILES_DIR%/.gitignore file does not exist! Adding file...
    type nul > %FILES_DIR%/.gitignore
    echo * > %FILES_DIR%/.gitignore
    echo !.gitignore >> %FILES_DIR%/.gitignore
)
:: ===================================================

:: /libs checking
:: ===================================================
:: we check for /libs
if not exist %LIBS_DIR% (
    echo [BuilderChecker] /%LIBS_DIR% directory does not exist! Adding directory...
    mkdir %LIBS_DIR%
    type nul > %LIBS_DIR%/.gitignore
)

:: we check for dependecy files in /libs
if not exist %LIBS_DIR%/gson.jar (
    echo [BuilderChecker] /%LIBS_DIR%/gson.jar does not exist! Adding dependency...

    :: Check if PowerShell is available
    where powershell > nul
    if errorlevel 0 (
        echo PowerShell is available. Using PowerShell to download...
        powershell -Command "curl -o %LIBS_DIR%\gson.jar https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar"
    ) else (
        echo PowerShell is not available. Attempting to use curl directly in CMD...
        curl -o %LIBS_DIR%\gson.jar https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar
        if errorlevel 1 (
            echo Curl command failed! Please manually download gson.jar from the following link:
            echo https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar
            echo Make sure to put the .jar file in the /libs folder!
            exit /b 1
        )
    )
)

:: we check for other files in /libs
if not exist %LIBS_DIR%/.gitignore (
    echo [BuilderChecker] /%LIBS_DIR%/.gitignore file does not exist! Adding file...
    type nul > %LIBS_DIR%/.gitignore
    echo * > %LIBS_DIR%/.gitignore
    echo !.gitignore >> %LIBS_DIR%/.gitignore
)
:: ===================================================

:: /bin checking
:: ===================================================
:: we check for /bin directory
if not exist %BIN_DIR% (
    echo [BuilderChecker] /%BIN_DIR% directory does not exist! Adding folder...
    mkdir %BIN_DIR%
)

:: we check for files in /bin
if not exist %BIN_DIR%/.gitignore (
    echo [BuilderChecker] /%BIN_DIR%/.gitignore file does not exist! Adding file...
    type nul > %BIN_DIR%/.gitignore
    echo * > %BIN_DIR%/.gitignore
    echo !.gitignore >> %BIN_DIR%/.gitignore
)
:: ===================================================

:: Compiling Java project
:: ===================================================
:: we check if JDK is installed
where javac >nul
if errorlevel 1 (
    echo [BuilderChecker] JDK not found! Please make sure Java is installed and added to the PATH.
    exit /b 1
)

echo [BuilderChecker] Compiling Java project...

:: we recursively get all the .java files within the %SRC_MAIN_DIR%
set JAVA_FILES=
for /r %SRC_MAIN_DIR% %%f in (*.java) do (
    @REM !VAR! is the syntax for delayed expension
    @REM we do this instead of compile each file seperatelly for speed!
    @REM compiling each file individually would be slow
    set JAVA_FILES=!JAVA_FILES! %%f
)
:: we compile all .java files
javac -cp %CLASSPATH% -d %BIN_DIR% %JAVA_FILES%
:: we check if the compilation was successful
if errorlevel 1 (
    echo [BuilderChecker] Compilation failed!
    exit /b 1
)
:: ===================================================