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

set PID=0

for /f "usebackq tokens=1-2" %%a in (`%JAVA_HOME%\bin\jps.exe -l ^| findstr %APP_MAIN%`) do (
set PID=%%a
)

if %PID% == 0 (
     echo ================================================================================================================
     echo %APP_MAIN%  is not running
) else (
    echo ================================================================================================================
    echo %APP_MAIN% already started(PID=%PID%)
   )