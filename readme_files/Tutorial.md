# SETUP / INSTALLATION TUTORIAL
This is a 6 step tutorial that explains how to install and setup the Refresher program:

## WHAT YOU NEED (Prerequisites)
- Git:
  - [link-to-git-installation-tutorial-for-windows](https://www.geeksforgeeks.org/install-git-on-windows/) 
  - [link-to-git-installation-tutorial-other-OS](https://www.geeksforgeeks.org/how-to-install-git/)
- Java jdk: 
  - [link-to-jdk-installation-tutorial](https://www.geeksforgeeks.org/download-and-install-java-development-kit-jdk-on-windows-mac-and-linux/)
- Notion: [NOTION TUTORIAL](NotionTutorial.md)
  - blockIds for blocks you want refreshed
  - Notion API key, that can make changes to these blocks

## TUTORIAL STEPS FOR REFRESHER PROGRAM

1. Open CMD, clone the git repository and move to the repository directory:

    ![alt text](assets/Tutorial-cmd.png)
    ```console
    C:\Users\your_name> git clone https://github.com/dbomen/DTRN.git
    ```
    ```console
    C:\Users\your_name> cd DTRN
    ```

2. Run the Controller program

    ```console
    C:\Users\your_name\DTRN> .\controller.bat
    ```
    ![alt text](assets/Tutorial-run-controller.png)

3. Add your Notion API KEY with the ``a`` command

    ```console
    >a secret_1234567890abcdefghijklmnopqrstuvwxyz123456
    ```

4. Add your blocks with the ``b`` command. Note that you can choose the type of refreshing for each block:
    - 1: refresh daily
    - 2: refresh weekly

    ```console
    >b 1234567890abcdefghijklmnopqrstuv 1 vutsrqponmlkjihgfedcba0987654321 2
    ```

    - In the example above, the block with the id:
      - ``1234567890abcdefghijklmnopqrstuv`` will be refreshed daily (type: ``1``)
      - ``vutsrqponmlkjihgfedcba0987654321`` will be refreshed weekly (type: ``2``)
  
5. OPTIONALLY check for correctness with the ``s`` command

    ```console
    >s
    ```
    ![alt text](assets/Tutorial-s-command.png)

6. Activate the Refresher program with the ``activate`` command

    ```console
    >activate
    ```

### YOU ARE ALL SET!

- If you want to deactivate the Refresher program you can do that in the Controller program with the ``deactivate`` command or manually in the Task Scheduler application.
- If you want to add / change blocks or the notion api key, you can do that with the ``b`` and ``a`` commands in the Controller program. Note that you do not have to ``deactivate`` and again ``activate`` the tasks in Task Scheduler.