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
set xd=%xd% build
set xd=%xd% dist
set xd=%xd% private

:documents
set folder1=%adalid_dir%\source\documents
set folder1
set folder2=D:%HOMEPATH%\Dropbox\adalid\source\documents
set folder2
echo.
set siono=N
set /p siono="copy source files into target, Are you sure  (Y/N)? [%siono%] "
if /i "%siono%" == "Y" robocopy %folder1% %folder2% /np /s /tee %xd% %xf% /log+:%log%
echo.

:adalid-oracle
set folder1=%adalid_dir%\source\adalid-oracle
set folder1
set folder2=D:%HOMEPATH%\Dropbox\adalid\source\adalid-oracle
set folder2
echo.
set siono=N
set /p siono="copy source files into target, Are you sure  (Y/N)? [%siono%] "
if /i "%siono%" == "Y" rmdir %folder2% /s
if /i "%siono%" == "Y" robocopy %folder1% %folder2% /np /e /tee %xd% %xf% /log+:%log%
echo.

:eoj
set siono=N
set /p siono="open log (Y/N)? [%siono%] "
if /i "%siono%" == "Y" (
    start /d %SystemRoot% notepad %log%
    echo.
    pause
)
goto:eof
