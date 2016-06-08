@echo off
cd /d "%~dp0"
setlocal
call variables
set log="%~f0.log"
echo %~f0>%log%
set xf=/xf
set xf=%xf% Thumbs.db
set xf=%xf% build-impl.xml
set xf=%xf% genfiles.properties
set xf=%xf% *.laccb
set xf=%xf% *.lnk
set xf=%xf% *.log
set xf=%xf% ~$*.*
set xd=/xd
set xd=%xd% .git
set xd=%xd% build
set xd=%xd% dist
set xd=%xd% private
call:documents
call:adalid-prime
if defined %~n0-no-pause goto:eof
call:eoj
goto:eof

:documents
set source=%adalid_dir%\source\documents
set target=D:%HOMEPATH%\Dropbox\adalid\source\documents
set options=/np /log+:%log% /tee /mir /ndl
call:robocopy
goto:eof

:adalid-oracle
set source=%adalid_dir%\source\adalid-oracle
set target=D:%HOMEPATH%\Dropbox\adalid\source\adalid-oracle
set options=/np /log+:%log% /tee /mir /ndl
call:robocopy
goto:eof

:adalid-prime
set source=%adalid_dir%\source\adalid-prime
set target=D:%HOMEPATH%\Dropbox\adalid\source\adalid-prime
set options=/np /log+:%log% /tee /mir /ndl
call:robocopy
goto:eof

:robocopy
set source
set target
set options
echo.
set siono=Y
set /p siono="copy source files into target, Are you sure  (Y/N)? [%siono%] "
if /i "%siono%" == "Y" robocopy %source% %target% /e %xd% %xf% %options%
echo.
goto:eof

:eoj
set siono=N
set /p siono="open log (Y/N)? [%siono%] "
if /i "%siono%" == "Y" (
    start /d %SystemRoot% notepad %log%
    echo.
    pause
    echo.
)
goto:eof
