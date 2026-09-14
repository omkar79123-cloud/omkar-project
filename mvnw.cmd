@REM Maven Wrapper for Windows — no global Maven install required.
@ECHO OFF
SETLOCAL
SET MAVEN_PROJECTBASEDIR=%~dp0
IF NOT "%MAVEN_PROJECTBASEDIR:~-1%"=="\" SET MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR%\
SET WRAPPER_HOME=%USERPROFILE%\.m2\wrapper\dists
SET DOWNLOAD_URL=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.9/apache-maven-3.9.9-bin.zip
SET MAVEN_HOME=%USERPROFILE%\.m2\wrapper\apache-maven-3.9.9

IF EXIST "%MAVEN_HOME%\bin\mvn.cmd" GOTO RUN

ECHO Downloading Maven 3.9.9 (one-time)...
IF NOT EXIST "%TEMP%\campusbites-mvn" mkdir "%TEMP%\campusbites-mvn"
powershell -NoProfile -Command "Invoke-WebRequest -Uri '%DOWNLOAD_URL%' -OutFile '%TEMP%\campusbites-mvn\maven.zip'"
IF NOT EXIST "%USERPROFILE%\.m2\wrapper" mkdir "%USERPROFILE%\.m2\wrapper"
powershell -NoProfile -Command "Expand-Archive -Force -Path '%TEMP%\campusbites-mvn\maven.zip' -DestinationPath '%USERPROFILE%\.m2\wrapper'"

:RUN
"%MAVEN_HOME%\bin\mvn.cmd" %*
ENDLOCAL
