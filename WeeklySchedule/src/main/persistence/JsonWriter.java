package persistence;

import model.WeeklyPlanner;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Saves Json representation to a fil
// Modeled on JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String location;

    // EFFECTS: constructs new JsonWriter
    public JsonWriter(String location) {
        this.location = location;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if location cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(location));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON data to file
    public void write(WeeklyPlanner wp) {
        JSONObject json = wp.toJson();
        saveFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: saves string to file
    private void saveFile(String json) {
        writer.print(json);
    }
}
