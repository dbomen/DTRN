#!/bin/bash
# makes sure that the script is run in the same directory as the refresher.sh file
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR" || exit 1

echo [Refresher] STARTING REFRESHER

# SETUP
(
source helperBatFiles/vars.sh DTRN_SUPER_SECRET
source helperBatFiles/buildChecker.sh DTRN_SUPER_SECRET

# Run the DTRN class
echo "[Refresher] Running $DTRN_CLASS..."
java -cp "$BIN_DIR":"$CLASSPATH" "$DTRN_CLASS" $1
)
