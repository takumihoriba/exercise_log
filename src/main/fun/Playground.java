package fun;

import model.Exercise;
import model.ExerciseLog;
import model.Sport;

import java.util.ArrayList;
import java.util.List;

public class Playground {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("goodbye");
        String[] str = list.toArray(new String[0]);

        System.out.println(list.toArray()[0]);

        ExerciseLog log = new ExerciseLog();
        log.logExercise(new Exercise(10, "running"));
        log.logExercise(new Exercise(7, "swimming"));

        List<Exercise> ex = log.getExercises();
        System.out.println(ex.get(1).getActivity());

        String[][] data = new String[2][2];
        data[1][1] = Integer.toString(ex.get(1).getTime());
        data[0][0] = "2";

        data[0][1] = "4";

        System.out.println(data[1][1]);
        for (int i = 0; i < 2; i++) {
            System.out.println(data[0][i]);
        }

//        data[2][2] = "a";

    }
}
