package persistence;

import model.Exercise;
import model.ExerciseLog;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests JsonWriter
public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ExerciseLog log = new ExerciseLog();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterDefaultExerciseLog() {
        try {
            ExerciseLog log = new ExerciseLog();
            JsonWriter writer = new JsonWriter("./data/testWriterDefault.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefault.json");
            log = reader.read();

            assertEquals(0, log.getExercises().size());
            assertEquals(1000, log.getGoal());
            assertEquals(3, log.getSportList().size());
            assertEquals("running", log.getSportList().get(0).getName());
            assertEquals(0, log.getSportList().get(0).getTime());
            assertEquals("swimming", log.getSportList().get(1).getName());
            assertEquals(0, log.getSportList().get(1).getTime());
            assertEquals("cycling", log.getSportList().get(2).getName());
            assertEquals(0, log.getSportList().get(2).getTime());
        } catch (IOException e) {
            fail("Exception is not expected.");
        }
    }

    @Test
    void testWriterGeneral() {
        try {
            ExerciseLog log = new ExerciseLog();
            log.logExercise(new Exercise(100, "running"));
            log.logExercise(new Exercise(20, "cycling"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            log = reader.read();

            assertEquals(100, log.getExercises().get(0).getTime());
            assertEquals("running", log.getExercises().get(0).getActivity());
            assertEquals(20, log.getExercises().get(1).getTime());
            assertEquals("cycling", log.getExercises().get(1).getActivity());

        } catch (IOException e) {
            fail("IOException is not expected");
        }
    }
}
