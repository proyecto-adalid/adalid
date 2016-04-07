@echo off
cd /d "%~dp0"
setlocal
call variables
set BACKUP="%adalid_dir%\source\documents\sql\postgresql\meta-javadoc.sql"
set BACKUP
set DBNAME="metajavadoc"
set DBNAME
set PGCOPT=--host localhost --port 5432 --username "postgres" --no-password
set PGCOPT
set PGEXE1="%ProgramFiles%\PostgreSQL\9.3\bin\pg_dump.exe"
set PGEXE1
set PGEXE2="%ProgramFiles%\PostgreSQL\9.3\bin\vacuumdb.exe"
set PGEXE2
set PGLOG1="%~dpn0.log"
set PGLOG1
set PGOPT1=--format plain --no-owner --create --clean --encoding UTF8 --no-privileges --no-tablespaces --verbose --no-unlogged-table-data
set PGOPT1
set PGOPT2=--echo --full --quiet --analyze
set PGOPT2
echo.
if exist %PGLOG1% (del %PGLOG1%)
pause
echo.
if exist %BACKUP% (del %BACKUP%)
echo %DATE%-%TIME% 1>>%PGLOG1%
echo %PGEXE2% %PGCOPT% %PGOPT2% --dbname %DBNAME% & echo.
call %PGEXE2% %PGCOPT% %PGOPT2% --dbname %DBNAME%
echo %PGEXE1% %PGCOPT% %PGOPT1% --file %BACKUP% %DBNAME%
echo %PGEXE1% %PGCOPT% %PGOPT1% --file %BACKUP% %DBNAME% 1>>%PGLOG1%
call %PGEXE1% %PGCOPT% %PGOPT1% --file %BACKUP% %DBNAME% 1>>%PGLOG1% 2>&1
echo %DATE%-%TIME% 1>>%PGLOG1%
echo.
set siono=S
set /p siono="abrir %PGLOG1% (S/N) ? [%siono%] "
if /i "%siono%" == "S" (
    start /d %SystemRoot% notepad %PGLOG1%
    echo.
    pause
)
echo.
goto:eof
