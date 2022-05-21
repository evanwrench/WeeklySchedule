package model;

import org.json.JSONObject;

// Event with name, time, day and whether it is completed
public class PlannerEvent {
    private String name;
    private Boolean completed;
    private Integer day;
    private Integer time;

    // EFFECTS: sets name equal to name of event and whether event is completed to false
    public PlannerEvent(String name, Integer day, Integer time) {
        this.name = name;
        completed = false;
        this.day = day;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getTime() {
        return time;
    }

    public Boolean getCompleted() {
        return completed;
    }

    // MODIFIES: this
    // EFFECTS: Completes task
    public void complete() {
        completed = true;
    }

    // EFFECTS: tests if 2 events are equals
    public boolean equals(PlannerEvent compare) {
        return (name.equals(compare.getName()) && completed == compare.getCompleted()
                && day == compare.getDay() && time == compare.getTime());
    }

    // EFFECTS: turns event into JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("day", Integer.toString(day));
        json.put("time", Integer.toString(time));
        json.put("completed", String.valueOf(completed));
        return json;
    }
}
