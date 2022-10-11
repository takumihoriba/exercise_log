package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExerciseLogTest {
    private ExerciseLog exLog;


    @BeforeEach
    public void setup() {
        exLog = new ExerciseLog();

    }

    @Test
    void testConstructor() {
        assertEquals(0, exLog.getTime().size());
        assertEquals(0, exLog.getActivity().size());
        assertEquals(3, exLog.getSports().size());
        assertEquals(0, exLog.getTimeByActivity().size());
        assertEquals(1000, exLog.getGoal());

        assertEquals("running", exLog.getSports().get(0));
        assertEquals("swimming", exLog.getSports().get(1));
        assertEquals("cycling", exLog.getSports().get(2));

    }

    @Test
    void testLogExerciseSingle() {
        exLog.logExercise(100, "cycling");
        assertEquals(100, exLog.getTime().get(0));
        assertEquals("cycling", exLog.getActivity().get(0));
    }

    @Test
    void testLogExerciseMulti() {
        exLog.logExercise(100, "cycling");
        exLog.logExercise(200, "swimming");

        assertEquals(100, exLog.getTime().get(0));
        assertEquals("cycling", exLog.getActivity().get(0));
        assertEquals(200, exLog.getTime().get(1));
        assertEquals("swimming", exLog.getActivity().get(1));
    }


    @Test
    void testRecommendEmpty() {
        assertEquals("running", exLog.recommend());
    }

    @Test
    void testRecommendAllSame() {
        exLog.logExercise(100, "cycling");
        exLog.logExercise(100, "swimming");
        exLog.logExercise(100, "running");

        assertEquals("running", exLog.recommend());
    }

    @Test
    void testRecommendTwoCandidates1() {
        exLog.logExercise(200, "running");
        exLog.logExercise(100, "swimming");
        exLog.logExercise(100, "cycling");

        assertEquals("swimming", exLog.recommend());

    }

    @Test
    void testRecommendTwoCandidates2() {
        exLog.logExercise(100, "running");
        exLog.logExercise(200, "swimming");
        exLog.logExercise(100, "cycling");

        assertEquals("running", exLog.recommend());
    }

    @Test
    void testRecommendTwoCandidates3() {
        exLog.logExercise(100, "running");
        exLog.logExercise(100, "swimming");
        exLog.logExercise(300, "cycling");

        assertEquals("running", exLog.recommend());
    }


    @Test
    void testRecommendSingleCandidate() {
        exLog.logExercise(300, "running");
        exLog.logExercise(100, "swimming");
        exLog.logExercise(200, "cycling");

        assertEquals("swimming", exLog.recommend());
    }

    @Test
    void testCalculateTimeByActivity() {
        exLog.logExercise(300, "running");
        exLog.logExercise(100, "swimming");
        exLog.logExercise(200, "cycling");
        exLog.logExercise(10, "swimming");
        exLog.logExercise(50, "cycling");
        exLog.calculateTimeByActivity();
        assertEquals(exLog.getSports().size(), exLog.getTimeByActivity().size());
        assertEquals(300, exLog.getTimeByActivity().get(0)); // running
        assertEquals(110, exLog.getTimeByActivity().get(1)); // swimming
        assertEquals(250, exLog.getTimeByActivity().get(2)); // cycling

    }

    @Test
    void testDistanceToGoalSingle() {
        exLog.logExercise(300, "running");

        assertEquals(1000-300, exLog.distanceToGoal());
    }

    @Test
    void testDistanceToGoalDifferentGoal() {
        exLog.setGoal(1200);
        exLog.logExercise(300, "running");

        assertEquals(1200-300, exLog.distanceToGoal());
    }

    @Test
    void testDistanceToGoalMulti() {
        exLog.logExercise(300, "running");
        exLog.logExercise(100, "swimming");
        exLog.logExercise(200, "cycling");
        exLog.logExercise(10, "swimming");
        exLog.logExercise(50, "cycling");

        assertEquals(1000-660, exLog.distanceToGoal());
    }


    // setGoal, getSports, getGoal are getters/setter
}