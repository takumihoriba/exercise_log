package ui.tools;

import java.awt.*;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.text.*;

/**
 * FormattedTextFieldDemo.java requires no other files.
 *
 * It implements a mortgage calculator that uses four
 * JFormattedTextFields.
 */
public class TextField extends JPanel implements PropertyChangeListener {
    //Values for the fields
    private int minutes = 20;
    private int goal = 1000;

    //Labels to identify the fields
    private JLabel minutesLabel;
    private JLabel tillGoalLabel;

    //Strings for the labels
    private static String minutesString = "Minutes exercised: ";
    private static String tillGoalString = "Minutes to goal: ";

    //Fields for data entry
    private JFormattedTextField minutesField;
    private JFormattedTextField tillGoalField;


    public TextField() {
        super(new BorderLayout());
        setUpFormats();
//        double payment = computePayment(amount,
//                rate,
//                minutes);
        int tillGoal = goal;

        //Create the labels.
//        amountLabel = new JLabel(amountString);
//        rateLabel = new JLabel(rateString);
        minutesLabel = new JLabel(minutesString);
//        paymentLabel = new JLabel(paymentString);
        tillGoalLabel = new JLabel(tillGoalString);


        minutesField = new JFormattedTextField();
        minutesField.setValue(new Integer(minutes));
        minutesField.setColumns(10);
        minutesField.addPropertyChangeListener("value", this);

//        paymentField = new JFormattedTextField(paymentFormat);
//        paymentField.setValue(new Double(payment));
//        paymentField.setColumns(10);
//        paymentField.setEditable(false);
//        paymentField.setForeground(Color.red);

        tillGoalField = new JFormattedTextField();
        tillGoalField.setValue(tillGoal);
        tillGoalField.setColumns(10);
        tillGoalField.setEditable(false);
//        tillGoalField.setForeground(Color.GREEN);

        //Tell accessibility tools about label/textfield pairs.
//        amountLabel.setLabelFor(amountField);
//        rateLabel.setLabelFor(rateField);
        minutesLabel.setLabelFor(minutesField);
//        paymentLabel.setLabelFor(paymentField);
        tillGoalLabel.setLabelFor(tillGoalField);

        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(minutesLabel);
        labelPane.add(tillGoalField);

        labelPane.add(minutesLabel);
        labelPane.add(tillGoalLabel);

//
//        //Layout the text fields in a panel.
//        JPanel fieldPane = new JPanel(new GridLayout(0,1));
////        fieldPane.add(amountField);
////        fieldPane.add(rateField);
//        fieldPane.add(minutesField);
////        fieldPane.add(paymentField);
//        fieldPane.add(tillGoalField);
//
//        //Put the panels in this panel, labels on left,
//        //text fields on right.
//        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//        add(labelPane, BorderLayout.CENTER);
//        add(fieldPane, BorderLayout.LINE_END);
    }

    /** Called when a field's "value" property changes. */
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
//        if (source == amountField) {
//            amount = ((Number)amountField.getValue()).doubleValue();
//        } else if (source == rateField) {
//            rate = ((Number)rateField.getValue()).doubleValue();
//        } else
        if (source == minutesField) {
            minutes = ((Number) minutesField.getValue()).intValue();
        }

//        double payment = computePayment(amount, rate, minutes);
//        paymentField.setValue(new Double(payment));
        int toGoal = distanceToGoal(minutes);
        tillGoalField.setValue(toGoal);
    }

    private int distanceToGoal(int minutes) {
        return goal - minutes;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FormattedTextFieldDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new TextField());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }



    //Create and set up number formats. These objects also
    //parse numbers input by user.
    private void setUpFormats() {
//        amountFormat = NumberFormat.getNumberInstance();
//
//        percentFormat = NumberFormat.getNumberInstance();
//        percentFormat.setMinimumFractionDigits(3);
//
//        paymentFormat = NumberFormat.getCurrencyInstance();
    }
}
