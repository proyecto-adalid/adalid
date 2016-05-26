@echo off
cd /d "%~dp0"
setlocal
set svnver="%ProgramFiles%\SlikSvn\bin\svnversion.exe"
if not exist %svnver% set svnver=
set svnver
if not defined svnver echo. & pause & goto:eof
set zipper="%ProgramFiles%\7-Zip\7z.exe"
if not exist %zipper% set zipper=
set zipper
if not defined zipper echo. & pause & goto:eof
set ziplog="%~dpn0.log"
set ziplog
if exist %ziplog% del %ziplog%
echo.
call variables
cd /d "%project_source_dir%"
echo.
echo %CD%
echo %CD% %DATE% %TIME% 1>%ziplog%
echo.>>%ziplog%
echo.
%svnver% %adalid_dir%>working.copy.version
set adalid_version=
for /f "tokens=1*" %%t in (working.copy.version) do set adalid_version=%%t
del working.copy.version
if defined adalid_version (
    set adalid_version=1.0.%adalid_version::=-%
) else (
    set adalid_version & echo. & pause & goto:eof
)
set adalid_version
set adalid_version=1.0
set adalid_version
echo.
for /D %%d in (adalid-*) do call:zip Y %%~nd %adalid_version%
set yesOrNo=N
set /p yesOrNo="open log file ? (Y/N) [%yesOrNo%] "
if /i "%yesOrNo%" == "Y" start /d %SystemRoot% notepad %ziplog%
echo.
pause
goto:eof

:zip
set yesOrNo=%1
rem set /p yesOrNo="zip %2? (Y/N) [%yesOrNo%] "
echo zip %2
echo.
if /i not "%yesOrNo%" == "Y" goto:eof
if not exist %2 echo %2 directory is missing & pause & goto:eof
if "%3" == "" (set zipfilename=%2.zip) else (set zipfilename=%2-%3.zip)
if exist %2*.zip del %2*.zip
echo zip a %2\ -xr!build -xr!dist -xr!nbproject -xr!resources -xr!test -xr!*.txt -xr!build.xml* 1>>%ziplog%
%zipper% a %project_dir%\zip\%zipfilename% %2\ -xr!build -xr!dist -xr!nbproject -xr!resources -xr!test -xr!*.txt -xr!build.xml* 1>>%ziplog%
echo.>>%ziplog%
goto:eof
