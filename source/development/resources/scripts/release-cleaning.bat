@echo off
cd /d "%~dp0"

setlocal
call variables
cd /d "%project_dir%"
if not exist release md release
cd release
set release=%CD%
set release
echo.
for /D %%d in (V10R*) do call:remove-dir %%d
pushd workspace
call:remove-junction adalid
popd
pause
goto:eof

:set-nx1
set nx1=%~nx1
goto:eof

:remove-dir
set siono=Y
set /p siono="remove directory %~f1, Are you sure  (Y/N)? [%siono%] "
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
    echo %junction% does not exist!
    echo.
    goto:eof
)
%junction% %1
set siono=Y
set /p siono="delete %1, Are you sure  (Y/N)? [%siono%] "
if /i "%siono%" == "N" echo. & goto:eof
echo.
%junction% -d %1
echo.
goto:eof
