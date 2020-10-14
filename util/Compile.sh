#!/bin/bash
cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
source $(pwd)/Directories.sh

cd $src
find . -name "*.java" > sources.txt
javac -d $bin --module-path $PATH_TO_FX --add-modules javafx.controls @sources.txt
rm sources.txt

cd $bin
jar cfe $snake/Snake.jar snake.Main snake/