@echo off
SET DIR=%~dp0
IF NOT EXIST "%DIR%bin" mkdir "%DIR%bin"
javac -cp "%DIR%lib/*" -d "%DIR%bin" "%DIR%main\*.java" "%DIR%src\*.java"
IF %ERRORLEVEL% EQU 0 (
    java -cp "%DIR%lib/*;%DIR%bin;%DIR%src;%DIR%main" Main
) ELSE (
    echo Compilation Error
)