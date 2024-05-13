@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion

REM 启动类文件
set APP_MAIN=io.thoughtware.matflow.starter.MatFlowApplication


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

REM 检查目录是否存在，如果不存在则创建
if not exist "%DIRS%" (
    mkdir "%DIRS%"
    if errorlevel 1 (
        echo Failed to create directory %DIRS%.
        exit
    )
)

REM 内嵌应用地址
set EMBEDDED_DIR=%DIRS%embbed
echo EMBEDDED_DIR:%EMBEDDED_DIR%

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

rem 判断java端口是否被占用
for /f "tokens=5" %%a in ('netstat -aon ^| findstr LISTENING ^| findstr "%SERVER_PORT%"') do (
    set SERVER_PID=%%a
)
if not "%SERVER_PID%" == "" (
    echo "Port %SERVER_PORT% is occupied by process with PID: %SERVER_PID%"
    exit
)

rem 判断pgsql端口是否被占用
for /f "tokens=5" %%a in ('netstat -aon ^| findstr LISTENING ^| findstr "%PGSQL_PORT%"') do (
    set PGSQL_PID=%%a
)
if "%PGSQL_ENABLE%" == "true" (
    if not "%PGSQL_PID%" == "" (
        echo "Port %PGSQL_PORT% is occupied by process with PID: %PGSQL_PID%"
        exit
    )
)

REM JDK路径
set JAVA_HOME=%EMBEDDED_DIR%\jdk-16.0.2
echo JAVA_HOME:%JAVA_HOME%
if not exist "%JAVA_HOME%" (
    echo Unable to obtain the JAVA_HOME path!
    goto :start_error
)

REM PGSQL路径
set PGSQL_VERSION=pgsql-10.23
set PGSQL_DIR=%EMBEDDED_DIR%\%PGSQL_VERSION%
echo PGSQL_DIR:%PGSQL_DIR%

if not exist "%PGSQL_DIR%\bin" (
    REM 解压文件
    tar -xvf %PGSQL_DIR%\%PGSQL_VERSION%.tar.gz -C %EMBEDDED_DIR%>nul 2>nul
    if not exist "%PGSQL_DIR%\bin" (
        echo Unable to obtain the PGSQL_DIR path!
        goto :start_error
    )
)

set CLASSPATH=%DIRS%lib\*

set APP_HOME=%DIRS%
set APP_LOG=%DIRS%logs\
set APP_CONFIG=%DIRS%conf\application.yaml

set JAVA_OPTS=-server -Xms512m -Xmx512m -Xmn128m -XX:ParallelGCThreads=20 -XX:+UseParallelGC -XX:MaxGCPauseMillis=850 -Xloggc:%APP_LOG%gc.log
set JAVA_OPTS=%JAVA_OPTS% -DlogPath=%APP_LOG% -Duser.timezone=GMT+08
set JAVA_OPTS=%JAVA_OPTS% -Dspring.config.location=file:%APP_CONFIG%
set JAVA_OPTS=%JAVA_OPTS% --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.sql/java.sql=ALL-UNNAMED  -classpath


cd %JAVA_HOME%\bin

set PID=0
for /f "usebackq tokens=1-2" %%a in (`.\jps.exe -l ^| findstr %APP_MAIN%`) do (
set PID=%%a
)

if not exist "%DIRS%\logs" (
    cd %DIRS%
    md logs
)

cd %JAVA_HOME%\bin

REM 判断pid
if %PID% == 0 (
    rem cd %JAVA_HOME%\bin
    start /b .\java.exe %JAVA_OPTS% %CLASSPATH% %APP_MAIN% >> %DIRS%info.log
    echo %APP_MAIN% START STARTING.............
) else (
    echo =============================================================================================================
    echo %APP_MAIN% already started PID=%PID%
    echo ================================================================================================================
    exit
)

timeout /t 5 >nul

REM 获取状态
set state=
for /f "usebackq tokens=1-2" %%c in (`.\jps.exe -l ^| findstr %APP_MAIN%`) do (
    set state=%%c
)

REM 运行 ipconfig 命令并将输出重定向到临时文件
ipconfig > temp.txt

REM 从临时文件中提取 IPv4 地址信息并存储在变量中
for /f "tokens=2 delims=:" %%a in ('find "IPv4" temp.txt') do (
    set IP_ADDRESS=%%a
)

REM 删除临时文件
del temp.txt
set "IP_ADDRESS=!IP_ADDRESS: =!"

for /f "tokens=5" %%a in ('netstat -aon ^| findstr LISTENING ^| findstr "%PGSQL_PORT%"') do (
    set PGSQL_PID=%%a
)
if "%state%" == "" (
    echo ================================================================================================================
    echo %APP_MAIN% START FAIL
    echo ================================================================================================================
) else (
    echo DCS START PORT:%DCS_PORT%
    if "%PGSQL_ENABLE%" == "true" (
        if not "%PGSQL_PID%" == "" (
            echo PGSQL START SUCCESS PORT:%PGSQL_PORT%(PID=%PGSQL_PID%^)
        )
    )
    echo %APP_MAIN% START SUCCESS PORT:%SERVER_PORT%(PID=%state%^)
    echo ====================================点击以下连接即可访问======================================================
    echo http://%IP_ADDRESS%:%SERVER_PORT%
    echo ================================================================================================================
)

cd %DIRS%bin






