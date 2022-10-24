package persistence;

import model.Exercise;
import model.ExerciseLog;
import model.Sport;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ExerciseLog from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ExerciseLog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseExerciseLog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }
    
    // EFFECTS: parses ExerciseLog from JSON object and returns it
    private ExerciseLog parseExerciseLog(JSONObject jsonObject) {
        int goal = jsonObject.getInt("goal");
        ExerciseLog exerciseLog = new ExerciseLog();
        exerciseLog.setGoal(goal);
        addExercises(exerciseLog, jsonObject);
        addSportList(exerciseLog, jsonObject);
        return exerciseLog;
    }

    // MODIFIES: exerciseLog
    // EFFECTS: extracts each Sport object from JSON object, and call a method to store it in exerciseLog
    private void addSportList(ExerciseLog exerciseLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sportList");
        for (Object json: jsonArray) {
            JSONObject nextSport = (JSONObject) json;
            addOneSport(exerciseLog, nextSport);
        }
    }

    // MODIFIES: exerciseLog
    // EFFECTS: Creates a Sport instance and adds it to the exerciseLog
    private void addOneSport(ExerciseLog exerciseLog, JSONObject jsonObject) {
        int time = jsonObject.getInt("time");
        String name = jsonObject.getString("name");
        Sport sport = new Sport(name);
        sport.setTime(time);
        exerciseLog.addSport(sport);
    }

    private void addExercises(ExerciseLog exerciseLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json: jsonArray) {
            JSONObject nextEx = (JSONObject) json;
            addOneExercise(exerciseLog, nextEx);
        }

    }

    private void addOneExercise(ExerciseLog exerciseLog, JSONObject jsonObject) {
        int time = jsonObject.getInt("time");
        String activity = jsonObject.getString("activity");
        Exercise ex = new Exercise(time, activity);
        exerciseLog.logExercise(ex);
    }

}
