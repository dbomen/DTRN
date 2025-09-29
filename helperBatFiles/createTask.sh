#!/bin/bash

# we check if the value in $1 (first argument) is correct, as we do not want this file
# to be run manually. It should be called from either the controller.sh or refresher.sh files
# as they assert the correct path
if [[ "$1" != "DTRN_SUPER_SECRET" ]]; then
    echo "You should not run this script directly. Run controller.sh or refresher.sh files instead!"
    exit 1
fi
#
# Require root
if [[ $EUID -ne 0 ]]; then
  echo "This needs root. Re-run: sudo $0 DTRN_SUPER_SECRET"
  exit 1
fi

script_path=$(cd .. && pwd)
script_path="${script_path}/refresher.sh"
echo $script_path

cat << EOF > /etc/systemd/system/DTRN_Refresher_Daily.timer
[Unit]
Description=DTRN Daily Refresher timer

[Timer]
OnCalendar=*-*-* 3:00:00
Persistent=true

[Install]
WantedBy=timers.target
EOF
cat << EOF > /etc/systemd/system/DTRN_Refresher_Daily.service
[Unit]
Description=DTRN Daily Refresher service

[Service]
Type=oneshot
ExecStartPre=/bin/sleep 60
ExecStart=${script_path}
EOF

cat << EOF > /etc/systemd/system/DTRN_Refresher_Weekly.timer
[Unit]
Description=DTRN Weekly Refresher timer

[Timer]
OnCalendar=Mon *-*-* 3:00:00
Persistent=true

[Install]
WantedBy=timers.target
EOF
cat << EOF > /etc/systemd/system/DTRN_Refresher_Weekly.service
[Unit]
Description=DTRN Weekly Refresher service

[Service]
Type=oneshot
ExecStartPre=/bin/sleep 60
ExecStart=${script_path}
EOF

systemctl daemon-reload
systemctl enable DTRN_Refresher_Daily.timer
systemctl start DTRN_Refresher_Daily.timer
systemctl enable DTRN_Refresher_Weekly.timer
systemctl start DTRN_Refresher_Weekly.timer
