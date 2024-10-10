# DTRN - Daily TODO Refresher for Notion
Notion TODO refresher for Windows. You can create Groups that hold TODOs. The TODOs will be refreshed daily. TODOs come in 2 different types:
- `[R]` - Refresher - A fixed TODO that is unchecked `DEFAULT`
- `[T]` - Temporary - A temporary TODO that is:
  - deleted if checked
  - left alone if not checked (completed)

## CONTROLLER
The controller is an interface that lets you create your TODOs. You can show/add/edit **groups** and a **specific TODO** in a group. If there are any errors while refreshing the TODOs, you will be able do display it in the controller.

### COMMANDS
- `s`: show all groups
- `a [group_name]`: add a group
- `e [group_name] [new_group_name]`: change group name
- `d [group_name]`: make an executable file for group
- `gs [group_name]`: show TODOs in group
- `ga [group_name] [todo_content]`: add TODO to group with default type
  - `ga [group_name] [todo_content] [todo_type]{R,T}`: add TODO with custom type
- `ge [todo_id] [OPTION]`
  - OPTION: `-c=[new_todo_content]`: change todo_content
  - OPTION: `-t=[new_todo_type]{R,T}`: change todo_type
- `err`: show all errors
  - `err [err_id]`: show error message
- `h`: display all command options

---
---
---
TODO: (...)

## HOW TO USE

### REQUIREMENTS
- **Notion API key** (Internal Integration Secret): [how to get a Notion API key](https://developers.notion.com/docs/create-a-notion-integration)
- **Block ID** of the block you want this application to refresh: [how to get blockId](https://stackoverflow.com/questions/67618449/how-to-get-the-block-id-in-notion-api)

### STEP BY STEP
- run DTRN_Controller.exe
  - **only on first time:** input Notion API key
  - **only first time:** input Block ID
  - ...
  - [Windows Task Scheduler Tutorial](https://www.youtube.com/watch?v=HAOP0HZeDJg)
