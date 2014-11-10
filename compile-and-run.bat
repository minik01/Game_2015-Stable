REM Compile

COPY res\* bin
javac src\Server.java -cp src -d bin

REM Run server

cd bin
java -classpath ".;sqlite-jdbc-3.8.6.jar" Server

if errorlevel 1 echo "Unsuccessful start"
	pause