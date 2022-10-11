package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExerciseLogTest {
    private ExerciseLog exLog;
    private Exercise ex3;
    private Exercise ex2;
    private Exercise ex1;
    private Exercise ex21;
    private Exercise ex22;
    private Exercise ex23;


    @BeforeEach
    public void setup() {
        exLog = new ExerciseLog();
        ex1 = new Exercise(100, "running");
        ex2 = new Exercise(100, "swimming");
        ex3 = new Exercise(100, "cycling");

        ex21 = new Exercise(200, "running");
        ex22 = new Exercise(200, "swimming");
        ex23 = new Exercise(200, "cycling");



    }

    @Test
    void testConstructor() {
//        assertEquals(0, exLog.getTime().size());
//        assertEquals(0, exLog.getActivity().size());
        assertEquals(0, exLog.getExercises().size());
        assertEquals(3, exLog.getSports().size());
        assertEquals(0, exLog.getTimeByActivity().size());
        assertEquals(1000, exLog.getGoal());

        assertEquals("running", exLog.getSports().get(0));
        assertEquals("swimming", exLog.getSports().get(1));
        assertEquals("cycling", exLog.getSports().get(2));

    }

    @Test
    void testLogExerciseSingle() {
//        exLog.logExercise(100, "cycling");
//        assertEquals(100, exLog.getTime().get(0));
//        assertEquals("cycling", exLog.getActivity().get(0));

        exLog.logExercise(ex3);
        assertEquals(100, exLog.getExercises().get(0).getTime());
        assertEquals("cycling", exLog.getExercises().get(0).getActivity());

    }

    @Test
    void testLogExerciseMulti() {
//        exLog.logExercise(100, "cycling");
//        exLog.logExercise(200, "swimming");


        exLog.logExercise(ex3);
        exLog.logExercise(ex22);
        assertEquals(100, exLog.getExercises().get(0).getTime());
        assertEquals("cycling", exLog.getExercises().get(0).getActivity());
        assertEquals(200, exLog.getExercises().get(1).getTime());
        assertEquals("swimming", exLog.getExercises().get(1).getActivity());


//        assertEquals(100, exLog.getTime().get(0));
//        assertEquals("cycling", exLog.getActivity().get(0));
//        assertEquals(200, exLog.getTime().get(1));
//        assertEquals("swimming", exLog.getActivity().get(1));
    }


    @Test
    void testRecommendEmpty() {
        assertEquals("running", exLog.recommend());
    }

    @Test
    void testRecommendAllSame() {
        Exercise ex3 = new Exercise(100, "cycling");
        Exercise ex4 = new Exercise(100, "swimming");
        Exercise ex5 = new Exercise(100, "running");
        exLog.logExercise(ex3);
        exLog.logExercise(ex4);
        exLog.logExercise(ex5);

        assertEquals("running", exLog.recommend());
    }

    @Test
    void testRecommendTwoCandidates1() {
        Exercise ex5 = new Exercise(200, "running");
        Exercise ex4 = new Exercise(100, "swimming");
        Exercise ex3 = new Exercise(100, "cycling");


        exLog.logExercise(ex3);
        exLog.logExercise(ex4);
        exLog.logExercise(ex5);

        assertEquals("swimming", exLog.recommend());

    }

    @Test
    void testRecommendTwoCandidates2() {
        Exercise ex5 = new Exercise(100, "running");
        Exercise ex4 = new Exercise(200, "swimming");
        Exercise ex3 = new Exercise(100, "cycling");

//        exLog.logExercise(100, "running");
//        exLog.logExercise(200, "swimming");
//        exLog.logExercise(100, "cycling");
        exLog.logExercise(ex3);
        exLog.logExercise(ex4);
        exLog.logExercise(ex5);


        assertEquals("running", exLog.recommend());
    }

//    @Test
//    void testRecommendTwoCandidates3() {
//        Exercise ex5 = new Exercise(200, "running");
//        Exercise ex4 = new Exercise(100, "swimming");
//        Exercise ex3 = new Exercise(200, "cycling");
//
//        exLog.logExercise(100, "running");
//        exLog.logExercise(100, "swimming");
//        exLog.logExercise(300, "cycling");
//
//        assertEquals("running", exLog.recommend());
//    }


    @Test
    void testRecommendSingleCandidate() {
        exLog.logExercise(ex3);
        exLog.logExercise(ex22);
        exLog.logExercise(ex23);

//        exLog.logExercise(300, "running");
//        exLog.logExercise(100, "swimming");
//        exLog.logExercise(200, "cycling");

        assertEquals("running", exLog.recommend());
    }

    @Test
    void testCalculateTimeByActivity() {
//        exLog.logExercise(300, "running");
//        exLog.logExercise(100, "swimming");
//        exLog.logExercise(200, "cycling");
//        exLog.logExercise(10, "swimming");
//        exLog.logExercise(50, "cycling");
        exLog.logExercise(ex1);
        exLog.logExercise(ex2);
        exLog.logExercise(ex3);
        exLog.logExercise(ex22);
        exLog.logExercise(ex23);


        exLog.calculateTimeByActivity();
        assertEquals(exLog.getSports().size(), exLog.getTimeByActivity().size());
        assertEquals(100, exLog.getTimeByActivity().get(0)); // running
        assertEquals(300, exLog.getTimeByActivity().get(1)); // swimming
        assertEquals(300, exLog.getTimeByActivity().get(2)); // cycling
    }

    @Test
    void testDistanceToGoalSingle() {
//        exLog.logExercise(300, "running");
        exLog.logExercise(ex2);

        assertEquals(1000-100, exLog.distanceToGoal());
    }

    @Test
    void testDistanceToGoalDifferentGoal() {
        exLog.setGoal(1200);
//        exLog.logExercise(300, "running");
        exLog.logExercise(ex2);
        assertEquals(1200-100, exLog.distanceToGoal());
    }

    @Test
    void testDistanceToGoalMulti() {
//        exLog.logExercise(300, "running");
//        exLog.logExercise(100, "swimming");
//        exLog.logExercise(200, "cycling");
//        exLog.logExercise(10, "swimming");
//        exLog.logExercise(50, "cycling");

        exLog.logExercise(ex1);
        exLog.logExercise(ex22);

        assertEquals(1000-300, exLog.distanceToGoal());
    }


    // setGoal, getSports, getGoal are getters/setter
}