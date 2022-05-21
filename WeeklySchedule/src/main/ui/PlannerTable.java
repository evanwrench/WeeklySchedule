package ui;

import model.DaySchedule;
import model.PlannerEvent;
import model.WeeklyPlanner;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import java.awt.*;

// Visual representation of WeeklyPlanner table
public class PlannerTable extends JTable {

    // EFFECTS: Creates a table with given data and column names
    public PlannerTable(Object [][] data, String []columnNames) {
        super(data, columnNames);
        TableColumn column = null;
        for (int i = 0; i < 7; i++) {
            column = getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }
        setRowHeight(30);
        setOpaque(true);
        setGridColor(Color.BLACK);
        setBorder(new LineBorder(Color.BLACK, 1));
        setDefaultRenderer(Object.class, new CustomCellRenderer());
    }

    // MODIFIES: this
    // EFFECTS: adds event to cell if cell is empty
    public void addEvent(int day, int time, String name) {
        if (getValueAt(time, day).equals("")) {
            setValueAt(name, time, day);
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes event at given cell
    public void removeEvent(int day, int time) {
        setValueAt("", time, day);
    }

    // MODIFIES: this
    // EFFECTS: Completes all events with given name on given day in planner
    public void completeEvent(int day, String name, WeeklyPlanner wp) {
        for (int i = 0; i < DaySchedule.HOURS_IN_PLANNER; i++) {
            PlannerEvent plannerEvent = wp.getSchedule().get(day).getEvents().get(i);
            if (plannerEvent != null && plannerEvent.getName().equals(name)) {
                setValueAt("Completed!", plannerEvent.getTime(), day);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates table so that data loaded from file is seen
    public void loadUpdate(WeeklyPlanner wp) {
        clearTable();
        for (int d = 0; d < 7; d++) {
            for (int t = 0; t < DaySchedule.HOURS_IN_PLANNER; t++) {
                PlannerEvent plannerEvent = wp.getSchedule().get(d).getEvents().get(t);
                if (plannerEvent != null) {
                    addEvent(d, t, plannerEvent.getName());
                    if (plannerEvent.getCompleted()) {
                        completeEvent(plannerEvent.getDay(), plannerEvent.getName(), wp);
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: clears table of all events
    public void clearTable() {
        for (int d = 0; d < 7; d++) {
            for (int t = 0; t < DaySchedule.HOURS_IN_PLANNER; t++) {
                removeEvent(d, t);
            }
        }
    }

}
