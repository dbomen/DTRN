#!/bin/bash

# we check if the value in $1 (first argument) is correct, as we do not want this file
# to be run manually. It should be called from either the controller.sh or refresher.sh files
# as they assert the correct path
if [[ "$1" != "DTRN_SUPER_SECRET" ]]; then
    echo "You should not run this script directly. Run controller.sh or refresher.sh files instead!"
    exit 1
fi

# Require root
if [[ $EUID -ne 0 ]]; then
  echo "This needs root. Re-run: sudo $0 DTRN_SUPER_SECRET"
  exit 1
fi
# Require systemd
if ! pidof systemd >/dev/null; then
  echo "You do not have systemd. You can run the ./refresher.sh script when you want to refresh your Notion blocks or use some other way to run this script automatically."
  exit 1
fi

systemctl stop DTRN_Refresher_Daily.timer
systemctl stop DTRN_Refresher_Daily.service
systemctl stop DTRN_Refresher_Weekly.timer
systemctl stop DTRN_Refresher_Weekly.service
systemctl disable DTRN_Refresher_Daily.timer
systemctl disable DTRN_Refresher_Weekly.timer
rm -f "/etc/systemd/system/DTRN_Refresher_Daily.timer"
rm -f "/etc/systemd/system/DTRN_Refresher_Daily.service"
rm -f "/etc/systemd/system/DTRN_Refresher_Weekly.timer"
rm -f "/etc/systemd/system/DTRN_Refresher_Weekly.service"
systemctl daemon-reload
