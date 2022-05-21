package ui;

import model.DaySchedule;
import model.Event;
import model.EventLog;
import model.PlannerEvent;
import model.WeeklyPlanner;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Weekly scheduling application
// User input code modeled based on Account project
public class WeeklyScheduleApp extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/weeklyplanner.json";
    private Scanner input;
    private WeeklyPlanner planner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private PlannerTable table;
    private JButton addBtn;
    private JButton rmvBtn;
    private JButton cplBtn;
    private JButton lcpBtn;
    private JButton lncBtn;
    private JButton getBtn;
    private JButton savBtn;
    private JButton loaBtn;
    private JLabel nameText = new JLabel("Name: ");
    private JLabel dayText = new JLabel("Day: ");
    private JLabel timeText = new JLabel("Time: ");
    private JTextField nameField;
    private JTextField dayField;
    private JTextField timeField;
    private JLabel iconLabel;

    // EFFECTS: creates Weekly Schedule Application
    public WeeklyScheduleApp() {
        super("test");
        createWindow();

        setVisible(true);
        setResizable(false);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        planner = new WeeklyPlanner();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Iterator<Event> it = EventLog.getInstance().iterator();
                while (it.hasNext()) {
                    System.out.println(it.next().toString());
                    System.out.println();
                }
                System.exit(0);
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: Creates window with features of the app
    public void createWindow() {
        createWindowFeatures();
        createTableWithLogo();
        createButtons();
        setButtonCommands();
        setActionListeners();
        addButtons();
        setFieldsAndLabels();
    }

    // MODIFIES: this
    // EFFECTS: Sets window features
    public void createWindowFeatures() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(750, 530);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
    }

    // MODIFIES: this
    // EFFECTS: Performs action dictated by button
    @SuppressWarnings("methodlength")
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        if (e.getActionCommand().equals("save")) {
            savePlanner();
        } else if (e.getActionCommand().equals("load")) {
            loadPlanner();
        } else if (e.getActionCommand().equals("list completed")) {
            System.out.println(planner.listCompletedEvents());
        } else if (e.getActionCommand().equals("list non completed")) {
            System.out.println(planner.listNonCompletedEvents());
        } else if (!name.equals("")) {
            if (checkValidDay(dayField.getText())) { //Check valid name
                int day = Integer.parseInt(dayField.getText());
                if (e.getActionCommand().equals("complete")) {
                    planner.completeEvent(day, name);
                    table.completeEvent(day, name, planner);
                } else if (checkValidTime(timeField.getText())) {
                    int time = Integer.parseInt(timeField.getText());
                    if (e.getActionCommand().equals("add")) {
                        planner.addEvent(day, time, name);
                        table.addEvent(day, time, name);
                    } else if (e.getActionCommand().equals("remove")) {
                        planner.removeEvent(day, time);
                        table.removeEvent(day, time);
                    } else if (e.getActionCommand().equals("get")) {
                        System.out.println(planner.getEventAtTime(day, time)); // Something instead of print
                    }
                } else {
                    System.out.println("Sorry, invalid time (must be an integer from 0-"
                              + (DaySchedule.HOURS_IN_PLANNER - 1) + ")");
                }
            } else {
                System.out.println("Sorry, invalid day (must be from 0-6)");
            }
        } else {
            System.out.println("Sorry, invalid name (cannot be empty)");
        }
    }

    //MODIFIES: this
    //EFFECTS: creates table inside pane and logo
    public void createTableWithLogo() {
        iconLabel = new JLabel();
        ImageIcon icon = new ImageIcon("./data/weekly-schedule.jpg");
        Image image = icon.getImage();
        Image resized = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resized);
        iconLabel.setIcon(resizedIcon);
        add(iconLabel);
        table = new PlannerTable(createData(), createNames());
        add(table);
        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(700, 323));
        add(pane);
    }

    //EFFECTS: creates table with no events inside
    public Object[][] createData() {
        int numRow = DaySchedule.HOURS_IN_PLANNER;
        Object[][] slots = new Object[numRow][7];
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < 7; j++) {
                slots[i][j] = "";
            }
        }
        return slots;
    }

    // EFFECTS: Creates names of columns for table
    public String[] createNames() {
        String[] names = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        return names;
    }

    // MODIFIES: this
    // EFFECTS: creates fields and adds them to app
    public void setFieldsAndLabels() {
        nameField = new JTextField(5);
        dayField = new JTextField(5);
        timeField = new JTextField(5);
        add(nameText);
        add(nameField);
        add(dayText);
        add(dayField);
        add(timeText);
        add(timeField);
    }

    // MODIFIES: this
    // EFFECTS: Creates buttons
    public void createButtons() {
        addBtn = new JButton("Add");
        rmvBtn = new JButton("Remove");
        cplBtn = new JButton("Complete");
        lcpBtn = new JButton("List Completed");
        lncBtn = new JButton("List Non Completed");
        getBtn = new JButton("Get");
        savBtn = new JButton("Save");
        loaBtn = new JButton("Load");
    }

    // MODIFIES: this
    // EFFECTS: sets button commands
    public void setButtonCommands() {
        addBtn.setActionCommand("add");
        rmvBtn.setActionCommand("remove");
        cplBtn.setActionCommand("complete");
        lcpBtn.setActionCommand("list completed");
        lncBtn.setActionCommand("list non completed");
        getBtn.setActionCommand("get");
        savBtn.setActionCommand("save");
        loaBtn.setActionCommand("load");
    }

    // MODIFIES: this
    // EFFECTS: sets action listener of buttons to this
    public void setActionListeners() {
        addBtn.addActionListener(this);
        rmvBtn.addActionListener(this);
        cplBtn.addActionListener(this);
        lcpBtn.addActionListener(this);
        lncBtn.addActionListener(this);
        getBtn.addActionListener(this);
        savBtn.addActionListener(this);
        loaBtn.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: Adds buttons to app
    public void addButtons() {
        add(addBtn);
        add(rmvBtn);
        add(cplBtn);
        add(lcpBtn);
        add(lncBtn);
        add(getBtn);
        add(savBtn);
        add(loaBtn);
    }

    // EFFECTS: return true if day is an integer and within 0-6
    public Boolean checkValidDay(String day) {
        if (day == null) {
            return false;
        }
        try {
            Integer.parseInt(day);
        } catch (NumberFormatException e) {
            return false;
        }
        int val = Integer.parseInt(day);
        if (0 <= val && val <= 6) {
            return true;
        }
        return false;
    }

    // EFFECTS: return true if time is an integer and within the number of hours in day, false otherwise
    public Boolean checkValidTime(String time) {
        if (time == null) {
            return false;
        }
        try {
            Integer.parseInt(time);
        } catch (NumberFormatException e) {
            return false;
        }
        int val = Integer.parseInt(time);
        if (0 <= val && val <= DaySchedule.HOURS_IN_PLANNER) {
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: processes input from the user
    private void runSchedule() {
        boolean continueRunning = true;
        String command = null;
        init();

        while (continueRunning) {
            selectionScreen();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                continueRunning = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Thank you for using the program!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addEvent();
        } else if (command.equals("r")) {
            removeEvent();
        } else if (command.equals("c")) {
            completeEvent();
        } else if (command.equals("l")) {
            listCompleted();
        } else if (command.equals("n")) {
            listNonCompleted();
        } else if (command.equals("g")) {
            getEvent();
        } else if (command.equals("1")) {
            savePlanner();
        } else if (command.equals("2")) {
            loadPlanner();
        } else {
            System.out.println("Sorry, not an option");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes planner
    private void init() {
        planner = new WeeklyPlanner();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void selectionScreen() {
        System.out.println("Choose and option:");
        System.out.println("a to add event");
        System.out.println("r to remove event");
        System.out.println("c to complete event");
        System.out.println("l to list completed events");
        System.out.println("n to list non completed events");
        System.out.println("g to get an event");
        System.out.println("1 to save planner");
        System.out.println("2 to load previous planner");
        System.out.println("q to quit");
    }

    private void addEvent() {
        int day = chooseDay();
        System.out.println("Choose when to add, must be less that " + DaySchedule.HOURS_IN_PLANNER + ":");
        int time = input.nextInt();
        System.out.println("Choose name of event:");
        PlannerEvent plannerEvent = new PlannerEvent(input.next(), day, time);
        planner.addEvent(day, time, plannerEvent);
    }

    private void removeEvent() {
        int day = chooseDay();
        System.out.println("Choose when to remove, must be between 0 and " + (DaySchedule.HOURS_IN_PLANNER - 1) + ":");
        int time = input.nextInt();
        planner.removeEvent(day, time);
    }

    private void completeEvent() {
        int day = chooseDay();
        System.out.println("Choose name of event:");
        String name = input.next();
        System.out.println(planner.completeEvent(day, name));
    }

    private void listCompleted() {
        int day = chooseDay();
        System.out.println(planner.listCompletedEvents(day));
    }

    private void listNonCompleted() {
        int day = chooseDay();
        System.out.println(planner.listNonCompletedEvents(day));
    }

    private void getEvent() {
        int day = chooseDay();
        System.out.println("Choose time slot to view, must be less that " + DaySchedule.HOURS_IN_PLANNER + ":");
        int time = input.nextInt();
        System.out.println(planner.getEventAtTime(day, time));
    }

    private int chooseDay() {
        int choice = -1;

        while (choice < 0 || choice > 6) {
            System.out.println("Choose day of the week (0 for Monday, 1 for Tuesday...");
            choice = Integer.parseInt(input.next());
        }

        return choice;

    }

    // EFFECTS: saves the planner to file
    private void savePlanner() {
        try {
            jsonWriter.open();
            jsonWriter.write(planner);
            jsonWriter.close();
            System.out.println("Saved planner to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to given file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads planner from file
    private void loadPlanner() {
        try {
            planner = jsonReader.read();
            System.out.println("Loaded planner from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load from file: " + JSON_STORE);
        }
        table.loadUpdate(planner);
    }
}
