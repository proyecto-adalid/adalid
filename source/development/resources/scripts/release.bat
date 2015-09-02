@echo off
cd /d "%~dp0"

setlocal
if not defined robocopy-log set robocopy-log="%~dpn0.robocopy.log"
if exist %robocopy-log% if %robocopy-log% == "%~dpn0.robocopy.log" del %robocopy-log%
echo %~f0>>%robocopy-log%
set log="%~f0.log"
if exist %log% del %log%
call variables
cd /d "%project_dir%"
if not exist release md release
cd release
set release=%CD%
set release
echo.

:ask
set vernum=10
set /p vernum="version number (nn) [%vernum%] "
if not defined vernum goto ask
echo.

set VRNAME=V%vernum%R%aammdd%
set VRPATH=%release%\%VRNAME%
set VR
echo.

set DQPATH="%VRPATH%"
rem DQPATH
echo.
pause
echo.

echo rd %DQPATH%
if exist %DQPATH% rd %DQPATH% /s/q
echo.

echo md %DQPATH%
md %DQPATH%
echo.

call:x-development
pause
echo.
call:x-documents
echo.
call:x-lib
pause
echo.
call:sweep
pause
echo.
call:x-junction
pause
echo.
goto:eof

:x-development
call:set-sub-dir "%VRPATH%\source"
call:robocopy-folder "%project_source_dir%" %SUBDIR% development
goto:eof

:x-documents
set documents=%project_source_dir%\documents
set setup-txt="%VRPATH%\setup.txt"
if exist %setup-txt% del %setup-txt%
type "%documents%\adalid-jee1-setup.txt">>%setup-txt%
type "%documents%\adalid.project">"%VRPATH%\.project"
call:copy-files "%documents%" %DQPATH% License.txt
goto:eof

:x-lib
call:set-sub-dir "%VRPATH%\lib"
set LIBDIR=%project_dir%\lib
call:copy-files "%LIBDIR%" %SUBDIR% *.*
set jar="%VRPATH%\lib\adalid-oracle.jar"
if /i "%exclude_oracle%" == "Y" if exist %jar% del %jar%
set jar="%VRPATH%\lib\adalid-jee2.jar"
if exist %jar% del %jar%
set jar="%VRPATH%\lib\adalid-prime.jar"
if exist %jar% del %jar%
dir %VRPATH%\lib
goto:eof

:x-junction
set junction="%third_party_dir%\tools\junction\junction.exe"
if not exist %junction% set junction=
set junction
echo.
if not defined junction goto:eof
call:set-sub-dir "%project_dir%\release\workspace"
pushd %SUBDIR%
if exist %project% (
    echo %junction% -d %project%
    call %junction% -d %project%
    echo.
)
echo %junction% %project% %DQPATH%
call %junction% %project% %DQPATH%
echo.
popd
goto:eof

:copy-files
if "%~1" == "" goto:eof
if "%~2" == "" goto:eof
if "%~3" == "" goto:eof
set SOURCE="%~f1"
set TARGET="%~f2"
if not exist %SOURCE% (
    echo %SOURCE% no existe
    echo.
    pause
    echo.
    goto:eof
)
set SOURCE="%~f1\%~3"
echo copy %SOURCE% %TARGET%
call copy %SOURCE% %TARGET%
echo.
goto:eof

:set-sub-dir
set SUBDIR="%~f1"
set SUBDIR
if not exist %SUBDIR% md %SUBDIR%
echo.
goto:eof

:sweep
if not defined VRNAME goto:eof
if not defined VRPATH goto:eof
rem call:replace-VnnRaammdd
rem pause
call:convert-text-files
pause
call:modify-files-date
pause
echo.
call:modify-libraries-timestamp
goto:eof

:replace-VnnRaammdd
set replacer="%third_party_dir%\tools\fart\fart.exe"
if not exist %replacer% set replacer=
set replacer
echo.
if not defined replacer goto:eof
set findstring="VnnRaammdd"
set replacestring="%VRNAME%"
call:replacer bat
call:replacer properties
call:replacer sh
call:replacer sql
call:replacer txt
goto:eof

:replacer
set wildcard="%VRPATH%\*.%1"
echo %replacer% -r %wildcard% %findstring% %replacestring% 2>nul
call %replacer% -r %wildcard% %findstring% %replacestring% 2>nul
echo.
goto:eof

:convert-text-files
set dos2unix="%third_party_dir%\tools\dos2unix\bin\dos2unix.exe"
if not exist %dos2unix% set dos2unix=
echo.
set dos2unix
echo.
if not defined dos2unix goto:eof
call:dos2unix *.jrtx
call:dos2unix *.jrxml
call:dos2unix *.password
call:dos2unix *.properties
call:dos2unix *.psql
call:dos2unix *.sh
call:dos2unix *.sql
call:dos2unix *.txt
call:dos2unix *.vm
call:dos2unix *.xml
echo.
goto:eof

:dos2unix
echo.
echo dos2unix %1
echo.
for /R "%VRPATH%" %%f in (%1) do %dos2unix% -o -q "%%f"
goto:eof

:modify-files-date
set filedate="%third_party_dir%\tools\abfTools\FileDate\FileDate.exe"
set filedate="%third_party_dir%\tools\FileTouch\FileTouch.exe"
if not exist %filedate% set filedate=
set filedate
echo.
if not defined filedate goto:eof
if /i "%filedate%" == "filedate" (
    %filedate% "%VRPATH%\*.txt" %mm%/%dd%/%aaaa% %hh24%-00-00
    %filedate% "%VRPATH%\source\*" %mm%/%dd%/%aaaa% %hh24%-00-00 /r
) else (
    %filedate% /W /A /C /S /D %mm%/%dd%/%aaaa% /T %hh24%:00:00 "%VRPATH%\*.txt"
    set newmm=%mm%
    set newdd=%dd%
    set newhh=%hh24%
    set newnn=00
    call:forFileTouch "%VRPATH%\source"
)
echo.
goto:eof

:modify-libraries-timestamp
set filedate="%third_party_dir%\tools\abfTools\FileDate\FileDate.exe"
set filedate="%third_party_dir%\tools\FileTouch\FileTouch.exe"
if not exist %filedate% set filedate=
set filedate
echo.
if not defined filedate goto:eof
dir %VRPATH%\lib
echo.
set siono=Y
set /p siono="modify NBM and JAR files date and time (Y|N) [%siono%] "
echo.
if /i not "%siono%" == "Y" goto:eof
set newmm=%mm%
set /p newmm="Double-digit Month (01-12) [%newmm%] "
echo.
if not defined newmm goto:eof
set newdd=%dd%
set /p newdd="Double-digit Day (01-31) [%newdd%] "
echo.
if not defined newdd goto:eof
set newhh=%hh24%
set /p newhh="Double-digit Hour (00-23) [%newhh%] "
echo.
if not defined newhh goto:eof
set newnn=%nn%
set newnn=00
set /p newnn="Double-digit Minute (00-59) [%newnn%] "
echo.
if not defined newnn goto:eof
set siono=Y
set /p siono="mm/dd/yyyy=%newmm%/%newdd%/%aaaa%, hh-mm=%newhh%-%newnn%-00 (Y|N) [%siono%] "
echo.
if /i not "%siono%" == "Y" goto:eof
if /i "%filedate%" == "filedate" (
    rem %filedate% "%VRPATH%\lib\*" %newmm%/%newdd%/%aaaa% %newhh%-%newnn%-00 /r
    %filedate% "%VRPATH%\lib\*.nbm" %newmm%/%newdd%/%aaaa% %newhh%-%newnn%-00 /r
    %filedate% "%VRPATH%\lib\*.jar" %newmm%/%newdd%/%aaaa% %newhh%-%newnn%-00 /r
) else (
    rem %filedate% /W /A /C /S /D %newmm%/%newdd%/%aaaa% /T %newhh%:%newnn%:00 "%VRPATH%\lib\*"
    %filedate% /W /A /C /S /D %newmm%/%newdd%/%aaaa% /T %newhh%:%newnn%:00 "%VRPATH%\lib\*.nbm"
    %filedate% /W /A /C /S /D %newmm%/%newdd%/%aaaa% /T %newhh%:%newnn%:00 "%VRPATH%\lib\*.jar"
)
echo.
dir %VRPATH%\lib
echo.
goto:eof

:forFileTouch
for /R %1 %%f in (*) do %filedate% /W /A /C    /D %newmm%/%newdd%/%aaaa% /T %newhh%:%newnn%:00 "%%f" >> %log%
for /R %1 %%f in (.) do %filedate% /W /A /C /S /D %newmm%/%newdd%/%aaaa% /T %newhh%:%newnn%:00 "%%~dpf*" >> %log%
goto:eof

:robocopy-folder
if "%~1" == "" goto:eof
if "%~2" == "" goto:eof
if "%~3" == "" goto:eof
set SOURCE="%~f1\%3"
set TARGET="%~f2\%3"
if not exist %SOURCE% (
    echo %SOURCE% no existe!
    echo.
    pause
    echo.
    goto:eof
)
if exist %TARGET% rmdir %TARGET% /s/q
if not exist %TARGET% md %TARGET%
set xf=/xf
set xf=%xf% Thumbs.db
set xf=%xf% *.lnk
set xf=%xf% *.log
set xd=/xd
set xd=%xd% %SOURCE%\resources\libraries
set xd=%xd% %SOURCE%\resources\log4j
set xd=%xd% %SOURCE%\resources\scripts
robocopy %SOURCE% %TARGET% /s %xf% %xd% /nfl /ndl /log+:%robocopy-log%
echo.
goto:eof
