package persistence;

import model.WeeklyPlanner;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Tests for JsonReader class
// Based on JsonSerializationDemo
public class JSonReaderTest {

    @Test
    void testReaderFakeFile() {
        JsonReader reader = new JsonReader("./data/fakeFile.json");
        try {
            WeeklyPlanner wp = reader.read();
            fail("expected IOException");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyWeeklyPlanner() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            WeeklyPlanner wp = reader.read();
            assertTrue(wp.isEmpty());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }

    @Test
    void testReaderNonEmptyWeeklyPlanner() {
        JsonReader reader = new JsonReader("./data/testReaderNonEmpty.json");
        try {
            WeeklyPlanner wp = reader.read();
            assertTrue(wp.getEventAtTime(1, 2).equals("Math HW"));
            assertTrue(wp.getEventAtTime(4, 6).equals("English Test"));
        } catch (IOException e) {
            fail("File could not be read");
        }
    }
}
