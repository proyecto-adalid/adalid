@echo off
cd /d "%~dp0"
setlocal
call variables
set keytool="%ProgramFiles%\Java\jdk1.8.0_25\bin\keytool.exe"
set alias=adalid
set dname="CN=Jorge Campins, OU=Development, O=Adalid, L=Caracas, ST=Miranda, C=VE"
set keypass=sesamo
set keystore=%project_source_dir%\adalid.jks
set storepass=sesamo
if not exist "%keystore%" call:genkey
call:list
pause
goto:eof

:genkey
set alias
set dname
echo time %TIME%>set-time.bat
time 00:00:00
%keytool% -genkey -noprompt -trustcacerts -alias %alias% -dname %dname% -keypass %keypass% -validity 7305 -keystore %keystore% -storepass %storepass%
call set-time.bat
del set-time.bat
goto:eof

:list
set keystore
%keytool% -list -v -keystore %keystore% -storepass %storepass%
goto:eof
