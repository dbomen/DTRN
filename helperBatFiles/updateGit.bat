@echo off
git checkout master
if %errorlevel% neq 0 (
    exit /b %errorlevel%
)

git pull --ff-only
if %errorlevel% neq 0 (
    exit /b %errorlevel%
)
