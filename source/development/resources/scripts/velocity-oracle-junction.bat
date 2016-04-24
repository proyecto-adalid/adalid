@echo off
cd /d "%~dp0"
setlocal
call variables
set does_not_exist=
set already_exists=
set junction=%third_party_dir%\tools\junction\junction.exe
call:check-exist junction executable file "%junction%"
if defined does_not_exist goto:eof
set source_dir=%project_source_dir%\adalid-oracle\resources\velocity
call:check-exist source directory "%source_dir%"
if defined does_not_exist goto:eof
set target_dir=%project_source_dir%\development\resources
call:check-exist target directory "%target_dir%"
if defined does_not_exist goto:eof
set target_lnk=velocity-oracle
call:junction
echo.
if defined %~n0-no-pause goto:eof
pause
echo.
goto:eof

:junction
pushd "%target_dir%"
call:check-junction %target_lnk%
if defined junction_token "%junction%" -d %target_lnk%
if /i not "%exclude_oracle%" == "Y" "%junction%" %target_lnk% "%source_dir%"
popd
goto:eof

:check-exist
call:echo-checking %*
call:set-last-parameter %*
if exist %last_parameter% (
    call:echo-does-exist %*
) else (
    call:echo-does-not-exist %*
    goto:eof
)
goto:eof

:check-not-exist
call:echo-checking %*
call:set-last-parameter %*
if exist %last_parameter% (
    call:echo-already-exists %*
    goto:eof
)
goto:eof

:set-last-parameter
set last_parameter=
:loop
if not "%1" == "" (
    set last_parameter=%1
    shift
    goto :loop
)
call:set-last-parameter-in-quotes %last_parameter%
goto:eof

:set-last-parameter-in-quotes
set last_parameter="%~f1"
rem echo check %last_parameter%
goto:eof

:check-junction
"%junction%" %1>junction.log
set junction_token=
for /f "tokens=1*" %%t in (junction.log) do set junction_token=%%t
del junction.log
if defined junction_token (
    if /i not "%junction_token%" == "Substitute" set junction_token=
)
goto:eof

:echo-checking
goto:eof
echo checking %*
goto:eof

:echo-does-exist
goto:eof
echo %* does exist
echo.
goto:eof

:echo-does-not-exist
set does_not_exist=true
echo ***ERROR*** %* does not exist!
echo.
pause
echo.
goto:eof

:echo-already-exists
set already_exists=true
echo ***ERROR*** %* already exists!
echo.
pause
goto:eof
