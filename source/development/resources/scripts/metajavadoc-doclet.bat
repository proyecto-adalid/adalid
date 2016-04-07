@echo off
cd /d "%~dp0"
setlocal
call variables
set SCRIPT="%adalid_dir%\source\documents\sql\postgresql\meta-doclet.sql"
set SCRIPT
set DBNAME="metajavadoc"
set DBNAME
set PGCOPT=--host localhost --port 5432 --username "postgres" --no-password
set PGCOPT
set PGEXE1="%ProgramFiles%\PostgreSQL\9.3\bin\psql.exe"
set PGEXE1
set PGEXE2="%ProgramFiles%\PostgreSQL\9.3\bin\vacuumdb.exe"
set PGEXE2
set PGLOG1="%~dpn0.log"
set PGLOG1
set PGOPT1=--echo-queries
set PGOPT1
set PGOPT2=--echo --full --quiet --analyze
set PGOPT2
echo.
if exist %PGLOG1% (del %PGLOG1%)
pause
echo.
echo %DATE%-%TIME% 1>>%PGLOG1%
echo %PGEXE1% %PGCOPT% %PGOPT1% --file %SCRIPT% --dbname %DBNAME%
echo %PGEXE1% %PGCOPT% %PGOPT1% --file %SCRIPT% --dbname %DBNAME% 1>>%PGLOG1%
call %PGEXE1% %PGCOPT% %PGOPT1% --file %SCRIPT% --dbname %DBNAME% 1>>%PGLOG1% 2>&1
echo %DATE%-%TIME% 1>>%PGLOG1%
echo.
echo %PGEXE2% %PGCOPT% %PGOPT2% --dbname %DBNAME% & echo.
call %PGEXE2% %PGCOPT% %PGOPT2% --dbname %DBNAME%
set siono=S
set /p siono="abrir %PGLOG1% (S/N) ? [%siono%] "
if /i "%siono%" == "S" (
    start /d %SystemRoot% notepad %PGLOG1%
    echo.
    pause
)
echo.
goto:eof
