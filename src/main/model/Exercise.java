package model;

import org.json.JSONObject;
import persistence.Writable;

// This is a class that holds time and activity.
public class Exercise implements Writable {
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

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("time", time);
        json.put("activity", activity);
        return json;
    }
}
