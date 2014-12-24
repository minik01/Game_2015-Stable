#!/bin/bash
#javac src/game.java -cp src -d bin
cd bin
ls
rm *
echo "==================="
ls
cd ..
cp res/* bin
javac src/Server.java -cp src  -d bin
