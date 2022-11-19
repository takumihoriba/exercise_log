// Citation: Some code for buttons are from this demo.
// https://docs.oracle.com/javase/tutorial/uiswing/examples/zipfiles/components-ButtonDemoProject.zip

package ui;

import model.Exercise;
import model.ExerciseLog;
import model.Sport;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.ImageIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


// class that builds GUI for ExerciseLog.
public class ExerciseTrackerGUI extends JFrame implements ActionListener {
    private ExerciseLog exerciseLog;
    private JButton saveButton;
    private JButton loadButton;
    private JButton logButton;
    private JButton historyButton;
    private JButton summaryButton;
    private JButton goalButton;

    private JLabel sportLabel;
    private JLabel minutesLabel;
    private static String minutesString = "Time (minutes): ";
    private static String sportString = "Select sport you did: ";

    private JFormattedTextField minutesField;
    private JComboBox sportMenu;

    private JLabel message;
    private JLabel homeDisplay;

    private static final String JSON_STORE = "./data/log.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private ImageIcon imageSave;
    private ImageIcon imageLoad;
    private ImageIcon imageWater;
    private ImageIcon imageBicep;
    private ImageIcon imageHome;

    private JTable historyTable;

    private JFrame frame;
    private JPanel panel;

    private JScrollPane histScroll;
    private JTable histTable2;

    // Constructor
    // EFFECTS: setup exercise log, json writer/reader and JFrame panel. Creates an initial state of GUI by configuring
    // layout of buttons. Set default home image and buttons. Calls helper methods.
    public ExerciseTrackerGUI() {
        exerciseLog = new ExerciseLog();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panel.setLayout(new GridLayout(0, 4));

        setUpImageIcons();

        message = new JLabel("Welcome to Exercise Tracker!");
        homeDisplay = new JLabel(imageHome);

        setUpMinutesAndSportFields();
        setUpButtons();

        addComponentsToPanel();

        panel.setPreferredSize(new Dimension(800,500));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Exercise Tracker");

        frame.pack();
        frame.setVisible(true);

    }

    // REQUIRES: All the components used (homeDisplay, saveButton, ...) are ready to be used.
    // MODIFIES: this
    // EFFECTS: adds components such as buttons, a text field, a table for history, and images to the panel.
    private void addComponentsToPanel() {
        panel.add(homeDisplay);
        panel.add(message);

        panel.add(saveButton);
        panel.add(loadButton);

        panel.add(sportLabel);
        panel.add(sportMenu);

        panel.add(minutesLabel);
        panel.add(minutesField);

        panel.add(goalButton);
        panel.add(summaryButton);

        panel.add(historyButton);
        panel.add(logButton);

        histTable2 = new JTable();
        histScroll = new JScrollPane(histTable2);
        panel.add(histScroll);
    }

    // EFFECTS: creates a String array that contains name of available sports in the exercise log.
    private String[] availableSportsToArray() {
        List<Sport> sportList = exerciseLog.getSportList();
        int size = sportList.size();
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = sportList.get(i).getName();
        }
        return array;
    }

    // MODIFIES: this
    // EFFECTS: creates time text field and sports drop down list.
    private void setUpMinutesAndSportFields() {
        minutesLabel = new JLabel(minutesString);
        sportLabel = new JLabel(sportString);
        minutesField = new JFormattedTextField();
        minutesField.setBounds(10, 50, 80, 25);

        // sets up drop down list
        sportMenu = new JComboBox(availableSportsToArray());
        sportMenu.setSelectedIndex(0);
        sportMenu.addActionListener(this);

    }

    // MODIFIES: this
    // EFFECTS: instantiates ImageIcons with source files.
    private void setUpImageIcons() {
        imageSave = new ImageIcon(getClass().getResource("./images/save_10.png"));
        imageLoad = new ImageIcon(getClass().getResource("./images/load_10.png"));
        imageHome = new ImageIcon(getClass().getResource("./images/home_1_20.png"));
        imageBicep = new ImageIcon(getClass().getResource("./images/bicep_1_20.png"));
        imageWater = new ImageIcon(getClass().getResource("./images/water_1_20.png"));
    }

    // MODIFIES: this
    // EFFECTS: instantiates all the buttons(save, load, history, log) that will be used in GUI.
    private void setUpButtons() {
        saveButton = new JButton("Save to file", imageSave);
//        saveButton.setVerticalTextPosition(AbstractButton.CENTER);
//        saveButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
//        saveButton.setMnemonic(KeyEvent.VK_D);
        saveButton.setActionCommand("save");
        saveButton.setEnabled(false);

        loadButton = new JButton("Load from file", imageLoad);
//        loadButton.setMnemonic(KeyEvent.VK_E);
        loadButton.setActionCommand("load");

        logButton = new JButton("Log your exercise");
        logButton.setActionCommand("log");
        logButton.setForeground(Color.BLUE);

        historyButton = new JButton("Show history");
        historyButton.setActionCommand("history");

        summaryButton = new JButton("Show summary");
        summaryButton.setActionCommand("summary");

        goalButton = new JButton("Show goal");
        goalButton.setActionCommand("goal");

        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        logButton.addActionListener(this);
        historyButton.addActionListener(this);
        summaryButton.addActionListener(this);
        goalButton.addActionListener(this);
    }


    // process a button click
    // EFFECTS: calls helper methods depending on the task this GUI needs to handle. If data was loaded, save button
    // will be available to use.
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if ("save".equals(actionCommand)) {
            saveToFile();
        } else if ("load".equals(actionCommand)) {
            saveButton.setEnabled(true);
            loadFromFile();
        } else if ("log".equals(actionCommand)) {
            logTheExercise();
        } else if ("history".equals(actionCommand)) {
            showHistory();
        } else if ("summary".equals(actionCommand)) {
            showSummary();
        } else if ("goal".equals(actionCommand)) {
            displayGoal();
        }
    }

    // MODIFIES: this
    // EFFECTS: gets goal and distanceToGoal data from the exercise log and display it in dialog.
    private void displayGoal() {
        int goal = exerciseLog.getGoal();
        int distanceToGoal = exerciseLog.distanceToGoal();

        String s = "Goal: " + goal + " minutes. \n" + distanceToGoal + " minutes to goal!";
        JOptionPane.showMessageDialog(panel, s, "Show goal", 1);
    }


    // MODIFIES: this
    // EFFECTS: gets summary data of user from the exercise log and show it in dialog and set the home display to
    // default.
    private void showSummary() {
        String str = "You have done:";
        List<Sport> sportList = exerciseLog.getSportList();
        for (Sport s: sportList) {
            str += "\n" + s.getName() + ": " + s.getTime() + " minutes";
        }
        JOptionPane.showMessageDialog(panel, str, "Your summary (total time per sport)", 1);
        setHomeDisplayAndMessage(imageHome, "Hello. Let's get started.");


    }

    // MODIFIES: this
    // EFFECTS: creates a table for data(all the logs of user), and displays it in pop-up window;
    // displays homeDisplay and message to user; updates table in the main window.
    private void showHistory() {
//        historyPanel.remove(histScroll);

        String[][] data = generateDataArray();
        String[] columnNames = { "Sport", "Time(minutes)"};

        historyTable = new JTable(data, columnNames);
        JOptionPane.showMessageDialog(panel, new JScrollPane(historyTable), "History", 1);

////        historyPanel.add(histScroll);
//
//        historyPanel.add(new JScrollPane(historyTable));
//
////        newFrame.setSize(200,400);
//        newFrame.pack();
//        newFrame.setVisible(true);
//        panel.remove(histTable2);
        panel.remove(histScroll);

        histTable2 = new JTable(data, columnNames);
        histScroll = new JScrollPane(histTable2);
        panel.add(histScroll);
//        panel.add(histTable2);
        setHomeDisplayAndMessage(imageHome, "Hello. Let's get started.");

    }

    // EFFECTS: returns array that contains all the logs of user so that it can be used in showHistory() method.
    private String[][] generateDataArray() {
        int row = exerciseLog.getExercises().size();
        List<Exercise> exercises = exerciseLog.getExercises();
        String[][] array = new String[row][2];
        for (int i = 0; i < row; i++) {
            // sports col
            array[i][0] = exercises.get(i).getActivity();
            // time col
            array[i][1] = Integer.toString(exercises.get(i).getTime());
        }

        return array;
    }

    // MODIFIES: this
    // EFFECTS: get data typed/selected in minutesField and sportMenu when Log button is clicked. If non-integer value
    // or non-positive integer was entered, tells so to user by message and set homeDisplay to default.
    // If data was valid, log it in the exerciseLog, and show some message and change homeDisplay to inform user.
    // Regardless of validity of input in minutesField, clears the input in the field for clarity.
    private void logTheExercise() {
        String sportName = (String) sportMenu.getSelectedItem();
        String minutesInString = minutesField.getText();

        try {
            int minutes = Integer.parseInt(minutesInString);
            if (minutes <= 0) {
                minutesField.setValue("");
                JOptionPane.showMessageDialog(panel, "Time must be positive", "Error", 0);
                setHomeDisplayAndMessage(imageHome, "Time must be positive.");
            } else {
                Exercise ex = new Exercise(minutes, sportName);
                exerciseLog.logExercise(ex);
//                JOptionPane.showMessageDialog(panel, "successfully logged");
//                System.out.println("dev purpose: distance to goal == " + exerciseLog.distanceToGoal());

                minutesField.setValue("");
                saveButton.setEnabled(true);

                // I need to show graph or image or sth here.
                // Maybe depending on time, change message, graphics?
                setHomeDisplayAndMessage(imageWater, "Nice work! Stay hydrated too!");
            }
        } catch (NumberFormatException e) {
            minutesField.setValue("");
            JOptionPane.showMessageDialog(panel, "Time must be an integer", "Error", 0);
            setHomeDisplayAndMessage(imageHome, "Time must be an integer.");
        }
    }

    // MODIFIES: this
    // EFFECTS: set homeDisplay and message to the passed arguments.
    private void setHomeDisplayAndMessage(ImageIcon imageIcon, String msg) {
        message.setText(msg);
        homeDisplay.setIcon(imageIcon);
    }

    // MODIFIES: this
    // EFFECTS: reads data stored in json file to retrieve the state of exerciseLog. After it is loaded, shows some
    // message and change homeDisplay to notify user. If it is not successful, notify user by message.
    private void loadFromFile() {
        try {
            exerciseLog = jsonReader.read();
            System.out.println("Loaded your exercise log from " + JSON_STORE);
            System.out.println(exerciseLog.distanceToGoal());

            setHomeDisplayAndMessage(imageBicep,
                    "<html>Your past progress has been loaded from file. <br/> Let's get started! </html>");
        } catch (IOException e) {
            setHomeDisplayAndMessage(imageHome, "Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: save the state of exerciseLog into file in json format. Inform user if it's successful by message and
    // home display; show message otherwise.
    private void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(exerciseLog);
            jsonWriter.close();
            setHomeDisplayAndMessage(imageHome, "Saved your progress! ");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to the file: " + JSON_STORE);
        }
    }

}
