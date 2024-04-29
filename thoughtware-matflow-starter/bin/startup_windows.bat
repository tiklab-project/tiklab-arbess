
@echo off

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

REM PGSQL路径
set PGSQL_VERSION=pgsql-10.23
set PGSQL_DIR=%EMBEDDED_DIR%\%PGSQL_VERSION%
echo PGSQL_DIR:%PGSQL_DIR%

if not exist "%PGSQL_DIR%\bin" (
    REM 解压文件
    tar -xvf %PGSQL_DIR%\%PGSQL_VERSION%.tar.gz -C %EMBEDDED_DIR%
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

set PID=0
for /f "usebackq tokens=1-2" %%a in (`jps -l ^| findstr %APP_MAIN%`) do (
set PID=%%a
)

if not exist "%DIRS%\logs" (
        cd %DIRS%
        md logs
)

REM 判断pid
if %PID% == 0 (
    cd %JAVA_HOME%\bin
    start /b .\java.exe %JAVA_OPTS% %CLASSPATH% %APP_MAIN% >> %DIRS%info.log
    echo %APP_MAIN% START STARTING.............
) else (
    echo ================================================================================================================
    echo %APP_MAIN% already started PID=%PID%
)

timeout /t 5 >nul

REM 获取状态
set state=
for /f "usebackq tokens=1-2" %%c in (`jps -l ^| findstr %APP_MAIN%`) do (
    set state=%%c
)

echo ================================================================================================================

if "%state%" == "" (
    echo %APP_MAIN% START FAIL
) else (
    echo %APP_MAIN% START SUCCESS PID=%state%
)
echo ================================================================================================================














