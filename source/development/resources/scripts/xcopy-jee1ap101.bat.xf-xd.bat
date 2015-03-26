@echo off
set fx=
if defined fx (
    echo.
)
set xf=/xf
call:set-xf %~f1 delete-generated-files*.*
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-ejb\nbproject\project.properties
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-ejb\src\conf\persistence.xml
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-ejb\src\conf\sun-ejb-jar.xml
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-lib\nbproject\project.properties
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-resources\nbproject\project.properties
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\nbproject\project.properties
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\web\WEB-INF\faces-config.xml
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\web\WEB-INF\glassfish-web.xml
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\web\WEB-INF\web.xml
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\web\ayuda.html
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\web\error.jsp
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\web\index.html
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\web\index.jsp
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\web\login.jsp
set xf=%xf% %~f1\source\jee1ap101\jee1ap101-war\web\redirect.html
set xf=%xf% %~f1\source\jee1ap101\src\conf\application.xml
set xf=%xf% %~f1\source\jee1ap101\src\conf\glassfish-application.xml
call:set-xf %~f1\source\management *.bat
call:set-xf %~f1\source\management *.password
call:set-xf %~f1\source\management *.sh
call:set-xf %~f1\source\management *.txt
set xf=%xf% %~f1\source\management\resources\scripts\windows\postgresql\dump-table-tokens.txt
set xf=%xf% %~f1\source\management\setup\scripts\linux\ln-s.sh
set xf=%xf% %~f1\source\management\setup\scripts\linux\variables-conf.sh
set xf=%xf% %~f1\source\management\setup\scripts\linux\variables-home.sh
set xf=%xf% %~f1\source\management\setup\scripts\windows\variables-conf.bat
set xf=%xf% %~f1\source\management\setup\scripts\windows\variables-home.bat
set xf=%xf% *.gif
set xf=%xf% *.jpg
set xf=%xf% *.lnk
set xf=%xf% *.log
set xf=%xf% *.png
set xf=%xf% Thumbs.db
set xf=%xf% ant-deploy.xml
set xf=%xf% build-impl.xml
set xf=%xf% build.xml
set xf=%xf% eclipse.classpath
rem xf=%xf% eclipse.project
set xf=%xf% genfiles.properties
set xf=%xf% org.eclipse.wst.common.project.facet.core.xml
if /i "%exclude_oracle%" == "Y" set xf=%xf% variables-oracle.*

set xd=/xd
set xd=%xd% %~f1\release
set xd=%xd% %~f1\source\jee1ap101\EarContent
set xd=%xd% %~f1\source\jee1ap101\eclipse.settings
set xd=%xd% %~f1\source\jee1ap101\jee1ap101-ejb\ejbModule
set xd=%xd% %~f1\source\jee1ap101\jee1ap101-ejb\setup
set xd=%xd% %~f1\source\jee1ap101\jee1ap101-ejb\src\java\crop
set xd=%xd% %~f1\source\jee1ap101\jee1ap101-lib\src\java\crop
set xd=%xd% %~f1\source\jee1ap101\jee1ap101-resources\src\crop
rem xd=%xd% %~f1\source\jee1ap101\jee1ap101-resources\src\i18n\en\jee1ap101
set xd=%xd% %~f1\source\jee1ap101\jee1ap101-resources\src\i18n\es\jee1ap101
set xd=%xd% %~f1\source\jee1ap101\jee1ap101-war\src\java\crop
set xd=%xd% %~f1\source\jee1ap101\jee1ap101-war\web\META-INF
set xd=%xd% %~f1\source\jee1ap101\jee1ap101-war\web\crop
set xd=%xd% %~f1\source\jee1ap101\nbproject
set xd=%xd% %~f1\source\management\backup
set xd=%xd% %~f1\source\management\dist
set xd=%xd% %~f1\source\management\logs
set xd=%xd% %~f1\source\management\resources\config
set xd=%xd% %~f1\source\management\resources\database\postgresql\data
set xd=%xd% %~f1\source\management\resources\database\postgresql\data-migration
set xd=%xd% %~f1\source\management\resources\database\postgresql\views\custom-made
set xd=%xd% %~f1\source\management\resources\database\postgresql\views\data-provider
set xd=%xd% %~f1\source\management\resources\database\postgresql\views\jasper-report
set xd=%xd% %~f1\source\management\resources\database\postgresql\views\system
rem xd=%xd% %~f1\source\management\resources\reporting
set xd=%xd% %~f1\source\management\sql
set xd=%xd% build
set xd=%xd% dist
set xd=%xd% private
set xd=%xd% test
if /i "%exclude_oracle%" == "Y" set xd=%xd% oracle

goto:eof

:set-xf
if defined fx (
    echo %*
)
set xf-xd-dir-dir=
set xf-xd-dir-opt=
if /i "%3" == "/s" (
    set xf-xd-dir-opt=/s
) else (
    set xf-xd-dir-dir=%~f1\
)
set xf-xd-dir-log=%~f0.dir.log
if defined fx (
    dir %~f1\%2 %xf-xd-dir-opt%/b>%xf-xd-dir-log%
) else (
    dir %~f1\%2 %xf-xd-dir-opt%/b 1>%xf-xd-dir-log% 2>nul
)
if exist %xf-xd-dir-log% (
    for /f "delims=*" %%t in (%xf-xd-dir-log%) do call:add-xf %xf-xd-dir-dir%%%t
    del %xf-xd-dir-log%
)
if defined fx (
    echo.
)
goto:eof

:add-xf
if defined fx (
    echo %*
)
set xf=%xf% %~f1
goto:eof
