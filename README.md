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


# Instructions for Grader

- You can generate the first required event related to adding exercises you did to a exerciseLog by clicking the "Log your exercise" button after selecting what you did and typing how long you did.
- You can generate the second required event related to adding exercises you did to a exerciseLog by clicking the "Show history" button to check what exercises you have done and how long you have spent on each instance of exercise.
- You can locate my visual component by looking at buttons that have small icons and left-top corner of GUI. This part will show several images depending on the state of the exercise log. For example, if an exercise is logged, water bottle will be displayed to encourage hydration. If data is loaded from file, it will show a muscle icon to encourage starting exercise now. If logging is unsuccessful, history is shown or exercise log is saved to file, default image will be displayed. This display works together with some text on the right to better inform user about state of this app.  
- You can save the state of my application by clicking the button named "Save to file". Please note that this app doesn't prompt to ask you if you want to save when you try to close the app.
- You can reload the state of my application by clicking the button named "Load from file". Please note that this app doesn't prompt to ask you if you want to load when you start the app.