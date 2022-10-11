package model;

import java.util.ArrayList;
import java.util.List;

// This is the whole log that contains all the data of the user.
public class ExerciseLog {

    //    private List<Integer> time;
//    private List<String> activity;
    private List<Exercise> exercises;
    private List<String> sports;
    private List<Integer> timeByActivity;
    private int goal;

    // EFFECTS: instantiates time, activity,sports, and timeByActivity lists;
    // Set goal to 1000(min) as default; inputs three sports (running, swimming, cycling) to sports by default.
    public ExerciseLog() {
//        time = new ArrayList<>();
//        activity = new ArrayList<>();
        sports = new ArrayList<>();
        exercises = new ArrayList<>();
        timeByActivity = new ArrayList<>();
        sports.add("running");
        sports.add("swimming");
        sports.add("cycling");
        goal = 1000;
    }

    // MODIFIES: this
    // EFFECTS: add an Exercise object to the list exercises.
    public void logExercise(Exercise ex) {
        this.exercises.add(ex);
    }


    //EFFECTS: returns the sport that the user spend the shortest time on.
    // If multiple sports have the same time, the sport that appears earliest in the sports list will be selected.
    public String recommend() {
        calculateTimeByActivity();
        int min = this.goal;
        int minIdx = 0;
        int size = timeByActivity.size();
        for (int i = 0; i < size; i++) {
            if (timeByActivity.get(i) < min) {
                min = timeByActivity.get(i);
                minIdx = i;
            }
        }
        String sport = sports.get(minIdx);
        return sport;
    }


    //MODIFIES: this
    //EFFECTS: modifies timeByActivity by finding the sum of minutes for each exercise
    public void calculateTimeByActivity() {
        timeByActivity = new ArrayList<>();
        for (String s: sports) {
            timeByActivity.add(0);
        }
        for (int i = 0; i < exercises.size(); i++) {
            String activityAtIth = exercises.get(i).getActivity();
            for (int j = 0; j < sports.size(); j++) {
                String sportAtJth = sports.get(j);
                if (activityAtIth.equals(sportAtJth)) {
                    int updatedTime = timeByActivity.get(j) + exercises.get(i).getTime();
                    timeByActivity.set(j, updatedTime);
                }
            }
        }
    }

    //EFFECTS: returns integer representing the difference between sum of all minutes(user already spent) and goal
    public int distanceToGoal() {
        int sum = 0;
        int size = exercises.size();
        for (int i = 0; i < size; i++) {
            sum += exercises.get(i).getTime();
        }
        return goal - sum;
    }

    //REQUIRES: New goal must be a positive integer
    //MODIFIES: this
    //EFFECTS: Changes goal to a new quantity
    public void setGoal(int newGoal) {
        this.goal = newGoal;
    }

    // below are simple getter methods

    public List<String> getSports() {
        return sports;
    }

    public int getGoal() {
        return this.goal;
    }

    public List<Integer> getTimeByActivity() {
        return timeByActivity;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
