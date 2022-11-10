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
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ExerciseTrackerGUI implements ActionListener {
//    private ExerciseTracker exTracker;
    private ExerciseLog exerciseLog;
    private JButton saveButton;
    private JButton loadButton;
    private JButton logButton;
    private JButton historyButton;
    private JLabel sportLabel;
    private JLabel minutesLabel;
    private static String minutesString = "Minutes exercised: ";
    private static String sportString = "Sport you did: ";
    private static String availableSportsString = "Available sports: ";

    private JFormattedTextField minutesField;
    private JFormattedTextField sportField;
    private JComboBox sportMenu;

    private JLabel update;
    private JLabel availableSports;

    private static final String JSON_STORE = "./data/log.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private ImageIcon imageSave;
    private ImageIcon imageLoad;

    private JTable historyTable;

//    private JLabel label = new JLabel("A");
    private JFrame frame;
    private JPanel panel;


    public ExerciseTrackerGUI() {
        exerciseLog = new ExerciseLog();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 2));

//        panel.add(new JLabel(availableSportsString));
//        availableSports = new JLabel(availableSportsToString());
//        panel.add(availableSports);

        setUpTextFields();
        setUpButtons();


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tracker");

        update = new JLabel("");
        panel.add(update);

        frame.pack();
        frame.setVisible(true);


    }

    private void setUpDropdownList() {
        sportMenu = new JComboBox(availableSportsToArray());
        sportMenu.setSelectedIndex(0);
        sportMenu.addActionListener(this);
        panel.add(sportMenu);
    }

    private String availableSportsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Sport s: exerciseLog.getSportList()) {
            stringBuilder.append(s.getName() + "  ");
        }
        return stringBuilder.toString();
    }

    private String[] availableSportsToArray() {
        List<Sport> sportList = exerciseLog.getSportList();
        int size = sportList.size();
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = sportList.get(i).getName();
        }
        return array;
    }

    private void setUpTextFields() {
        minutesLabel = new JLabel(minutesString);
        sportLabel = new JLabel(sportString);
        minutesField = new JFormattedTextField();
        minutesField.setBounds(10, 50, 80, 25);
        sportField = new JFormattedTextField();
        // property change listener? maybe as needed

//        minutesLabel.setLabelFor(minutesField);
//        minutesField.setColumns(10);
//        sportLabel.setLabelFor(sportField);

        panel.add(sportLabel);
//        panel.add(sportField);
        setUpDropdownList();

        panel.add(minutesLabel);
        panel.add(minutesField);
    }

    private void setUpButtons() {
//        ImageIcon saveIcon = new ImageIcon("save.png");
        imageSave = new ImageIcon(getClass().getResource("./images/save_10.png"));
//        JLabel imageLabel = new JLabel(image);
//        panel.add(imageLabel);
        imageLoad = new ImageIcon(getClass().getResource("./images/load_10.png"));

        saveButton = new JButton("Save", imageSave);
//        saveButton.setVerticalTextPosition(AbstractButton.CENTER);
//        saveButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
//        saveButton.setMnemonic(KeyEvent.VK_D);
        saveButton.setActionCommand("save");
        saveButton.setEnabled(false);

        loadButton = new JButton("Load", imageLoad);
//        loadButton.setMnemonic(KeyEvent.VK_E);
        loadButton.setActionCommand("load");

        logButton = new JButton("Log");
        logButton.setActionCommand("log");

        historyButton = new JButton("Show history");
        historyButton.setActionCommand("history");

        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        logButton.addActionListener(this);
        historyButton.addActionListener(this);

        panel.add(historyButton);
        panel.add(logButton);

        panel.add(saveButton);
        panel.add(loadButton);
    }


    // process a button click
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if ("save".equals(actionCommand)) {
            saveToFile();
        } else if ("load".equals(actionCommand)) {
            saveButton.setEnabled(true);
            loadFromFile();
        } else if ("log".equals(actionCommand)) {
//            saveButton.setEnabled(true);
            logTheExercise();
        } else if ("history".equals(actionCommand)) {
            showHistory();
        }
    }

    private void showHistory() {
        String[][] data = generateDataArray();

        // Column Names
        String[] columnNames = { "Sport", "Time(minutes)"};

        historyTable = new JTable(data, columnNames);
        JOptionPane.showMessageDialog(panel, new JScrollPane(historyTable));
    }

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

    private void logTheExercise() {
//        String sportName = sportField.getText();
        String sportName = (String) sportMenu.getSelectedItem();
        System.out.println(sportName);
        System.out.println("a");

        String minutesInString = minutesField.getText();

        try {
            int minutes = Integer.parseInt(minutesInString);
            System.out.println(minutes);
            Exercise ex = new Exercise(minutes, sportName);
            exerciseLog.logExercise(ex);
            update.setText("successfully logged");
            System.out.println("dev purpose: distance to goal == " + exerciseLog.distanceToGoal());

            minutesField.setValue("");
            sportField.setValue("");
            saveButton.setEnabled(true);
            // I need to show graph or image or sth here.

        } catch (NumberFormatException e) {
            minutesField.setValue("");
            update.setText("minutes must be an integer");
        }
    }

    private void loadFromFile() {
        try {
            exerciseLog = jsonReader.read();
            System.out.println("Loaded your exercise log from " + JSON_STORE);
            System.out.println(exerciseLog.distanceToGoal());

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(exerciseLog);
            jsonWriter.close();
            System.out.println("Saved your exercise log to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to the file: " + JSON_STORE);
        }
    }

    public static void main(String[] args) {
        ExerciseTrackerGUI gui = new ExerciseTrackerGUI();
    }
}
