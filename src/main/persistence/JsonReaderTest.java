package persistence;

import model.ExerciseLog;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests JsonReader
class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ExerciseLog log = reader.read();
            fail("IOException is expected");
        } catch (IOException e) {
            // good
        }
    }

    @Test
    void testReaderDefault() {
        JsonReader reader = new JsonReader("./data/testWriterDefault.json");
        try {
            ExerciseLog log = reader.read();
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
            fail("IOException is not expected");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testWriterGeneral.json");

        try {
            ExerciseLog log = reader.read();
            assertEquals(100, log.getExercises().get(0).getTime());
            assertEquals("running", log.getExercises().get(0).getActivity());
            assertEquals(20, log.getExercises().get(1).getTime());
            assertEquals("cycling", log.getExercises().get(1).getActivity());
        } catch (IOException e) {
            fail("IOException is not expected");
        }
    }

}
