#!/bin/bash

# we check if the value in $1 (first argument) is correct, as we do not want this file
# to be run manually. It should be called from either the controller.sh or refresher.sh files
# as they assert the correct path
if [[ "$1" != "DTRN_SUPER_SECRET" ]]; then
    echo "You should not run this script directly. Run controller.sh or refresher.sh files instead!"
    exit 1
fi

export FILES_DIR="files"
export LIBS_DIR="libs"

export SRC_MAIN_DIR="src/main"
export BIN_DIR="src/bin"

export CLASSPATH="$LIBS_DIR/gson.jar"

export DTRN_CLASS="main.DTRN"
export CONTROLLER_CLASS="main.controller.Controller"
