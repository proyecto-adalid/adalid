@echo off
cd /d "%~dp0"

setlocal
call variables
cd /d "%project_dir%"
if not exist release (echo el directorio release no existe & echo. & pause & goto:eof)
cd release
set release=%CD%
set release
echo.
for /D %%d in (V??R*) do call:remove-dir %%d
if not exist workspace (echo el directorio workspace no existe & echo. & pause & goto:eof)
pushd workspace
call:remove-junction adalid
popd
pause
goto:eof

:set-nx1
set nx1=%~nx1
goto:eof

:remove-dir
set siono=S
set /p siono="eliminar el directorio %~f1 (S/N)? [%siono%] "
if /i "%siono%" == "N" echo. & goto:eof
echo rmdir /s /q "%~f1"
rmdir /s /q "%~f1"
set zip="%~dpn1.zip"
if exist %zip% echo del /q %zip% & del /q %zip%
echo.
goto:eof

:remove-junction
if not exist %1 goto:eof
set junction="%USERPROFILE%\workspace\third-party\tools\junction\junction.exe"
set junction
if not exist %junction% (
    echo.
    echo %junction% no existe
    echo.
    goto:eof
)
%junction% %1
set siono=S
set /p siono="eliminar %1 (S/N)? [%siono%] "
if /i "%siono%" == "N" echo. & goto:eof
echo.
%junction% -d %1
echo.
goto:eof
