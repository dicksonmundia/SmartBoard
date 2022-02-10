Smart Board is a desktop-based Kanban-style application for managing personal work. It has a similar concept to Trello,
which is a web-based Kanban-style application to enable teams to manage any sized collaborative projects from start to
finish. You can have a look at Trello website (https://trello.com/en) to see how a Trello board looks like.
Task Specification
Basic functional requirements are listed below (mandatory).
• The application can have many users.
• Each user can create a profile, with a unique username, password, the first name and the last name, and the
profile photo. If there is no photo, a default profile photo is provided by the application.
• Once the username and password are created, the user can log in.
• Each user has an empty workspace after first login and can perform following actions on the workspace:
o Edit the profile (i.e., change the first name or the last name, select a new profile photo).
Create a new project board representing a personal project to work on. Each project board has a name
and columns representing stages in the workflow of the project (e.g., to do, in-progress, completed).
o Set one project as default and unset the default setting. When multiple projects exist in the workspace,
the default project will be the board shown to the user after login. If no project is set as default, the first
added project will be the board shown to the user after login.
o Add a new column to the project board. Each column has a name and a list of tasks.
o Add a task card to the column. Each task has a name and description.
o Edit the task name and description.
o Reorder tasks within a column.
o Move a task to another column.
o Rename a project board/column.
o Delete an existing project board/column/task.
o Log out (and go back to login page).
• The workspace will display an inspirational quote every time after the user logs in to motivate the user to work
on the project. The quote to be displayed will be randomly chosen from a list of quotes at each login and remain
the same during the same login period. This list can be hard coded in your application. You are free to choose any
quotes that you like (e.g., https://www.goodreads.com/quotes/tag/motivation). A minimum of 3 quotes is
required to demonstrate the random quote selection at each login.
• The application data should persist between logins for the same user.
