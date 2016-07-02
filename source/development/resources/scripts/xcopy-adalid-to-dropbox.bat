@echo off
cd /d "%~dp0"
setlocal
call variables
set log="%~f0.robocopy.log"
echo %~f0>%log%
call:adalid-documents
call:source-maven
if defined %~n0-no-pause goto:eof
call:eoj
goto:eof

:adalid-documents
set source=%adalid_dir%\source\documents
set target=D:%HOMEPATH%\Dropbox\adalid\source\documents
set wildcard=
call:set-xd1
call:set-xf1
set xf=%xf% *.laccb
set options=/mir /s /ndl /tee /np /log+:%log%
call:robocopy
dir %target% /a-d /b /s > "%~f0.dir.adalid-documents.log"
goto:eof

:source-maven
set source=%adalid_dir%\source-maven
set target=D:%HOMEPATH%\Dropbox\adalid\source-maven
call:set-xd1
set xd=%xd% src
set xd=%xd% target
call:set-xf1
set options=/mir /s /ndl /tee /np /log+:%log%
call:robocopy
copy %source%\.gitignore %target%
dir %target% /a-d /b /s > "%~f0.dir.source-maven.log"
goto:eof

:robocopy
set source
set target
set wildcard
set options
echo.
set siono=Y
set /p siono="copy source files into target, Are you sure  (Y/N)? [%siono%] "
if /i "%siono%" == "Y" robocopy %source% %target% %wildcard% %xd% %xf% %options%
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

:set-xd1
set xd=/xd
set xd=%xd% .git
set xd=%xd% .svn
goto:eof

:set-xf1
set xf=/xf
set xf=%xf% Thumbs.db
set xf=%xf% *.bat~*
set xf=%xf% *.lnk
set xf=%xf% *.log
set xf=%xf% ~$*.*
goto:eof
