@echo off
cd /d "%~dp0"
setlocal
call variables
cd /d %project_dir%
set dp0=%CD%
set x010=%~f0.010.log
set x020=%~f0.020.log
set x030=%~f0.030.log
if exist %x010% del %x010%
if exist %x020% del %x020%
if exist %x030% del %x030%

set junction=%USERPROFILE%\workspace\third-party\tools\junction\junction.exe
call:check-exist junction executable file "%junction%"
if defined does_not_exist goto:eof

set replacer=%USERPROFILE%\workspace\third-party\tools\fart\fart.exe
call:check-exist replacer executable file "%replacer%"
if defined does_not_exist goto:eof

set m2r=%USERPROFILE%\.m2\repository\org\adalid
set sma=source-maven\adalid
if not exist %sma% md %sma%
if not exist %sma% goto:eof
if not exist source goto:eof
for /D %%d in (source\*) do call:check-directory %%d
echo.
pause
goto:eof

:check-directory
    if not exist %~f1\build.xml (
        echo skipping directory %~f1
        echo.
        goto:eof
    )
    if not exist %~f1\src (
        echo skipping directory %~f1
        echo.
        goto:eof
    )
    if not exist %~f1\srx (
        echo skipping directory %~f1
        echo.
        goto:eof
    )
    call:build-project %~f1
    echo.
    goto:eof

:build-project
    pause
    cls
    set sma-nx1=%sma%\%~nx1
    echo building directory %sma-nx1%
    if not exist %sma-nx1% (
        echo creating directory %sma-nx1%
        md %sma-nx1%
    )
    set pom=%m2r%\%~nx1\1.0\%~nx1-1.0.pom
    set xml=%sma-nx1%\pom.xml
    if exist %pom% if not exist %xml% (
        echo copying %pom% to %sma%\pom.xml
        copy %pom% %xml%
        %replacer% %xml%* "org.adalid" "org.proyecto-adalid"
    )
    dir %~f1\src\*.* /a-d /b /s 1>%x010% 2>nul
    for /f "tokens=1*" %%f in (%x010%) do if /i not "%%~xf" == ".java" echo %%f >> %x020%
    call:create-junction-main %~f1
goto:eof

:create-junction-main
    set target=%sma-nx1%\src\main
    if exist "%target%" rd /s /q "%target%"
    if not exist "%target%" md "%target%"
    set source=%~f1\src
    call:create-junction java
    echo.
    set source=%~f1\srx
    set source
    call:create-junction resources
    echo.
    goto:eof

:create-junction
    call:check-exist source directory "%source%"
    if defined does_not_exist goto:eof
    call:check-exist target directory "%target%"
    if defined does_not_exist goto:eof

:create-junction-010
    pushd "%target%"
    call:check-junction %1
    if defined junction_token (
        "%junction%" -d %1
        goto:create-junction-020
    )
    call:check-not-exist target directory "%target%\%1"
    if defined already_exists goto:create-junction-999

:create-junction-020
    "%junction%" %1 "%source%"

:create-junction-999
    popd
    goto:eof

:check-junction
    set junction_token=
    "%junction%" %1>junction.log
    for /f "tokens=1,2* delims=: " %%i in (junction.log) do (
        if /i "[%%i %%j]" == "[Substitute Name]" (
            rem echo %%i %%j %%k
            set junction_token=%1
        )
    )
    del junction.log
    if defined junction_token goto:eof
    dir %1? /a:d /o:n>junction.log 2>nul
    for /f "tokens=1,2,3* delims=<>" %%i in (junction.log) do (
        if /i "[%%j]" == "[JUNCTION]" (
            rem echo %%i%%j%%k
            set junction_token=%1
        )
    )
    del junction.log
    goto:eof

:check-exist
    call:set-last-parameter %*
    if exist %last_parameter% (
        call:echo-does-exist %*
    ) else (
        call:echo-does-not-exist %*
    )
    goto:eof

:check-not-exist
    call:set-last-parameter %*
    if exist %last_parameter% (
        call:echo-already-exists %*
    ) else (
        call:echo-does-not-already-exist %*
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
    goto:eof

:echo-does-exist
    set does_not_exist=
    goto:eof

:echo-does-not-exist
    set does_not_exist=true
    echo.
    echo ***ERROR*** %* does not exist!
    echo.
    pause
    echo.
    goto:eof

:echo-already-exists
    set already_exists=true
    echo.
    echo ***ERROR*** %* already exists!
    echo.
    pause
    echo.
    goto:eof

:echo-does-not-already-exist
    set already_exists=
    goto:eof
