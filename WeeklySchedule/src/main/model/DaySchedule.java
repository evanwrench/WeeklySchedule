package model;

import org.json.JSONArray;

import java.util.ArrayList;

// Schedule for a single day with HOURS_IN_PLANNER time slots
public class DaySchedule {
    private ArrayList<PlannerEvent> events;
    public static final int HOURS_IN_PLANNER = 10;

    // EFFECTS: Creates day planner with HOURS_IN_PLANNER slots
    public DaySchedule() {
        events = new ArrayList<>(HOURS_IN_PLANNER);
        for (int i = 0; i < HOURS_IN_PLANNER; i++) {
            events.add(null);
        }
    }

    // REQUIRES: 0 <= time < HOURS_IN_PLANNER
    // MODIFIES: this
    // EFFECTS: adds event at set time if null
    public void addEvent(int time, PlannerEvent plannerEvent) {
        if (events.get(time) == null) {
            events.set(time, plannerEvent);
            Event event = new Event(plannerEvent.getName() + " was added to planner");
            EventLog.getInstance().logEvent(event);
        } else {
            System.out.println("Already an event at that time");
        }
    }

    // REQUIRES: 0 <= time < HOURS_IN_PLANNER
    // MODIFIES: this
    // EFFECTS: removes event at set time
    public void removeEvent(int time) {
        events.set(time, null);
    }

    // MODIFIES: this
    // EFFECTS: changes event to be complete if in events
    public String completeEvent(String name) {
        Boolean found = false;
        for (PlannerEvent e: events) {
            if (e != null && e.getName().equals(name)) {
                e.complete();
                found = true;
                Event event = new Event(e.getName() + " was completed");
                EventLog.getInstance().logEvent(event);
            }
        }
        if (found) {
            return "Congratulations, you have completed an event!";
        }
        return "Could not find that event on that day";
    }

    // EFFECTS: lists names of all non completed events
    public ArrayList<String> listNonCompletedEvents() {
        ArrayList<String> uncompletedEvents = new ArrayList();
        for (PlannerEvent e: events) {
            if (e != null && !e.getCompleted()) {
                uncompletedEvents.add(e.getName());
            }
        }
        return uncompletedEvents;
    }

    // EFFECTS: lists names of all completed events
    public ArrayList<String> listCompletedEvents() {
        ArrayList<String> completedEvents = new ArrayList();
        for (PlannerEvent e: events) {
            if (e != null && e.getCompleted()) {
                completedEvents.add(e.getName());
            }
        }
        return completedEvents;
    }

    // REQUIRES: 0 <= time < HOURS_IN_PLANNER
    // EFFECTS: produces name of event at given time
    public String getEventAtTime(int time) {
        if (events.get(time) != null) {
            return events.get(time).getName();
        } else {
            return "No event at that time";
        }
    }

    // EFFECTS: returns true if day has 0 events, false otherwise
    public Boolean isEmpty() {
        for (PlannerEvent e: events) {
            if (e != null) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: gets events
    public ArrayList<PlannerEvent> getEvents() {
        return events;
    }

    // EFFECTS: Changes events in day to JSONArray
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (PlannerEvent e : events) {
            if (e != null) {
                jsonArray.put(e.toJson());
            }
        }
        return jsonArray;
    }
}
