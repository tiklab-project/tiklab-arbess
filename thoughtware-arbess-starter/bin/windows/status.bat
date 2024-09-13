@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion
REM 启动类文件
set APP_MAIN=io.thoughtware.arbess.starter.ArbessApplication

REM 获取系统文件地址
set CurrentPath=%~dp0
set P1Path=
:begin
for /f "tokens=1,* delims=\" %%i in ("%CurrentPath%") do (set content=%%i&&set CurrentPath=%%j)
if "%P1Path%%content%\" == "%~dp0" goto end

set P1Path=%P1Path%%content%\

goto begin

:end

set DIRS=%P1Path%
REM 输出基本文件地址
echo DIRS:%DIRS%

REM 内嵌应用地址
set EMBEDDED_DIR=%DIRS%embbed
echo EMBEDDED_DIR:%EMBEDDED_DIR%

REM JDK路径
set JAVA_HOME=%EMBEDDED_DIR%\jdk-16.0.2
echo JAVA_HOME:%JAVA_HOME%
if not exist "%JAVA_HOME%" (
    echo Unable to obtain the JAVA_HOME path!
    goto :start_error
)


set YAML_FILE=%DIRS%conf\application.yaml
set SERVER_PORT=
set DCS_PORT=
set PGSQL_PORT=
set PGSQL_ENABLE=
set DATE_HOME=
set values=

rem 获取ServerPort
for /f "tokens=1,* delims=:" %%a in ('type "%YAML_FILE%"') do (
    rem 输出当前行内容，以便调试
    if "%%a" == "server" (
        set values=1
    )
    if "!values!" == "1" (
        if "%%a" == "  port" (
            set SERVER_PORT=%%b
            set values=0
            goto found
        )
    )
)

:found
set "SERVER_PORT=!SERVER_PORT: =!"
rem echo Apply server port:%SERVER_PORT%

rem 获取DateHome
for /f "tokens=1,* delims=:" %%a in ('type "%YAML_FILE%"') do (
    rem 输出当前行内容，以便调试
    if "%%a" == "DATA_HOME" (
         set DATE_HOME=%%b
    )
)

:found
set "DATE_HOME=!DATE_HOME: =!"
rem echo Apply data home:%DATE_HOME%


rem 获取DcsPort
for /f "tokens=1,* delims=:" %%a in ('type "%YAML_FILE%"') do (
    rem 输出当前行内容，以便调试
    if "%%a" == "dcs" (
        set values=1
    )
    if "!values!" == "1" (
        if "%%a" == "  server" (
            set values=2
        )
    )
    if "!values!" == "2" (
        if "%%a" == "    port" (
            set DCS_PORT=%%b
            set values=0
            goto found
        )
    )
)

:found
set "DCS_PORT=!DCS_PORT: =!"
rem echo Apply dcs port:%DCS_PORT%

rem 获取PgsqlPort
for /f "tokens=1,* delims=:" %%a in ('type "%YAML_FILE%"') do (
    rem 输出当前行内容，以便调试
    if "%%a" == "postgresql" (
        set values=1
    )
    if "!values!" == "1" (
        if "%%a" == "  db" (
            set values=2
        )
    )
    if "!values!" == "2" (
        if "%%a" == "    port" (
            set PGSQL_PORT=%%b
            set values=0
            goto found
        )
    )
)

:found
set "PGSQL_PORT=!PGSQL_PORT: =!"
rem echo Apply pgsql port:%PGSQL_PORT%


rem 获取PgsqlPort
for /f "tokens=1,* delims=:" %%a in ('type "%YAML_FILE%"') do (
    rem 输出当前行内容，以便调试
    if "%%a" == "postgresql" (
        set values=1
    )
    if "!values!" == "1" (
        if "%%a" == "  embbed" (
            set values=2
        )
    )
    if "!values!" == "2" (
        if "%%a" == "    enable" (
            set PGSQL_ENABLE=%%b
            set values=0
            goto found
        )
    )
)

:found
set "PGSQL_ENABLE=!PGSQL_ENABLE: =!"
rem echo Apply enable pgsql:%PGSQL_ENABLE%

echo ===============================================================================================================
for /f "tokens=5" %%a in ('netstat -aon ^| findstr LISTENING ^| findstr %PGSQL_PORT%') do (
        set PGSQL_PID=%%a
    )
rem 判断pgsql端口是否被占用
if "%PGSQL_ENABLE%" == "true" (
    if not "%PGSQL_PID%" == "" (
        echo PGSQL ALREADY STARED PORT:%PGSQL_PORT%(PID=%PGSQL_PID%^)
    ) else (
        echo PGSQL IS NOT RUNNING......
    )
)

set PID=0

for /f "usebackq tokens=1-2" %%a in (`%JAVA_HOME%\bin\jps.exe -l ^| findstr %APP_MAIN%`) do (
set PID=%%a
)

if %PID% == 0 (
     echo %APP_MAIN% IS NOT RUNNING......
     echo ===============================================================================================================
) else (
    echo %APP_MAIN% ALREADY STARTED PORT:%SERVER_PORT%(PID=%PID%^)
    echo ================================================================================================================
   )