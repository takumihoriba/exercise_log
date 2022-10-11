package ui;

import model.Exercise;
import model.ExerciseLog;

import java.util.Scanner;

// UI for exercise log application.
public class ExerciseTracker {
    private ExerciseLog log;
    private Scanner input;

    // EFFECTS: start the ExerciseLog
    public ExerciseTracker() {
        startExerciseLog();
    }

    // MODIFIES: this
    // EFFECTS: set the flag variable running true; instantiates ExerciseLog and Scanner, and call progress().
    // Ask user for input until exit option is selected.
    public void startExerciseLog() {
        boolean running = true;

        log = new ExerciseLog();
        input = new Scanner(System.in);

        progress();

        while (running) {
            showMenu();
            char userChoice = input.nextLine().toLowerCase().charAt(0);

            if (userChoice == 'e') {
                running = false;
                System.out.println("See you!");
            } else {
                dealWithUserChoice(userChoice);
            }


        }
    }

    // EFFECTS: depending on the provided argument userChoice, calls appropriate methods to meet the user's need.
    // If the character doesn't match the available options, print to tell the user's choice is invalid.
    private void dealWithUserChoice(char userChoice) {
        switch (userChoice) {
            case 'a': record();
                break;
            case 'r': getRec();
                break;
            case 'p': progress();
                break;
            case 'c': changeGoal();
                break;
            default: System.out.println("Your choice is invalid");
        }
    }

    // EFFECTS: asks new goal and call setGoal() to change the current goal.
    private void changeGoal() {
        System.out.print("Enter your new goal (in minutes): ");
        int newGoal = input.nextInt();
        input.nextLine();
        log.setGoal(newGoal);
    }

    // EFFECTS: calls recommend() to get a recommendation and prints out it.
    private void getRec() {
        System.out.println("thinking based on your record....");
        System.out.println("How about " + log.recommend() + " ?");
    }

    // REQUIRES: User inputs valid values, for example, time must be a positive int; activity must be an element of
    // the sports list.
    // EFFECTS: gets inputs for time and activity from user and instantiates Exercise class.
    // If time is not a positive integer, it will say so to the user and doesn't log.
    private void record() {
        System.out.println("what did you do? Pick one from the following: ");
        System.out.println(log.getSports().toString());
        String activity = input.nextLine();
        System.out.print("How many minutes did you do it?: ");
        int time = input.nextInt();
        input.nextLine();
        if (time > 0) {
            Exercise ex = new Exercise(time, activity);
            log.logExercise(ex);
            System.out.println("Your record has been saved successfully");
        } else {
            System.out.println("time must be a positive integer");
        }
    }

    private void showMenu() {
        System.out.println("-------------------------");
        System.out.println("a -> add a new log to your record");
        System.out.println("r -> get a recommendation");
        System.out.println("p -> show progress");
        System.out.println("c -> change your goal");
        System.out.println("e -> exit this application");
        System.out.println("-------------------------");
    }

    public void progress() {
        System.out.println("Your current goal: " + log.getGoal());
        if (log.distanceToGoal() <= 0) {
            System.out.println("You reached your goal. Congrats!");
        } else {
            System.out.println(log.distanceToGoal() + " min to your goal.");
            System.out.println("Available sports:" + log.getSports().toString());
        }

    }
}
