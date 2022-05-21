package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the weekly planner class
// Also tests DaySchedule since WeeklyPlanner methods call DaySchedule methods
public class WeeklyPlannerTest {
    private WeeklyPlanner testPlanner;

    @BeforeEach
    void runBefore() {
        testPlanner = new WeeklyPlanner();
    }

    @Test
    void testConstructor() {
        assertEquals(7, testPlanner.getSize());
        assertEquals(7, testPlanner.getSchedule().size());
        assertTrue(testPlanner.isEmpty());
    }
    @Test
    void testAddEvent() {
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        testPlanner.addEvent(0, 2, plannerEvent);

        assertEquals("Wedding", testPlanner.getEventAtTime(0, 2));
    }

    @Test
    void testAddEventOverlap() {
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        PlannerEvent overlap = new PlannerEvent("Birthday", 0, 2);
        testPlanner.addEvent(0, 2, plannerEvent);
        testPlanner.addEvent(0, 2, overlap);

        assertEquals("Wedding", testPlanner.getEventAtTime(0, 2));
    }

    @Test
    void testAddEventString() {
        testPlanner.addEvent(0, 2, "Wedding");

        assertEquals("Wedding", testPlanner.getEventAtTime(0, 2));
    }

    @Test
    void testAddEventStringOverlap() {
        testPlanner.addEvent(0, 2, "Wedding");
        testPlanner.addEvent(0, 2, "Overlap");

        assertEquals("Wedding", testPlanner.getEventAtTime(0, 2));
    }

    @Test
    void testRemoveEvent() {
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        testPlanner.addEvent(0, 2, plannerEvent);
        testPlanner.removeEvent(0, 2);

        assertEquals("No event at that time", testPlanner.getEventAtTime(0, 2));
    }

    @Test
    void testCompleteEvent() {
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        testPlanner.addEvent(0, 2, plannerEvent);
        assertEquals("Congratulations, you have completed an event!",
                testPlanner.completeEvent(0, "Wedding"));

        assertEquals("Wedding", testPlanner.getEventAtTime(0, 2));
        assertTrue(plannerEvent.getCompleted());
    }

    @Test
    void testCompleteEventNoEvent() {
        assertEquals("Could not find that event on that day",
                testPlanner.completeEvent(0, "Wedding"));
    }

    @Test
    void testListNonCompletedEventsEmpty() {
        assertEquals(0, testPlanner.listNonCompletedEvents(0).size());
    }

    @Test
    void testListNonCompletedEventsOne() {
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        PlannerEvent plannerEvent2 = new PlannerEvent("Birthday", 0, 5);
        testPlanner.addEvent(0, 2, plannerEvent);
        testPlanner.addEvent(0, 5, plannerEvent2);
        testPlanner.completeEvent(0, "Wedding");

        assertEquals(1, testPlanner.listNonCompletedEvents(0).size());
        assertEquals("Birthday", testPlanner.listNonCompletedEvents(0).get(0));
    }

    @Test
    void testListNonCompletedEventsMultiple() {
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        PlannerEvent plannerEvent2 = new PlannerEvent("Birthday", 0, 5);
        PlannerEvent event3 = new PlannerEvent("Homework", 0, 4);
        testPlanner.addEvent(0, 2, plannerEvent);
        testPlanner.addEvent(0, 5, plannerEvent2);
        testPlanner.addEvent(0, 4, event3);
        testPlanner.completeEvent(0, "Wedding");

        assertEquals(2, testPlanner.listNonCompletedEvents(0).size());
        assertEquals("Homework", testPlanner.listNonCompletedEvents(0).get(0));
        assertEquals("Birthday", testPlanner.listNonCompletedEvents(0).get(1));
    }

    @Test
    void testListCompletedEventsEmpty() {
        assertEquals(0, testPlanner.listCompletedEvents(0).size());
    }

    @Test
    void testListCompletedEventsOne() {
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        PlannerEvent plannerEvent2 = new PlannerEvent("Birthday", 0, 5);
        testPlanner.addEvent(0, 2, plannerEvent);
        testPlanner.addEvent(0, 5, plannerEvent2);
        testPlanner.completeEvent(0, "Wedding");

        assertEquals(1, testPlanner.listCompletedEvents(0).size());
        assertEquals("Wedding", testPlanner.listCompletedEvents(0).get(0));
    }

    @Test
    void testListCompletedEventsMultiple() {
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        PlannerEvent plannerEvent2 = new PlannerEvent("Birthday", 0, 5);
        PlannerEvent event3 = new PlannerEvent("Homework", 0, 4);
        testPlanner.addEvent(0, 2, plannerEvent);
        testPlanner.addEvent(0, 5, plannerEvent2);
        testPlanner.addEvent(0, 4, event3);
        testPlanner.completeEvent(0, "Wedding");
        testPlanner.completeEvent(0, "Homework");

        assertEquals(2, testPlanner.listCompletedEvents(0).size());
        assertEquals("Wedding", testPlanner.listCompletedEvents(0).get(0));
        assertEquals("Homework", testPlanner.listCompletedEvents(0).get(1));
    }

    @Test
    void testListAllCompletedEvents(){
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        testPlanner.addEvent(0, 2, plannerEvent);
        PlannerEvent plannerEvent2 = new PlannerEvent("Homework", 3, 5);
        testPlanner.addEvent(3, 5, plannerEvent2);
        testPlanner.completeEvent(0, "Wedding");
        testPlanner.completeEvent(3, "Homework");

        assertEquals(2, testPlanner.listCompletedEvents().size());
        assertEquals(0, testPlanner.listNonCompletedEvents().size());
        assertEquals("Wedding", testPlanner.listCompletedEvents().get(0));
        assertEquals("Homework", testPlanner.listCompletedEvents().get(1));
    }

    @Test
    void testListAllNonCompletedEvents(){
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 0, 2);
        testPlanner.addEvent(0, 2, plannerEvent);
        PlannerEvent plannerEvent2 = new PlannerEvent("Homework", 3, 5);
        testPlanner.addEvent(3, 5, plannerEvent2);

        assertEquals(0, testPlanner.listCompletedEvents().size());
        assertEquals(2, testPlanner.listNonCompletedEvents().size());
        assertEquals("Wedding", testPlanner.listNonCompletedEvents().get(0));
        assertEquals("Homework", testPlanner.listNonCompletedEvents().get(1));
    }

    @Test
    void testGetEventNoEvent() {
        assertEquals("No event at that time", testPlanner.getEventAtTime(1,3));
    }

    @Test
    void testGetEvent() {
        PlannerEvent plannerEvent = new PlannerEvent("Wedding", 1, 5);
        testPlanner.addEvent(1, 5, plannerEvent);

        assertEquals("Wedding", testPlanner.getEventAtTime(1, 5));
    }

    @Test
    void testGetSize() {
        assertEquals(7, testPlanner.getSize());
    }

    @Test
    void testIsEmptyEmpty() {
        assertTrue(testPlanner.isEmpty());
    }

    @Test
    void testIsEmptyNonEmpty() {
        testPlanner.addEvent(0, 0, new PlannerEvent("homework", 0, 0));
        assertFalse(testPlanner.isEmpty());
    }

    @Test
    void testToJson() {
        JSONObject json = testPlanner.toJson();
        assertEquals(0, json.getJSONArray("events").length());
    }
}
