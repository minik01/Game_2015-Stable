REM Run server

cd bin
java -classpath ".;sqlite-jdbc-3.8.6.jar" Server

if errorlevel 1 echo "Unsuccessful start"
	pause