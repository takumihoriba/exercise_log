package model;

// This is a class that holds time and activity.
public class Exercise {
    private int time;
    private String activity;

    // REQUIRES: time must be a positive integer; activity must be an element of the sports list.
    // EFFECTS: set time and activity of the object to the two arguments respectively.
    public Exercise(int time, String activity) {
        this.time = time;
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }

    public int getTime() {
        return time;
    }

}
