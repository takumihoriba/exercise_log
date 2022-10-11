package model;

public class Exercise {
    private int time;
    private String activity;

    // REQUIRES: time must be a positive integer; activity must be an element of the sports list.
    public Exercise(int time, String activity) {
        this.time = time;
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
