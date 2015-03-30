@echo off
cd /d "%~dp0"
setlocal
set dp0=%CD%
call variables
set log=%~f0.log
if exist "%log%" del "%log%"
set quiet_option=/q
set source_project_base=jee1\base
set source_project_code=jee1ap101
set source_project_vtlv=${project.alias}
set source_project_code_upper_case=JEE1AP101
set source_project_vtlv_upper_case=${project.alias.toUpperCase()}
set replacer=%third_party_dir%\tools\fart\fart.exe
set velocity=%project_source_dir%\development\resources\velocity
call:check-exist replacer executable file "%replacer%"
if defined does_not_exist goto:eof
call:check-exist velocity directory "%velocity%"
if defined does_not_exist goto:eof

:set-rmdir-options-loop
set siono=Y
set /p siono="Do not ask if ok to remove directory trees, Are you sure (Y/N)? [%siono%] "
if /i "%siono%" == "Y" set rmdir_options=/s /q
if /i "%siono%" == "Y" echo. & goto:confirm-rmdir-quiet-option-loop
if /i "%siono%" == "N" set rmdir_options=/s
if /i "%siono%" == "N" echo. & goto:all-set
goto:set-rmdir-options-loop

:confirm-rmdir-quiet-option-loop
set siono=Y
set /p siono="Are you nuts (Y/N)? [%siono%] "
if /i "%siono%" == "Y" echo. & goto:all-set
if /i "%siono%" == "N" echo. & goto:set-rmdir-options-loop
goto:confirm-rmdir-quiet-option-loop

:all-set
call:clean-dir
call:copy-dir
call:transform
call:copy-bin
call:build-dir

set /a files=0
set templates=%velocity%\templates\%source_project_code%
set templates=%velocity%\templates\%source_project_base%
for /R %templates% %%f in (*) do set /a files=files+1
echo %files% Templates

set /a files=0
set platforms=%velocity%\platforms\%source_project_code%
set platforms=%velocity%\platforms\%source_project_base%
for /R %platforms% %%f in (*) do set /a files=files+1
echo %files% Properties Files

echo.
pause
echo.
call:copy-from-jee1ap101-to-jee1
echo.
pause
echo.
goto:eof

:clean-dir
set templates=%velocity%\templates\%source_project_code%
set templates=%velocity%\templates\%source_project_base%
set platforms=%velocity%\platforms\%source_project_code%
set platforms=%velocity%\platforms\%source_project_base%
set templates
set platforms
echo.
if  exist "%templates%" (
    if /i "%rmdir_options%" == "/s" echo ***DELETE DIRECTORY TREE***
    rmdir "%templates%" %rmdir_options%
    if /i "%rmdir_options%" == "/s" echo.
)
if  exist "%platforms%" (
    if /i "%rmdir_options%" == "/s" echo ***DELETE DIRECTORY TREE***
    rmdir "%platforms%" %rmdir_options%
    if /i "%rmdir_options%" == "/s" echo.
)
if /i "%rmdir_options%" == "/s" pause & echo.
goto:eof

:copy-bin
set exclude=%~f0.xf-xd-bin.bat
call:copy-files "*.gif *.jpg *.png"
goto:eof

:copy-dir
set exclude=%~f0.xf-xd.bat
call:copy-files *.*
goto:eof

:copy-files
set templates=%velocity%\templates\%source_project_code%
set templates=%velocity%\templates\%source_project_base%
set platforms=%velocity%\platforms\%source_project_code%
set platforms=%velocity%\platforms\%source_project_base%
set source=%workspace%\%source_project_code%
set target=%templates%
set wildcard=%~1
set options=/nfl /ndl /s
set xf=
set xd=
call:check-exist source directory "%source%"
if defined does_not_exist goto:eof
call:check-exist set xf/xd file "%exclude%"
if defined does_not_exist goto:eof

call:copy-block
goto:eof

:copy-block
set siono=
echo source=%source%
echo target=%target%
echo.
if defined quiet_option goto:copy-block-exec

:copy-block-loop
set siono=
set /p siono="copy source\%wildcard% into target, Are you sure (Y/N)? "
if /i "%siono%" == "N" goto:eof
if /i not "%siono%" == "Y" goto:copy-block-loop
echo.

:copy-block-exec
set siono=Y
if not exist "%target%" mkdir "%target%"
echo copy %wildcard%
call "%exclude%" "%source%"
robocopy "%source%" "%target%" %wildcard% %options% %xf% %xd%
echo.
goto:eof

:transform
set templates=%velocity%\templates\%source_project_code%
set templates=%velocity%\templates\%source_project_base%
set platforms=%velocity%\platforms\%source_project_code%
set platforms=%velocity%\platforms\%source_project_base%
set wildcard=%templates%\*.*

set findstring=$
set replacestring=${dollar}
call:replacer-block

set findstring=#
set replacestring=${pound}
call:replacer-block

set findstring=\\
set replacestring=${backslash}
call:replacer-block

set findstring=%source_project_code%
set replacestring=%source_project_vtlv%
call:replacer-block

set findstring=%source_project_code_upper_case%
set replacestring=%source_project_vtlv_upper_case%
call:replacer-block

echo.
goto:eof

:replacer-block
set siono=
echo replace %findstring% with %replacestring%
goto:replacer-block-exec

set findstring
set replacestring
set wildcard
echo.
if defined quiet_option goto:replacer-block-exec

:replacer-block-loop
set siono=
set /p siono="Replace findstring with replacestring in all files matching wildcard, Are you sure (Y/N)? "
if /i "%siono%" == "N" goto:eof
if /i not "%siono%" == "Y" goto:replacer-block-loop
echo.

:replacer-block-exec
set siono=Y
echo replace "%findstring%" with "%replacestring%" 1>>%log% 2>nul
call %replacer% -r "%wildcard%" "%findstring%" "%replacestring%" 1>>%log% 2>nul
goto:eof

:build-dir
set templates=%velocity%\templates\%source_project_code%
set templates=%velocity%\templates\%source_project_base%
set platforms=%velocity%\platforms\%source_project_code%
set platforms=%velocity%\platforms\%source_project_base%
call:builder-block
goto:eof

:builder-block
set siono=
set templates
set platforms
echo.
if defined quiet_option goto:builder-block-exec

:builder-block-loop
set siono=
set /p siono="Build properties for each template, Are you sure (Y/N)? "
if /i "%siono%" == "N" goto:eof
if /i not "%siono%" == "Y" goto:builder-block-loop
echo.

:builder-block-exec
set siono=Y
if not exist "%platforms%" mkdir "%platforms%"
set /a batch=0
set /a files=0
for /R %templates% %%f in (*) do call:build-properties-file "%%f"
echo %files% Properties Files built
echo.
goto:eof

:build-properties-file
set /a batch=batch+1
set /a files=files+1

if %batch% EQU 100 (
    echo %files% Properties Files built so far
    set /a batch=0
)

set template_file=%~f1
set template_path=%~dp1
set template_name=%~nx1

call set value1=%%template_file:%velocity%\templates=templates%%
call set value1=%%template_file:%velocity%\templates\%source_project_base%=templates\%source_project_base%%%
set value1=%value1:\=/%

call set value2=%%template_path:%velocity%\templates=%%
call set value2=%%template_path:%velocity%\templates\%source_project_base%=/%source_project_vtlv%%%
call set value2=%%value2:%source_project_code%=%source_project_vtlv%%%
set value2=%value2:eclipse.settings=.settings%
set value2=%value2:\=/%
set value2=%value2:~0,-1%
set value2=${rootFolderSlashedPath}%value2%

call set value3=%%template_name:%source_project_code%=%source_project_vtlv%%%
set value3=%value3:eclipse.project=.project%
set value3=%value3:eclipse.classpath=.classpath%

call set properties_file=%%template_file:%velocity%\templates=%velocity%\platforms%%
set properties_file=%properties_file%.properties
if exist "%properties_file%" del "%properties_file%"

call set properties_path=%%template_path:%velocity%\templates=%velocity%\platforms%%
if not exist "%properties_path%" mkdir "%properties_path%"

call:binary-check %1
if defined binary (
    call:build-document-file-properties
) else (
    call:set-disabled %1
    call:set-preserve %1
    call:build-template-file-properties
)
goto:eof

:binary-check
set binary=
if "%~x1" == ".gif" set binary=true
if "%~x1" == ".jpg" set binary=true
if "%~x1" == ".png" set binary=true
goto:eof

:i18n-check
set i18n=
if not "%~x1" == ".properties" goto:eof
set f18n="%~f1"
set f18n=%f18n:\src\i18n\=\%
if "%~f1" == %f18n% goto:eof
set f18n=%f18n:\lib\base\bundle\=\%
if "%~f1" == %f18n% goto:eof
set i18n=true
goto:eof

:set-disabled
call:set-dp1-n1 "%~f1%"
set disabled=
if "%~n1" == "eclipse"   set disabled=true
if "%n1%" == "eclipse"   set disabled=true
if "%n1%" == "nbproject" set disabled=true
goto:eof

:set-preserve
set preserve=
if "%~x1" == ".css"  set preserve=true
if "%~x1" == ".jrtx" set preserve=true
goto:eof

:build-document-file-properties
echo template = %value1%>>"%properties_file%"
echo template-type = document>>"%properties_file%"
echo path = %value2%>>"%properties_file%"
echo file = %value3%>>"%properties_file%"
echo preserve = true>>"%properties_file%"
goto:eof

:build-template-file-properties
echo template = %value1%>>"%properties_file%"
rem  template-type = velocity>>"%properties_file%"
echo path = %value2%>>"%properties_file%"
echo file = %value3%>>"%properties_file%"
if defined disabled echo disabled = true>>"%properties_file%"
if defined preserve echo preserve = true>>"%properties_file%"
echo dollar.string = $>>"%properties_file%"
echo pound.string = #>>"%properties_file%"
echo backslash.string = \\>>"%properties_file%"
goto:eof

:check-exist
set does_not_exist=
call:echo-checking %*
call:set-last-parameter %*
if exist %last_parameter% (
    call:echo-does-exist %*
) else (
    call:echo-does-not-exist %*
    goto:eof
)
goto:eof

:check-not-exist
set already_exists=
call:echo-checking %*
call:set-last-parameter %*
if exist %last_parameter% (
    call:echo-already-exists %*
    goto:eof
)
goto:eof

:set-last-parameter
set last_parameter=
:set-last-parameter-loop
if not "%1" == "" (
    set last_parameter=%1
    shift
    goto :set-last-parameter-loop
)
goto:eof

:set-extra-options
shift
shift
shift
set extra_options=
:set-extra-options-loop
if not "%1" == "" (
    set extra_options=%extra_options% %1
    shift
    goto :set-extra-options-loop
)
if defined extra_options set extra_options=%extra_options:~1%
goto:eof

:echo-checking
goto:eof
echo checking %*
goto:eof

:echo-does-exist
goto:eof
echo %* does exist
echo.
goto:eof

:echo-does-not-exist
set does_not_exist=true
echo ***ERROR*** %* does not exist!
echo.
pause
echo.
goto:eof

:echo-already-exists
set already_exists=true
echo ***ERROR*** %* already exists!
echo.
pause
goto:eof

:set-dp1-n1
set dp1=%~dp1
call:set-n1 "%dp1:~0,-1%"
goto:eof

:set-n1
set n1=%~n1
goto:eof

:copy-from-jee1ap101-to-jee1
call:set-dir-var ..\velocity\platforms\jee1
if defined jee1 (
    set jee1
    echo.
) else (
    echo ERROR: ..\velocity\platforms\jee1
    pause
    goto:eof
)
call:set-dir-var ..\velocity\platforms\jee1\base
if defined base (
    set base
    echo.
) else (
    echo ERROR: ..\velocity\platforms\jee1\base
    pause
    goto:eof
)
echo:make-dir-tree
echo.
call:copy-properties
echo.
set findstring=disabled = true
set findstring
set replacestring=preserve = true
set replacestring
set wildcard=*.properties
set wildcard
echo.
pushd %jee1%\eclipse
call:wildcard-replacer
popd
echo.
pushd %jee1%\netbeans
call:wildcard-replacer
popd
goto:eof

:wildcard-replacer
echo replace "%findstring%" with "%replacestring%" in "%wildcard%"
echo %replacer% -r "%wildcard%" "%findstring%" "%replacestring%"
call %replacer% -r "%wildcard%" "%findstring%" "%replacestring%"
goto:eof

:set-dir-var
pushd "%~f1"
set dirvar=%~n1
call set %dirvar%=
if /i not "%CD%" == "%dp0%" (
    call set %dirvar%=%CD%
    popd
)
goto:eof

:make-dir-tree
pushd %jee1%
rd eclipse /s /q
md eclipse
md eclipse\project
md eclipse\project\ejb
md eclipse\project\lib
md eclipse\project\resources
md eclipse\project\war
md eclipse\settings
md eclipse\settings\ejb
md eclipse\settings\lib
md eclipse\settings\resources
md eclipse\settings\war
tree eclipse
echo.
rd netbeans /s /q
md netbeans
md netbeans\nbproject
md netbeans\nbproject\ejb
md netbeans\nbproject\lib
md netbeans\nbproject\resources
md netbeans\nbproject\war
tree netbeans
echo.
popd
goto:eof

:copy-properties
call:copy-properties-1
call:copy-properties-2 ejb
call:copy-properties-2 lib
call:copy-properties-2 resources
call:copy-properties-2 war
goto:eof

:copy-properties-1
call:echo-copy-into %base%\source\jee1ap101\eclipse*.* %jee1%\eclipse\project
call copy %base%\source\jee1ap101\eclipse*.* %jee1%\eclipse\project
echo.
call:echo-copy-into %base%\source\jee1ap101\eclipse.settings\*.* %jee1%\eclipse\settings
call copy %base%\source\jee1ap101\eclipse.settings\*.* %jee1%\eclipse\settings
echo.
call:echo-copy-into %base%\source\jee1ap101\nbproject\*.* %jee1%\netbeans\nbproject
call copy %base%\source\jee1ap101\nbproject\*.* %jee1%\netbeans\nbproject
echo.
goto:eof

:copy-properties-2
call:echo-copy-into %base%\source\jee1ap101\jee1ap101-%1\eclipse*.* %jee1%\eclipse\project\%1
call copy %base%\source\jee1ap101\jee1ap101-%1\eclipse*.* %jee1%\eclipse\project\%1
echo.
call:echo-copy-into %base%\source\jee1ap101\jee1ap101-%1\eclipse.settings\*.* %jee1%\eclipse\settings\%1
call copy %base%\source\jee1ap101\jee1ap101-%1\eclipse.settings\*.* %jee1%\eclipse\settings\%1
echo.
call:echo-copy-into %base%\source\jee1ap101\jee1ap101-%1\nbproject\*.* %jee1%\netbeans\nbproject\%1
call copy %base%\source\jee1ap101\jee1ap101-%1\nbproject\*.* %jee1%\netbeans\nbproject\%1
echo.
goto:eof

:echo-copy-into
echo copy %1
echo into %2
echo.
goto:eof
