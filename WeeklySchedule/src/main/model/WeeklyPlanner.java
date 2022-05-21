package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// WeeklyPlanner with 7 day schedules
public class WeeklyPlanner {
    private ArrayList<DaySchedule> schedule;

    // EFFECTS: creates schedule with 7 days
    public WeeklyPlanner() {
        schedule = new ArrayList<DaySchedule>();
        for (int i = 0; i < 7; i++) {
            DaySchedule day = new DaySchedule();
            schedule.add(day);
        }
    }

    // REQUIRES: 0 <= time < HOURS_IN_PLANNER
    // MODIFIES: this
    // EFFECTS: adds event at set time and day if null
    public void addEvent(int dayIndex, int time, PlannerEvent plannerEvent) {
        schedule.get(dayIndex).addEvent(time, plannerEvent);
    }

    // REQUIRES 0 <= time < HOURS_IN_PLANNER
    // MODIFIES: this
    // EFFECTS: adds event with name at set time and day if null
    public void addEvent(int dayIndex, int time, String eventName) {
        PlannerEvent plannerEvent = new PlannerEvent(eventName, dayIndex, time);
        addEvent(dayIndex, time, plannerEvent);
    }

    // REQUIRES: 0 <= time < HOURS_IN_PLANNER
    // MODIFIES: this
    // EFFECTS: removes event at set time and day
    public void removeEvent(int dayIndex, int time) {
        schedule.get(dayIndex).removeEvent(time);
        Event event = new Event("The event on day " + dayIndex + " and time " + time
                + " was removed from the planner");
        EventLog.getInstance().logEvent(event);
    }

    // MODIFIES: this
    // EFFECTS: changes event on given day to be complete
    public String completeEvent(int dayIndex, String eventName) {
        return schedule.get(dayIndex).completeEvent(eventName);
    }

    // EFFECTS: lists names of all non completed events on given day
    public ArrayList<String> listNonCompletedEvents(int dayIndex) {
        return schedule.get(dayIndex).listNonCompletedEvents();
    }

    // EFFECTS: lists names of all completed events in planner
    public ArrayList<String> listNonCompletedEvents() {
        ArrayList<String> events = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            ArrayList<String> dayEvents = listNonCompletedEvents(i);
            events.addAll(dayEvents);
        }
        return events;
    }

    // EFFECTS: lists names of all completed events on given day
    public ArrayList<String> listCompletedEvents(int dayIndex) {
        return schedule.get(dayIndex).listCompletedEvents();
    }

    // EFFECTS: lists names of all completed events in planner
    public ArrayList<String> listCompletedEvents() {
        ArrayList<String> events = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            ArrayList<String> dayEvents = listCompletedEvents(i);
            events.addAll(dayEvents);
        }
        return events;
    }

    // REQUIRES: 0 <= time < HOURS_IN_PLANNER
    // EFFECTS: produces name of event at given time and day
    public String getEventAtTime(int dayIndex, int time) {
        return schedule.get(dayIndex).getEventAtTime(time);
    }

    // EFFECTS: gets schedule
    public ArrayList<DaySchedule> getSchedule() {
        return schedule;
    }

    // EFFECTS: Produces how many days are in schedule
    public int getSize() {
        return schedule.size();
    }

    // EFFECTS: Returns true is planner has no events
    public boolean isEmpty() {
        for (Integer i = 0;i < 7; i++) {
            if (!schedule.get(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Creates JSONObject out of planner
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("events", eventsToJson());
        return jsonObject;
    }

    // Adds events in planner to JSONArray
    public JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (DaySchedule day: schedule) {
            JSONArray dayEvents = day.toJson();
            for (int i = 0; i < dayEvents.length(); i++) {
                jsonArray.put(dayEvents.getJSONObject(i));
            }
        }
        return jsonArray;
    }

}
