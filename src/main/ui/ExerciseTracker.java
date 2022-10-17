package ui;

import model.Exercise;
import model.ExerciseLog;
import model.Sport;

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
            case 's': addSports();
                break;
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

    //EFFECTS: creates a Sport object from user's input and calls addSport() method of ExerciseLog.
    // Tells if the addition was completed or not, show the available sports to user.
    private void addSports() {
        System.out.println("Enter new sport: ");
        String name = input.nextLine();
        Sport sport = new Sport(name);
        boolean success = log.addSport(sport);
        if (success) {
            System.out.println("Successfully added");
        } else {
            System.out.println("Add failed.");
        }
        printAvailableSports();
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
        System.out.println("How about " + log.recommendASport() + " ?");
    }

    // REQUIRES: User inputs valid values, for example, time must be a positive int; activity must be an element of
    // the sports list.
    // EFFECTS: gets inputs for time and activity from user and instantiates Exercise class.
    // If time is not a positive integer, it will say so to the user and doesn't log.
    private void record() {
        System.out.println("what did you do? Pick one from the following: ");
        printAvailableSports();
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

    // EFFECTS: show available options user can choose
    private void showMenu() {
        System.out.println("-------------------------");
        System.out.println("a -> add a new log to your record");
        System.out.println("r -> get a recommendation");
        System.out.println("p -> show progress");
        System.out.println("c -> change your goal");
        System.out.println("s -> add a new sport");
        System.out.println("e -> exit this application");
        System.out.println("-------------------------");
    }

    // EFFECTS: show the current progress of user by showing their goal and the distance to thier goal.
    // Also prints out available sports as a reference
    public void progress() {
        System.out.println("Your current goal: " + log.getGoal());
        if (log.distanceToGoal() <= 0) {
            System.out.println("You reached your goal. Congrats!");
        } else {
            System.out.println(log.distanceToGoal() + " min to your goal.");
            printAvailableSports();
        }
    }

    // EFFECTS: prints out available sports
    public void printAvailableSports() {
        System.out.print("Available sports: ");
        for (Sport s: log.getSportList()) {
            System.out.print(s.getName() + " ");
        }
        System.out.println();
    }
}
