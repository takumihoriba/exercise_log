# Exercise log and recommendation

## What will this application do?

This application will keep track of activities a user enters and suggest activities so that a user can achieve their weekly goal. I expect users will be those who want to exercise but are sometimes unsure of what to do, which includes me. I am motivated to develop this application because it will be useful if there is an app that recommends me some activities when I want to do some exercises  **based on some factor**. The factors might be activities completed in last week, user's preference, and/or how much exercise is needed to achieve the weekly goal. In this project, I'd like to **simplify** calculation of amount of exercise by relying on time duration user inputs. 

## User stories

In this project:

- As a user, I want to be able to add exercise to this application *for multiple times*.
- As a user, I want to be able to see recommendation of exercise when I request.
- As a user, I want to be able to set/modify a goal.
- As a user, I want to be able to see how far I am from achieving that goal. 
- As a user, I want to be able to add new sports I play.
- As a user, I want to be able to save my exercise log to file.
- As a user, I want to be able to load my exercise log from file.


## Instructions

- You can generate the first required event related to adding exercises you did to a exerciseLog by clicking the "Show history" button to check what exercises you have done and how long you have spent on each instance of exercise. This will also update the table in the main panel. Without clicking this button, the table in the main panel stays the same.
- You can generate the second required event related to adding exercises you did to a exerciseLog by clicking the "Show summary" button. GUI will show how much time was spent on each sport in total.
- You can locate my visual component by looking at buttons that have small icons and top-left corner of GUI. This part will show several images depending on the state of the exercise log. For example, if an exercise is logged, water bottle will be displayed to encourage hydration. If data is loaded from file, it will show a muscle icon to encourage starting exercise now. If logging is unsuccessful, history is shown or exercise log is saved to file, default image will be displayed. This display works together with some text on the right to better inform user about state of this app.  
- You can save the state of my application by clicking the button named "Save to file". Please note that this app doesn't prompt to ask you if you want to save when you try to close the app.
- You can reload the state of my application by clicking the button named "Load from file". Please note that this app doesn't prompt to ask you if you want to load when you start the app.
- There is another event related to adding exercises you did to a exerciseLog, which can be activated by clicking "Show goal" button. This will show the goal of user(which is set to 1000 minutes by default, and the GUI doesn't allow user to change it at this point), and how far the goal is.

## Event Log

Here is my sample output when the application was closed after following events occurred

- log running of 12 minutes
- click "show goal" button
- click "what should I do?" button
- log swimming of 23 minutes
- log cycling of 2 minutes
- close the application

Wed Nov 30 10:24:57 PST 2022
An exercise was logged: running for 12 minutes.
Wed Nov 30 10:25:06 PST 2022
Viewed distance to goal.
Wed Nov 30 10:25:08 PST 2022
swimming was recommended.
Wed Nov 30 10:25:14 PST 2022
An exercise was logged: swimming for 23 minutes.
Wed Nov 30 10:25:20 PST 2022
An exercise was logged: cycling for 2 minutes.

## Possible improvements

- Separate ExerciseTrackerGUI class into smaller pieces to improve cohesion because this class has multiple responsibilities. For example, this class has buttons, drop-down list, a text field, images, a table, a JFrame and a JPanel. Since each has its own specification and responsibility, it is better to separate these into classes if possible.
- In addition to the above, there are some functionalities that are implemented only in GUI class instead of in the model package. These are some helper methods such as generating arrays for displaying added Xs (ie exercises) in the program, and it is only used once. However, to make my program robust when the model package deals with several kinds of data, I think there should be a separate class that deals with producing such arrays that will be used in GUI.
- SportList in ExerciseLog is essentially a map since it stores the value(time for each sport) corresponding to key(sport). So it might make my code neater if I make use of convenient data structures such as HashMap that I learned after the implementation of Sport class.
- Sometimes, the GUI class combines a few methods from the model package and the ui package to accomplish one task. But this makes it hard for EventLog to operate only within the model package because one call of a method in the model package does not guarantee that it was called for a single task in GUI class. A method in exerciseLog class can be used to do multiple tasks in GUI, which makes it hard to log the Event inside the model package. Thus, I should refactor some code in model and ui package to have a single method in the model package that is responsible for a single task (e.g. show all the Xs) performed in the ui package.

