pushd "%~dp0..\..\..\.."
call:set-project-variables "%CD%"
call:set-variables-date-time
popd
goto:eof

:set-project-variables
set project=%~n1
set project_dir=%~f1
set project_source_dir=%project_dir%\source
set workspace=%~dp1
set workspace=%workspace:~0,-1%
set adalid_dir=%workspace%\adalid
set third_party_dir=%workspace%\third-party
goto:eof

:set-variables-date-time
REM la configuración regional de windows debe ajustarse de la siguiente manera:
REM el formato de la fecha debe ser dia, mes y año
REM el separador de la fecha debe ser barra, guión o punto
REM el formato de la hora debe ser hora, minutos y, opcionalmente, periodo
REM el separador de la hora debe ser dos puntos, guión o punto
REM el periodo de la hora debe ser A, AM, A.M., M, P, PM o P.M.
call:set-variables-date
call:set-variables-time
REM call:show-variables
goto:eof

:set-variables-date
set d1=
set d2=
set d3=
set xx=/-.
for /f "tokens=1-9* delims=%xx% " %%a in ('date/t') do (
    set d1=0000%%a
    set d2=0000%%b
    set d3=0000%%c
)
set dd=%d1:~-2%
set mm=%d2:~-2%
set aa=%d3:~-2%
set aaaa=%d3:~-4%
set d1=
set d2=
set d3=
set xx=
set ddmmaa=%dd%%mm%%aa%
set mmddaa=%mm%%dd%%aa%
set aammdd=%aa%%mm%%dd%
set ddmmaaaa=%dd%%mm%%aaaa%
set mmddaaaa=%mm%%dd%%aaaa%
set aaaammdd=%aaaa%%mm%%dd%
goto:eof

:set-variables-time
set t1=
set t2=
set t3=
set xx=:-.,
for /f "tokens=1-9* delims=%xx% " %%a in ('time/t') do (
    set t1=00%%a
    set t2=00%%b
    set t3=%%c
)
set hh=%t1:~-2%
set nn=%t2:~-2%
call:set-tt %t3%
if defined tt (call:set-hh24 %hh%) else (set hh24=%hh%)
set t1=
set t2=
set t3=
set xx=
set hhmm=%hh%%nn%
set hh24mm=%hh24%%nn%
set hhmmtt=%hh%%nn%%tt%
set tthhmm=%tt%%hh%%nn%
goto:eof

:show-variables
date/t
set dd
set mm
set aa
time/t
set hh
set nn
set tt
pause
goto:eof

:set-tt
set tt=
set xx=%1x
set xx=%xx:~0,1%
if /i "%xx%" == "A" set tt=AM
if /i "%xx%" == "P" set tt=PM
set xx=
goto:eof

:set-hh24
if "%1" == "" (set xx=0) else (set xx=%1)
if not "%xx%" == "0" if "%xx:~0,1%" == "0" set xx=%xx:~1%
if "%xx%" == "12" (set /a hh24=0) else (set /a hh24=%xx%)
if %hh24% LSS 12 if /i "%tt%" == "PM" set /a hh24=hh24+12
set hh24=00%hh24%
set hh24=%hh24:~-2%
goto:eof

:lpad
set xx=%1
call set xx=00%%%xx%%%
set %1=%xx:~-2%
set xx=
goto:eof
