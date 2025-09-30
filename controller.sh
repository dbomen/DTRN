#!/bin/bash
# makes sure that the script is run in the same directory as the controller.sh file
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR" || exit 1

# SETUP
(
source helperBatFiles/vars.sh DTRN_SUPER_SECRET
source helperBatFiles/buildChecker.sh DTRN_SUPER_SECRET

# check for git updates
git fetch origin master
COMMITS_AHEAD=$(git rev-list HEAD..origin/master --count)
if [ "$COMMITS_AHEAD" -gt 0 ]; then
    echo "[Controller] Update available! Run the 'u' command to update the master branch."
fi

# Run the CONTROLLER class
echo "[Controller] Running $CONTROLLER_CLASS..."
java -cp "$BIN_DIR":"$CLASSPATH" "$CONTROLLER_CLASS"
)
