@ECHO OFF
rem ---------------------------------------------------------------------------
rem Execute groovy script using the projects library directory.
rem ---------------------------------------------------------------------------

SET VARS_HOME=%~dp0..
SET VARS_CLASSPATH="%VARS_HOME%\conf";"%VARS_HOME%\lib\*"

SET GROOVY_CLASSPATH="%VARS_HOME%\scripts\groovy";%VARS_CLASSPATH%

if (%1)==() SET ARGS="%VARS_HOME%\scripts\groovy\openshell.groovy" ELSE ARGS=%*

java -Xms16m -Xmx512m -Duser.timezone="UTC" -classpath %GROOVY_CLASSPATH% groovy.ui.GroovyMain %ARGS%
