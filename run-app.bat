@echo off
setlocal
cd /d "%~dp0"

if not exist "target\classes" (
  mkdir "target\classes"
)

javac --release 21 -encoding UTF-8 -cp "lib\forms_rt.jar" -d "target\classes" src\main\java\pt\ipleiria\es\worldcup\Main.java src\main\java\pt\ipleiria\es\worldcup\ui\*.java
if errorlevel 1 (
  pause
  exit /b 1
)

javaw -cp "target\classes;lib\forms_rt.jar" pt.ipleiria.es.worldcup.Main
endlocal
