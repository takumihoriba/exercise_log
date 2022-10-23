package model;

import org.json.JSONObject;
import persistence.Writable;

// Holds name of sport and time spent on that sport
public class Sport implements Writable {
    private String name;
    private int time;

    // REQUIRES: name must be non-empty and different from other names in SportList in ExerciseLog
    // EFFECTS: set name to the given name, set time to 0.
    public Sport(String name) {
        this.name = name;
        this.time = 0;
    }

//    public Sport(String name, int time) {
//        this.name = name;
//        this.time = time;
//    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time", time);
        return json;
    }
}
