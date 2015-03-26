@echo off
cd /d "%~dp0"
setlocal
call variables
cd /d "%project_source_dir%"
dir *.nbm /s
dir adalid-*.jar /s
set filedate="%third_party_dir%\tools\abfTools\FileDate\FileDate.exe"
set filedate="%third_party_dir%\tools\FileTouch\FileTouch.exe"
if not exist %filedate% set filedate=
set filedate
echo.
if not defined filedate pause & goto:eof
set siono=N
set /p siono="modify distribution files time (Y|N) [%siono%] "
echo.
if /i not "%siono%" == "Y" goto:eof
set newhh=%hh24%
set /p newhh="Double-digit Hour (00-23) [%newhh%] "
echo.
if not defined newhh goto:eof
set newnn=%nn%
set /p newnn="Double-digit Minute (00-59) [%newnn%] "
echo.
if not defined newnn goto:eof
set siono=Y
set /p siono="mm/dd/yyyy=%mm%/%dd%/%aaaa%, hh-mm=%newhh%-%newnn%-00 (Y|N) [%siono%] "
echo.
if /i not "%siono%" == "Y" goto:eof
call:forFileTouch *.nbm
call:forFileTouch adalid-*.jar
dir *.nbm /s
dir adalid-*.jar /s
echo.
pause
goto:eof

:forFileTouch
for /R "%project_source_dir%" %%f in (%1) do %filedate% /W /A /C /D %mm%/%dd%/%aaaa% /T %newhh%:%newnn%:00 "%%f"
pause
goto:eof
