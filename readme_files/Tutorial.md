# SETUP / INSTALLATION TUTORIAL
This is a 6-step tutorial that explains how to install and set up the Refresher program.

**HEADERS:**
- [WHAT YOU NEED (Prerequisites)](#what-you-need-prerequisites)
- [TUTORIAL STEPS FOR REFRESHER PROGRAM](#tutorial-steps-for-refresher-program)

## WHAT YOU NEED (Prerequisites)
- **Git**:
  - [Git Installation Tutorial for Windows](https://www.geeksforgeeks.org/install-git-on-windows/) 
  - [Git Installation Tutorial for Other Operating Systems](https://www.geeksforgeeks.org/how-to-install-git/)
- **Java JDK**: 
  - [JDK Installation Tutorial](https://www.geeksforgeeks.org/download-and-install-java-development-kit-jdk-on-windows-mac-and-linux/)
- **Notion**: Refer to the [NOTION TUTORIAL](NotionTutorial.md)
  - Block IDs for the blocks you want refreshed.
  - A Notion API key that can make changes to these blocks.

## TUTORIAL STEPS FOR REFRESHER PROGRAM

- [Windows](Tutorial_Windows.md)
- [Linux (systemd)](Tutorial_Linux_systemd.md)
- **MacOS and Linux (other):** you can follow the [Linux (systemd)](Tutorial_Linux_systemd.md) tutorial, but note that `activate` and `deactivate` won't work, and you'll have to either:  
    - manually run `./refresher.sh`, or  
    - set up your own way to run the script automatically (e.g. `cron`, `launchd`, ...).
