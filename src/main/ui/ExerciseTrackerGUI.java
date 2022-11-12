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



public class ExerciseTrackerGUI extends JFrame implements ActionListener {
    private ExerciseLog exerciseLog;
    private JButton saveButton;
    private JButton loadButton;
    private JButton logButton;
    private JButton historyButton;
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


    public ExerciseTrackerGUI() {
        exerciseLog = new ExerciseLog();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 2));

        setUpImageIcons();

        message = new JLabel("Welcome to Exercise Tracker!");
        homeDisplay = new JLabel(imageHome);
        panel.add(homeDisplay);
        panel.add(message);

        setUpTextFields();
        setUpButtons();

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Exercise Tracker");

        frame.pack();
        frame.setVisible(true);
    }


    private void setUpDropdownList() {
        sportMenu = new JComboBox(availableSportsToArray());
        sportMenu.setSelectedIndex(0);
        sportMenu.addActionListener(this);
        panel.add(sportMenu);
    }

//    private String availableSportsToString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (Sport s: exerciseLog.getSportList()) {
//            stringBuilder.append(s.getName() + "  ");
//        }
//        return stringBuilder.toString();
//    }

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

        panel.add(sportLabel);
        setUpDropdownList();

        panel.add(minutesLabel);
        panel.add(minutesField);
    }

    private void setUpImageIcons() {
        imageSave = new ImageIcon(getClass().getResource("./images/save_10.png"));
        imageLoad = new ImageIcon(getClass().getResource("./images/load_10.png"));
        imageHome = new ImageIcon(getClass().getResource("./images/home_1_20.png"));
        imageBicep = new ImageIcon(getClass().getResource("./images/bicep_1_20.png"));
        imageWater = new ImageIcon(getClass().getResource("./images/water_1_20.png"));

    }

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
            logTheExercise();
        } else if ("history".equals(actionCommand)) {
            showHistory();
        }
    }

    private void showHistory() {
        String[][] data = generateDataArray();
        String[] columnNames = { "Sport", "Time(minutes)"};

        historyTable = new JTable(data, columnNames);
        JOptionPane.showMessageDialog(panel, new JScrollPane(historyTable));
//        homeDisplay.setIcon(imageHome);
//        message.setText("Hello. Let's get started.");
        setHomeDisplayAndMessage(imageHome, "Hello. Let's get started.");
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
        String sportName = (String) sportMenu.getSelectedItem();
        String minutesInString = minutesField.getText();

        try {
            int minutes = Integer.parseInt(minutesInString);
            if (minutes <= 0) {
                minutesField.setValue("");
                JOptionPane.showMessageDialog(panel, "Time must be positive");
                setHomeDisplayAndMessage(imageHome, "Time must be positive.");
//                message.setText("Time must be positive.");
//                homeDisplay.setIcon(imageHome);
            } else {
                Exercise ex = new Exercise(minutes, sportName);
                exerciseLog.logExercise(ex);
                JOptionPane.showMessageDialog(panel, "successfully logged");
                System.out.println("dev purpose: distance to goal == " + exerciseLog.distanceToGoal());

                minutesField.setValue("");
                saveButton.setEnabled(true);

                // I need to show graph or image or sth here.
                // Maybe depending on time, change message, graphics?
//                homeDisplay.setIcon(imageWater);
//                message.setText("Nice work! Stay hydrated too!");
                setHomeDisplayAndMessage(imageWater, "Nice work! Stay hydrated too!");
            }
        } catch (NumberFormatException e) {
            minutesField.setValue("");
            JOptionPane.showMessageDialog(panel, "Time must be an integer");
//            message.setText("Time must be an integer.");
//            homeDisplay.setIcon(imageHome);
            setHomeDisplayAndMessage(imageHome, "Time must be an integer.");
        }
    }

    // MODIFIES: this
    // EFFECTS: set homeDisplay and message to the passed arguments.
    private void setHomeDisplayAndMessage(ImageIcon imageIcon, String msg) {
        message.setText(msg);
        homeDisplay.setIcon(imageIcon);
    }

    private void loadFromFile() {
        try {
            exerciseLog = jsonReader.read();
            System.out.println("Loaded your exercise log from " + JSON_STORE);
            System.out.println(exerciseLog.distanceToGoal());

            setHomeDisplayAndMessage(imageBicep,
                    "<html>Your past progress has been loaded from file. <br/> Let's get started! </html>");
//            homeDisplay.setIcon(imageBicep);
//            message.setText("<html>Your past progress has been loaded from file. <br/> Let's get started! </html>");
        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
            setHomeDisplayAndMessage(imageHome, "Unable to read from file: " + JSON_STORE);
//            message.setText("Unable to read from file: " + JSON_STORE);
        }
    }

    private void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(exerciseLog);
            jsonWriter.close();
//            System.out.println("Saved your exercise log to " + JSON_STORE);
//            message.setText("Saved your progress! ");
//            homeDisplay.setIcon(imageHome);
            setHomeDisplayAndMessage(imageHome, "Saved your progress! ");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to the file: " + JSON_STORE);
        }
    }

    public static void main(String[] args) {
        ExerciseTrackerGUI gui = new ExerciseTrackerGUI();
    }
}
