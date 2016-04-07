@echo off
cd /d "%~dp0"
setlocal
call variables
set USERDIR=%adalid_dir%\source\adalid-util
set USERDIR
set DOCLETPATH=
set DOCLETPATH=%DOCLETPATH%;%USERDIR%\dist\adalid-util.jar
set DOCLETPATH
set CLASSPATH=
set CLASSPATH=%CLASSPATH%;%adalid_dir%\source\adalid-util\dist\adalid-util.jar
set CLASSPATH=%CLASSPATH%;%adalid_dir%\source\adalid-commons\dist\adalid-commons.jar
set CLASSPATH=%CLASSPATH%;%third_party_dir%\lib\commons-collections-3.2.1.jar
set CLASSPATH=%CLASSPATH%;%third_party_dir%\lib\commons-io-2.1.jar
set CLASSPATH=%CLASSPATH%;%third_party_dir%\lib\commons-lang-2.6.jar
set CLASSPATH=%CLASSPATH%;%third_party_dir%\lib\log4j-1.2.16.jar
set CLASSPATH=%CLASSPATH%;%third_party_dir%\lib\velocity-1.7.jar
set CLASSPATH
set SOURCEPATH=
set SOURCEPATH=%SOURCEPATH%;%adalid_dir%\source\adalid-core\src
set SOURCEPATH
set JEXE1="%ProgramFiles%\Java\jdk1.8.0_25\bin\javadoc.exe"
set JEXE1
set JOPT1=%JOPT1% -J-DMetaDoclet.printOptions=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printPackages=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printAnnotations=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printElements=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printEnums=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printConstants=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printClasses=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printFields=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printConstructors=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printMethods=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printComments=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printTags=false
set JOPT1=%JOPT1% -J-DMetaDoclet.printRawTexts=false
set JOPT1=
set JOPT1=%JOPT1% -doclet adalid.util.doclet.MetaDoclet
set JOPT1=%JOPT1% -docletpath %DOCLETPATH%
set JOPT1=%JOPT1% -classpath %CLASSPATH%
set JOPT1=%JOPT1% -sourcepath %SOURCEPATH%
set JOPT1=%JOPT1% -encoding UTF8
set JOPT1=%JOPT1% -printOptions
set JOPT1=%JOPT1% -printPackages
set JOPT1=%JOPT1% -printAnnotations
set JOPT1=%JOPT1% -printElements
set JOPT1=%JOPT1% -printEnums
set JOPT1=%JOPT1% -printConstants
set JOPT1=%JOPT1% -printErrors
set JOPT1=%JOPT1% -printExceptions
set JOPT1=%JOPT1% -printInterfaces
set JOPT1=%JOPT1% -printClasses
set JOPT1=%JOPT1% -printFields
set JOPT1=%JOPT1% -printConstructors
set JOPT1=%JOPT1% -printMethods
rem JOPT1=%JOPT1% -printComments
rem JOPT1=%JOPT1% -printTags
rem JOPT1=%JOPT1% -printRawTexts
set JOPT1=%JOPT1% -public
set JOPT1=%JOPT1% -subpackages adalid
set JOPT1
set JLOG1="%~dpn0.log"
set JLOG1
echo.
cd /d %USERDIR%
if exist %JLOG1% (del %JLOG1%)
rem USERDIR 1>>%JLOG1%
rem DOCLETPATH 1>>%JLOG1%
rem CLASSPATH 1>>%JLOG1%
rem SOURCEPATH 1>>%JLOG1%
rem pause
rem echo.
echo %DATE%-%TIME% 1>>%JLOG1%
echo %JEXE1% %JOPT1%
echo %JEXE1% %JOPT1% 1>>%JLOG1%
call %JEXE1% %JOPT1% 1>>%JLOG1% 2>&1
echo %DATE%-%TIME% 1>>%JLOG1%
echo.
set siono=N
set /p siono="abrir %JLOG1% (S/N) ? [%siono%] "
if /i "%siono%" == "S" (
    start /d %SystemRoot% notepad %JLOG1%
    echo.
    pause
)
echo.
endlocal
cd /d "%~dp0"
echo.
call metajavadoc-doclet
echo.
goto:eof
