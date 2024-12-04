@echo off

NET SESSION >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo Run as Administrator:
    echo Right-click 'install_pj.bat' and left-click "Run as Administrator"
    pause
    exit 
)

SET DIR=%~dp0

setx /M PATH "%PATH%;%DIR%"

echo
echo Type and enter 'pj' at the command-line to invoke the interpreter.
echo Close this terminal and open a new one for changes to be in effect.
pause