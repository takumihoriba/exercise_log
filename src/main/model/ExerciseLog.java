package model;

import java.util.ArrayList;
import java.util.List;

// This is the whole log that contains all the data of the user.
public class ExerciseLog {

    private List<Exercise> exercises;
    private List<Sport> sportList;
    private int goal;

    // EFFECTS: instantiates exercises and sportList;
    // Set goal to 1000(min) as default; inputs three Sport objects (each of which has name running,
    // swimming and cycling respectively) to sportList by default.
    public ExerciseLog() {
        exercises = new ArrayList<>();
        goal = 1000;
        sportList = new ArrayList<>();
        Sport run = new Sport("running");
        Sport swim = new Sport("swimming");
        Sport cycle = new Sport("cycling");
        sportList.add(run);
        sportList.add(swim);
        sportList.add(cycle);

    }

    // MODIFIES: this
    // EFFECTS: add an Exercise object to the list exercises.
    public void logExercise(Exercise ex) {
        this.exercises.add(ex);
        calculateTimeBySport(ex);
    }

    //EFFECTS: returns the sport that the user spend the shortest time on.
    // If multiple sports have the same time, the sport that appears earliest in the sports list will be selected.
    public String recommendASport() {
        int min = this.goal;
        int minIdx = 0;
        int size = sportList.size();
        for (int i = 0; i < size; i++) {
            if (sportList.get(i).getTime() < min) {
                min = sportList.get(i).getTime();
                minIdx = i;
            }
        }
        String sport = sportList.get(minIdx).getName();
        return sport;
    }

    //MODIFIES: this
    //EFFECTS: add the time that user spent on the sport to the Sport object's time field.
    public void calculateTimeBySport(Exercise ex) {
        for (Sport s: sportList) {
            if (s.getName().equals(ex.getActivity())) {
                int currentTime = s.getTime();
                s.setTime(currentTime + ex.getTime());
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

    // MODIFIES: this
    // EFFECTS: adds a sport if the sport is not already in sportList. Returns true if the sport is added,
    // returns false otherwise.
    public boolean addSport(Sport s) {
        for (Sport sport: sportList) {
            if (sport.getName().equals(s.getName())) {
                return false;
            }
        }
        this.sportList.add(s);
        return true;
    }

    //REQUIRES: New goal must be a positive integer
    //MODIFIES: this
    //EFFECTS: Changes goal to a new quantity
    public void setGoal(int newGoal) {
        this.goal = newGoal;
    }


    public int getGoal() {
        return this.goal;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public List<Sport> getSportList() {
        return sportList;
    }
}
