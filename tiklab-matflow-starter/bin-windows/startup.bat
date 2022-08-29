
@echo off

set CurrentPath=%~dp0

set P1Path=

:begin
for /f "tokens=1,* delims=\" %%i in ("%CurrentPath%") do (set content=%%i&&set CurrentPath=%%j)
if "%P1Path%%content%\" == "%~dp0" goto end

set P1Path=%P1Path%%content%\

goto begin

:end

set DIRS=%P1Path%
set JAVA_HOME=%DIRS%/jdk-16.0.2

echo %DIRS%

Xcopy %DIRS%/temp %DIRS% /E/H/C

set APP_MAIN="com.tiklab.matflow.MatFlowApplication"

set APP_CONFIG=%DIRS%/conf/application-${env}.properties

set CLASSPATH=%DIRS%/conf

set APP_LOG=%DIRS%/logs

set JAVA_OPTS="%JAVA_OPTS% -server -Xms512m -Xmx512m -Xmn128m -XX:ParallelGCThreads=20 -XX:+UseParallelGC -XX:MaxGCPauseMillis=850 -Xloggc:$APP_LOG/gc.log -Dfile.encoding=UTF-8"
set JAVA_OPTS="%JAVA_OPTS% -DlogPath=%APP_LOG%"
set JAVA_OPTS="%JAVA_OPTS% -Dconf.config=file:%APP_CONFIG%"
set JAVA_OPTS="%JAVA_OPTS% --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.sql/java.sql=ALL-UNNAMED  -classpath"

set comment=%DIRS%/comment
@echo off & setlocal enabledelayedexpansion
for /f "delims=" %%i in ('dir / b /a-d /s "%comment%"') do echo %%~nxi & set s=!s!:%%~nxi
echo %s%

set public=%DIRS%/lib
@echo off & setlocal enabledelayedexpansion
for /f "delims=" %%i in ('dir / b /a-d /s "%public%"') do echo %%~nxi & set st=!st!:%%~nxi
echo %st%

set CLASSPATH=%s%:%st%

echo "%JAVA_HOME%="%JAVA_HOME%
echo "%JAVA_OPTS%="%JAVA_OPTS%
echo "%CLASSPATH%="%CLASSPATH%
echo "%APP_HOME%="%APP_HOME%
echo "%APP_MAIN%="%APP_MAIN%

set PID=0

set main="com.tiklab.matflow.MatFlowApplication"

for /f "usebackq tokens=1-2" %%a in (`jps -l ^| findstr %main%`) do (
set PID=%%a
)

echo pid: %PID%

if "%PID%" == 0 (

    %JAVA_HOME%/bin/java.exe %JAVA_OPTS% %CLASSPATH% %APP_MAIN%

    for /f "usebackq tokens=1-2" %%a in (`jps -l ^| findstr %main%`) do (
        set PID=%%a
        )

    if "%PID%" == 0 (
        echo "[failed]"
        echo "================================================================================================================"
    else(
        echo (PID=%PID%)...[success]
        echo "================================================================================================================"
    )
) else (
    echo %APP_MAIN% already started(PID=%PID%)
    echo "================================================================================================================"
   )





















