package persistence;

import model.PlannerEvent;
import model.WeeklyPlanner;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Tests for JsonReader class
// Based on JsonSerializationDemo
public class JSonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WeeklyPlanner wp = new WeeklyPlanner();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("expected IOException");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            WeeklyPlanner wp = new WeeklyPlanner();
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(wp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            wp = reader.read();
            assertTrue(wp.isEmpty());
        } catch (IOException e) {
            fail("IOException was falsely thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WeeklyPlanner wp = new WeeklyPlanner();
            PlannerEvent plannerEvent1 = new PlannerEvent("Family Dinner", 2, 5);
            PlannerEvent event2 = new PlannerEvent("Ski Trip", 4, 3);
            event2.complete();
            wp.addEvent(2, 5, plannerEvent1);
            wp.addEvent(4, 3, event2);
            JsonWriter writer = new JsonWriter("./data/testWriterNonEmpty.json");
            writer.open();
            writer.write(wp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNonEmpty.json");
            wp = reader.read();
            assertFalse(wp.isEmpty());
            assertEquals("Family Dinner", wp.getEventAtTime(2, 5));
            assertEquals("Ski Trip", wp.getEventAtTime(4, 3));

        } catch (IOException e) {
            fail("IOException was falsely thrown");
        }
    }
}
