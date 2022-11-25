package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

// Class to test ExerciseLog class
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
        assertEquals(0, exLog.getExercises().size());
        assertEquals(1000, exLog.getGoal());

        assertEquals(3, exLog.getSportList().size());
        assertEquals("running", exLog.getSportList().get(0).getName());
        assertEquals(0, exLog.getSportList().get(0).getTime());
        assertEquals("swimming", exLog.getSportList().get(1).getName());
        assertEquals(0, exLog.getSportList().get(1).getTime());
        assertEquals("cycling", exLog.getSportList().get(2).getName());
        assertEquals(0, exLog.getSportList().get(2).getTime());

    }

    @Test
    void testLogExerciseSingle() {
        exLog.logExercise(ex3);
        assertEquals(100, exLog.getExercises().get(0).getTime());
        assertEquals("cycling", exLog.getExercises().get(0).getActivity());
    }

    @Test
    void testLogExerciseMulti() {
        exLog.logExercise(ex3);
        exLog.logExercise(ex22);
        assertEquals(100, exLog.getExercises().get(0).getTime());
        assertEquals("cycling", exLog.getExercises().get(0).getActivity());
        assertEquals(200, exLog.getExercises().get(1).getTime());
        assertEquals("swimming", exLog.getExercises().get(1).getActivity());

    }

    @Test
    void testRecommendEmpty() {
        assertEquals("running", exLog.recommendASport());
    }

    @Test
    void testRecommendAllSame() {
        exLog.logExercise(ex1);
        exLog.logExercise(ex2);
        exLog.logExercise(ex3);

        assertEquals("running", exLog.recommendASport());
    }

    @Test
    void testRecommendTwoCandidates1() {
        exLog.logExercise(ex3);
        exLog.logExercise(ex2);
        exLog.logExercise(ex21);

        assertEquals("swimming", exLog.recommendASport());
    }

    @Test
    void testRecommendTwoCandidates2() {
        exLog.logExercise(ex1);
        exLog.logExercise(ex22);
        exLog.logExercise(ex3);

        assertEquals("running", exLog.recommendASport());
    }

    @Test
    void testRecommendSingleCandidate() {
        exLog.logExercise(ex3);
        exLog.logExercise(ex22);
        exLog.logExercise(ex23);

        assertEquals("running", exLog.recommendASport());
    }

    @Test
    void testDistanceToGoalSingle() {
        exLog.logExercise(ex2);
        assertEquals(1000-100, exLog.distanceToGoal());
    }

    @Test
    void testDistanceToGoalDifferentGoal() {
        exLog.setGoal(1200);
        exLog.logExercise(ex2);
        assertEquals(1200-100, exLog.distanceToGoal());
    }

    @Test
    void testDistanceToGoalMulti() {
        exLog.logExercise(ex1);
        exLog.logExercise(ex22);

        assertEquals(1000-300, exLog.distanceToGoal());
    }

    @Test
    void testAddSportsSameNameSameTime() {
        Sport s1 = new Sport("running");
        Sport s2 = new Sport("swimming");

        assertFalse(exLog.addSport(s1));
        assertFalse(exLog.addSport(s2));
    }

    @Test
    void testAddSportDifferentTime() {
        Sport s1 = new Sport("running");
        s1.setTime(150);
        assertFalse(exLog.addSport(s1));
    }


    @Test
    void testAddSportDifferent() {
        Sport s1 = new Sport("dead lift");
        Sport s2 = new Sport("dead lift");

        assertTrue(exLog.addSport(s1));
        assertFalse(exLog.addSport(s2));
    }

    @Test
    void testEventLogClear() {
        EventLog.getInstance().clear();
        assertEquals("Event log cleared.", EventLog.getInstance().iterator().next().getDescription());
    }

    @Test
    void testEventLogLogExercise() {
        exLog.logExercise(ex1);
        Iterator<Event> it = EventLog.getInstance().iterator();
        String desc = it.next().getDescription();
        assertEquals( "An exercise was logged: running for 100 minutes.", desc);
    }

    @Test
    void testEventLogDistanceToGoal() {
        exLog.distanceToGoal();
        Iterator<Event> it = EventLog.getInstance().iterator();
        String desc = it.next().getDescription();
        assertEquals( "Viewed distance to goal.", desc);
    }

    @Test
    void testEventLogRecommendASport() {
        exLog.logExercise(ex1);
        exLog.logExercise(ex22);
        exLog.logExercise(ex23);
        exLog.recommendASport();

        Iterator<Event> it = EventLog.getInstance().iterator();
        it.next();
        it.next();
        it.next();
        String desc = it.next().getDescription();
        assertEquals( "running was recommended.", desc);
    }}