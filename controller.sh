#!/bin/bash
# makes sure that the script is run in the same directory as the controller.sh file
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR" || exit 1

# SETUP
(
source helperBatFiles/vars.sh DTRN_SUPER_SECRET
source helperBatFiles/buildChecker.sh DTRN_SUPER_SECRET

# Run the CONTROLLER class
echo "[Controller] Running $CONTROLLER_CLASS..."
java -cp "$BIN_DIR":"$CLASSPATH" "$CONTROLLER_CLASS"
)
