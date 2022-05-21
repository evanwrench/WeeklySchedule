package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class for DaySchedule
// Most methods are tested in WeeklyScheduleTest
public class DayScheduleTest {

    @Test
    void testConstructor() {
        DaySchedule schedule = new DaySchedule();

        assertEquals(DaySchedule.HOURS_IN_PLANNER, schedule.getEvents().size());
    }
}
