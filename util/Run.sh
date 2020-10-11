#!/bin/bash
cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
source $(pwd)/Directories.sh

java -jar --module-path $PATH_TO_FX --add-modules javafx.controls $snake/Snake.jar