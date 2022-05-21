package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the event class
public class EventTest {
    private PlannerEvent plannerEvent;

    @BeforeEach
    void runBefore() {
        plannerEvent = new PlannerEvent("name", 1, 1);
    }

    @Test
    void testComplete() {
        plannerEvent.complete();
        assertTrue(plannerEvent.getCompleted());
    }

    @Test
    void testEqualsIsEqual() {
        PlannerEvent second = new PlannerEvent("name", 1, 1);
        assertTrue(plannerEvent.equals(second));
        assertTrue(second.equals(plannerEvent));
    }

    @Test
    void testEqualsDifferentName() {
        PlannerEvent second = new PlannerEvent("second", 1, 1);
        assertFalse(plannerEvent.equals(second));
        assertFalse(second.equals(plannerEvent));
    }

    @Test
    void testEqualsDifferentDay() {
        PlannerEvent second = new PlannerEvent("name", 2, 1);
        assertFalse(plannerEvent.equals(second));
        assertFalse(second.equals(plannerEvent));
    }

    @Test
    void testEqualsDifferentTime() {
        PlannerEvent second = new PlannerEvent("name", 1, 2);
        assertFalse(plannerEvent.equals(second));
        assertFalse(second.equals(plannerEvent));
    }

    @Test
    void testEqualsDifferentCompleted() {
        PlannerEvent second = new PlannerEvent("name", 1, 1);
        second.complete();
        assertFalse(plannerEvent.equals(second));
        assertFalse(second.equals(plannerEvent));
    }

    @Test
    void testToJson() {
        JSONObject json = plannerEvent.toJson();
        assertEquals("name", json.getString("name"));
        assertEquals("1", json.getString("day"));
        assertEquals("1", json.getString("time"));
        assertEquals("false", json.getString("completed"));
    }
}
