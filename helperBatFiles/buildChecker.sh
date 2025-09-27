#!/bin/bash

# we check if the value in $1 (first argument) is correct, as we do not want this file
# to be run manually. It should be called from either the controller.sh or refresher.sh files
# as they assert the correct path
if [[ "$1" != "DTRN_SUPER_SECRET" ]]; then
    echo "You should not run this script directly. Run controller.sh or refresher.sh files instead!"
    exit 1
fi

# /file checking
# ===================================================
# we check for /files
if [[ ! -d "$FILES_DIR" ]]; then
    echo "[BuilderChecker] '$FILES_DIR' directory does not exist! Adding directory..."
    mkdir "$FILES_DIR"
fi

# we check for files in /files
if [[ ! -f "$FILES_DIR/DTRNSettings.json" ]]; then
    echo "[BuilderChecker] '$FILES_DIR/DTRNSettings.json' file does not exist! Adding file..."
    touch "$FILES_DIR/DTRNSettings.json"
    echo "{\"run_once\":1, \"blockIds\":[], \"notionAPIKey\":-1}" >> "$FILES_DIR/DTRNSettings.json"
fi
if [[ ! -f "$FILES_DIR/.gitignore" ]]; then
    echo "[BuilderChecker] '$FILES_DIR/.gitignore' file does not exist! Adding file..."
    touch "$FILES_DIR/.gitignore"
    echo "*" >> "$FILES_DIR/.gitignore"
    echo "!.gitignore" >> "$FILES_DIR/.gitignore"
fi
# ===================================================

# /libs checking
# ===================================================
# we check for /libs
if [[ ! -d "$LIBS_DIR" ]]; then
    echo "[BuilderChecker] '$LIBS_DIR' directory does not exist! Adding directory..."
    mkdir "$LIBS_DIR"
fi

# we check for dependecy files in /libs
if [[ ! -f "$LIBS_DIR/gson.jar" ]]; then
    echo "[BuilderChecker] '$LIBS_DIR/gson.jar' does not exist! Adding dependency..."
    echo "Downloading gson.jar from https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar"
    if curl -fL -o "$LIBS_DIR/gson.jar" "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar"; then
        echo "Downloaded successfully."
    else 
            echo "Curl command failed! Please manually download gson.jar from the following link:"
            echo "https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar"
            echo "Make sure to put the .jar file in the /libs folder!"
            exit 1
    fi
fi

# we check for other files in /libs
if [[ ! -f "$LIBS_DIR/.gitignore" ]]; then
    echo "[BuilderChecker] '$LIBS_DIR/.gitignore' file does not exist! Adding file..."
    touch "$LIBS_DIR/.gitignore"
    echo "*" >> "$LIBS_DIR/.gitignore"
    echo "!.gitignore" >> "$LIBS_DIR/.gitignore"
fi
# ===================================================

# /bin checking
# ===================================================
# we check for /bin directory
if [[ ! -d "$BIN_DIR" ]]; then
    echo "[BuilderChecker] '$BIN_DIR' directory does not exist! Adding folder..."
    mkdir "$BIN_DIR"
fi

# we check for files in /bin
if [[ ! -f "$BIN_DIR/.gitignore" ]]; then
    echo "[BuilderChecker] '$BIN_DIR/.gitignore' file does not exist! Adding file..."
    touch "$BIN_DIR/.gitignore"
    echo "*" >> "$BIN_DIR/.gitignore"
    echo "!.gitignore" >> "$BIN_DIR/.gitignore"
fi
# ===================================================

# Compiling Java project
# ===================================================
# we check if JDK is installed
if ! command -v javac >/dev/null 2>&1; then
    echo "[BuilderChecker] JDK not found! Please make sure Java is installed and added to the PATH."
    exit 1
fi

echo "[BuilderChecker] Compiling Java project..."

# we recursively get all the .java files within the $SRC_MAIN_DIR
mapfile -d '' JAVA_FILES < <(find "$SRC_MAIN_DIR" -type f -name '*.java' -print0)
if (( ${#JAVA_FILES[@]} == 0 )); then
  echo "[BuilderChecker] No .java files found under '$SRC_MAIN_DIR'."
  exit 0
fi

# Compile all at once
if ! javac -cp "$CLASSPATH" -d "$BIN_DIR" "${JAVA_FILES[@]}"; then
  echo "[BuilderChecker] Compilation failed!"
  exit 1
fi
# ===================================================
