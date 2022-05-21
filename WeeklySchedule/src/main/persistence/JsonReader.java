package persistence;

import model.PlannerEvent;
import model.WeeklyPlanner;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Loads Json files
// Modeled on JsonSerialization
public class JsonReader {
    private String source;

    // EFFECTS: creates a reader that reads source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WeeklyPlanner read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWeeklyPlanner(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses WeeklyPlanner from JSON object and returns it
    private WeeklyPlanner parseWeeklyPlanner(JSONObject jsonObject) {
        WeeklyPlanner wp = new WeeklyPlanner();
        addEvents(wp, jsonObject);
        return wp;
    }

    // MODIFIES: wp
    // EFFECTS: parses events from jsonObject and adds them to wp
    private void addEvents(WeeklyPlanner wp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(wp, nextEvent);
        }
    }

    // MODIFIES: wp
    // EFFECTS: parses event from jsonObject and adds it to wp
    private void addEvent(WeeklyPlanner wp, JSONObject jsonObject) {
        Integer day = Integer.parseInt(jsonObject.getString("day"));
        Integer time = Integer.parseInt(jsonObject.getString("time"));
        Boolean completed = jsonObject.getString("completed").equals("true");
        String name = jsonObject.getString("name");
        PlannerEvent plannerEvent = new PlannerEvent(name, day, time);
        wp.addEvent(day, time, plannerEvent);
        if (completed) {
            wp.completeEvent(day, name);
        }
    }


}
