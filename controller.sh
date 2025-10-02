#!/bin/bash
# makes sure that the script is run in the same directory as the controller.sh file
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR" || exit 1

# SETUP
(
source helperBatFiles/vars.sh DTRN_SUPER_SECRET
source helperBatFiles/buildChecker.sh DTRN_SUPER_SECRET

# check for git updates
echo "[Controller] Checking for git project updates..."
git fetch origin master 2>/dev/null
COMMITS_AHEAD=$(git rev-list master..origin/master --count)
if [ "$COMMITS_AHEAD" -gt 0 ]; then
    echo "[Controller] Update available! Run the 'u' command to update the master branch."
else
    echo "[Controller] No updates found."
fi

# Run the CONTROLLER class
echo "[Controller] Running $CONTROLLER_CLASS..."
java -cp "$BIN_DIR":"$CLASSPATH" "$CONTROLLER_CLASS"
)
