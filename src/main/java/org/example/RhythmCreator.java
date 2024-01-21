package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.text.StyleConstants.setIcon;


public class RhythmCreator extends JFrame {
    JTextField chooseYourRhythm;
    JComboBox<String> notesAndRestsComboBox = new JComboBox<>();
    JLabel message = new JLabel(), beatsLeft;
    HashMap<String, String> rhythms = new HashMap<>();
    ArrayList<JLabel> rhythmForUndo = new ArrayList<>();
    JFrame frame = new JFrame();
    JLayeredPane staff = new JLayeredPane();
    int xCoordinateInterval = 73, numOfBarsLeft = 3, halfNoteInterval, quarterNoteInterval, eighthNoteInterval, wholeRestValue, halfRestValue, barNumber = 1;
    double beatsLeftPerBar, beatsInABar;
    String newRhythm = "", valueForHashMap = "";
    String rhythmName = "";
    String rhythmAsAString = "";
    String[] rhythmAsAnArray;
    ImageIcon halfRest = new ImageIcon("src/halfRest.png"), wholeRest = new ImageIcon("src/wholeRest.png"), wholeNote = new ImageIcon("src/wholeNote.png"), halfNote = new ImageIcon("src/halfNote.png"), quarterRest = new ImageIcon("src/quarterRest.png"), quarterNote = new ImageIcon("src/quarterNote.png"), eighthRest = new ImageIcon("src/eighthRest.png"), eighthNoteWithFlag = new ImageIcon("src/eighthNoteWithAFlag.png");
    JLabel eighthNoteWithFlag2, halfNote2, quarterRest2, quarterNote2, eighthRest2, halfRest2, wholeRest2, wholeNote2, notesAndRestsLabel = new JLabel("Notes and Rests:");
    String placingNotesOnStaff;
    JButton save = new JButton("save"), undo = new JButton("undo"), add = new JButton("add"), previousRhythms = new JButton("previous rhythms"), rhythmCreatorButton = new JButton("rhythm creator"), next = new JButton("next");;
    public RhythmCreator() {
        //setting up the jframe---------------------------------
        frame.setSize(900, 650);
        frame.setTitle("Daisy's Ear Training Course");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.white);

        readFromFile(); //sets the keys and values in a hashmap if the user wants to see previous rhythms

        //home button disposes the frame and reopens home screen class
        JButton home = new JButton("home");
        home.setFocusable(false);
        home.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        home.setBackground(new Color(0xcde7ec));
        home.setBounds(700, 100, 100, 60);
        home.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        home.setForeground(new Color(0x4a6267));
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen();
            }
        });

        JLabel title = new JLabel("Rhythm Creator");
        title.setFont(new Font("Rockwell", Font.BOLD, 50));
        title.setBounds(251, 80, 398, 100);
        title.setForeground(new Color(0x006874));

        JLabel timeSignatureLabel = new JLabel("Time Signature:");
        timeSignatureLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        timeSignatureLabel.setBounds(300, 187, 200, 50);
        timeSignatureLabel.setForeground(new Color(0x006874));

        String[] timeSignatureChoices = {"", "2/4", "4/4"}; //string array for the JComboBox with index 0 being "" so the the combobox looks empty
        JComboBox<String> timeSignatures = new JComboBox<>(timeSignatureChoices); //putting the string array into the jcombobox
        timeSignatures.setBackground(Color.white);
        timeSignatures.setBounds(475, 200, 125, 25);

        JButton continueButton = new JButton("continue");
        continueButton.setFocusable(false);
        continueButton.setBounds(400, 400, 100, 60);
        continueButton.setBackground(new Color(0xcde7ec));
        continueButton.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        continueButton.setForeground(new Color(0x4a6267));
        continueButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeSignatures.getSelectedItem() == "") { //they need to have selected a time signature
                    return;
                }

                rhythmCreatorButton.setVisible(true); //setting these buttons and labels visible to true and false in order to create the next display
                notesAndRestsLabel.setVisible(true);
                home.setVisible(false);
                previousRhythms.setVisible(false);
                timeSignatureLabel.setVisible(false);
                timeSignatures.setVisible(false);
                continueButton.setVisible(false);
                title.setVisible(false);
                if (timeSignatures.getSelectedItem() == "2/4") {
                    newRhythm = "2/4"; //adds the time signature to the string. newRhythm variable is where the rhythm is being recorded
                    setStaff("2/4", 0); //goes to setStaff method to set the staff and passes in the time signature, as well as whereIsThisFrom. whereIsThisFrom: 0 = rhythm being created the user, 1 = rhythm retrieved from the txt file
                } else if (timeSignatures.getSelectedItem() == "4/4") {
                    newRhythm = "4/4";
                    setStaff("4/4", 0);
                }
            }
        });

        notesAndRestsLabel.setBounds(280, 200, 166, 30);
        notesAndRestsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        notesAndRestsLabel.setForeground(new Color(0x4a6267));

        //next few things are set visible to false until the user clicks on previous rhythms------------------------------
        JTextArea rhythmList = new JTextArea(); //provides the user with the list of previous rhythms they can view
        rhythmList.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        rhythmList.setBounds(300, 100, 400, 200);
        rhythmList.setEditable(false);
        rhythmList.setVisible(false);

        JLabel typeInYourRhythmNameLabel = new JLabel("Type in the name of the rhythm you want");
        typeInYourRhythmNameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        typeInYourRhythmNameLabel.setVisible(false);
        typeInYourRhythmNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        typeInYourRhythmNameLabel.setBounds(250, 350, 400, 40);

        chooseYourRhythm = new JTextField();  //where the user types in the name of the rhythm they want to view
        chooseYourRhythm.setBounds(250, 400, 400, 30);
        chooseYourRhythm.setVisible(false);
        chooseYourRhythm.setEditable(true);

        previousRhythms.setFocusable(false); //JButton
        previousRhythms.setBounds(75, 400, 150, 60);
        previousRhythms.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        previousRhythms.setBackground(new Color(0xcde7ec));
        previousRhythms.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        previousRhythms.setForeground(new Color(0x4a6267));
        previousRhythms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rhythmList.setVisible(true); //setting buttons and labels to true and false to create the next display
                chooseYourRhythm.setVisible(true);
                typeInYourRhythmNameLabel.setVisible(true);
                rhythmCreatorButton.setVisible(true);
                next.setVisible(false);
                timeSignatureLabel.setVisible(false);
                timeSignatures.setVisible(false);
                continueButton.setVisible(false);
                title.setVisible(false);
                next.setVisible(true);
                String temp = "";
                for (String key : rhythms.keySet()) {  //places all the keys from the hashmap to a string
                    temp += key + "\n";
                }
                rhythmList.setText(temp); //presents the temp string onto the rhythm list text area for the user to see
                previousRhythms.setVisible(false); //set the previousRhythms JButton visible to false so the user cannot click it again
                }
        });

        //this JButton allows the user to return to the "starting page" of the rhythm creator by disposing the frame and then reloading the rhythmCreator class
        rhythmCreatorButton.setBounds(75, 100, 150, 60);
        rhythmCreatorButton.setFocusable(false);
        rhythmCreatorButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        rhythmCreatorButton.setBackground(new Color(0xcde7ec));
        rhythmCreatorButton.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        rhythmCreatorButton.setForeground(new Color(0x4a6267));
        rhythmCreatorButton.setVisible(false);
        rhythmCreatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RhythmCreator();
            }
        });

        //the next JButton is used to view a previously written and saved rhythm
        next.setVisible(false);
        next.setFocusable(false);
        next.setBackground(new Color(0xcde7ec));
        next.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        next.setForeground(new Color(0x4a6267));
        next.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        next.setBounds(700, 400, 100, 60);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                notesAndRestsLabel.setVisible(false); //sets certain labels to false
                save.setVisible(false);
                add.setVisible(false);
                undo.setVisible(false);
                String[] tempRhythmList = rhythmList.getText().split("\n"); //get the text from the rhythmList, splits it by each new line and places it in an array
                for (int i = 0; i < tempRhythmList.length; i++) {
                    if (chooseYourRhythm.getText().equals(tempRhythmList[i])) { //checks if the user typed in the name of a rhythm (case sensitive)
                        home.setVisible(false); //if they do type in the name of a previous rhythm, all the buttons and labels are removed and the process of putting the notes back on the staff begins. If they don't, nothing happens
                        rhythmList.setVisible(false);
                        previousRhythms.setVisible(false);
                        chooseYourRhythm.setVisible(false);
                        typeInYourRhythmNameLabel.setVisible(false);
                        next.setVisible(false);
                        valueForHashMap = rhythms.get(chooseYourRhythm.getText());
                        setStaff(rhythms.get(chooseYourRhythm.getText()).substring(0, 3), 1); //once again, passing in 1 for whereIsThisFrom signifies that this rhythm is from a previous rhythm and not a newly created one
                    }
                }
            }
        });

        frame.add(rhythmCreatorButton);
        frame.add(home);
        frame.add(next);
        frame.add(typeInYourRhythmNameLabel);
        frame.add(chooseYourRhythm);
        frame.add(rhythmList);
        frame.add(previousRhythms);
        frame.add(continueButton);
        frame.add(timeSignatureLabel);
        frame.add(title);
        frame.add(timeSignatures);
        frame.setVisible(true);
    }

    private void setStaff(String timeSignature, int whereIsThisFrom) {
        //first part of the method is quite self-explanatory--------------------------
        ImageIcon singleLineStaff = new ImageIcon("src/singleLineStaff.png");
        JLabel singleLineStaff2 = new JLabel(singleLineStaff);
        singleLineStaff2.setBounds(0, 0, 800, 115);
        staff.add(singleLineStaff2, Integer.valueOf(0));
        staff.setBounds(50, 50, 800, 115);

        //double bar line at the end of the staff
        ImageIcon doubleBarLine = new ImageIcon("src/doubleBarLine.png");
        JLabel doubleBarLine2 = new JLabel(doubleBarLine);
        doubleBarLine2.setBounds(780, 67, 20, 20);
        staff.add(doubleBarLine2, Integer.valueOf(1));

        //3 bar lines to separate the staff into 4 different measures
        ImageIcon barLines = new ImageIcon("src/barLine.png");
        JLabel barLines2 = new JLabel(barLines);
        barLines2.setBounds(248, 67, 1, 20);
        staff.add(barLines2, Integer.valueOf(1));

        JLabel barLines3 = new JLabel(barLines);
        barLines3.setBounds(428, 67, 2, 20);
        staff.add(barLines3, Integer.valueOf(1));

        JLabel barLines4 = new JLabel(barLines);
        barLines4.setBounds(608, 67, 2, 20);
        staff.add(barLines4, Integer.valueOf(1));

        if (timeSignature.equals("2/4")) { //placing the time signature image on the staff using a JLabel
            ImageIcon twoFourTime = new ImageIcon("src/24TimeSignature.png");
            JLabel twoFourTime2 = new JLabel(twoFourTime);
            twoFourTime2.setBounds(32, 37, 41, 80);
            staff.add(twoFourTime2, Integer.valueOf(1));
            beatsInABar = 2; //this variable is used to help reset the beats in a bar later on
            halfNoteInterval = 180; //these intervals are just how many pixels the x-coordinate needs to increase by each time this type of note or rest is added
            quarterNoteInterval = 90;
            eighthNoteInterval = 45;
            wholeRestValue = 2; //the whole rest value changes and does not stay at 4 in this case because you can use whole rest in 2/4 time but not whole note; additionally, you can use a half note in 2/4 time, but not a half rest. Music is complicated :)
            halfRestValue = 4; //half rest value is set at 4 but realistically could be set at any number greater than 2 -- this is just so that the user cannot add a half rest in 2/4 time
        } else if (timeSignature.equals("4/4")) {
            ImageIcon fourFourTime = new ImageIcon("src/44TimeSignature.png");
            JLabel fourFourTime2 = new JLabel(fourFourTime);
            fourFourTime2.setBounds(32, 37, 41, 80);
            staff.add(fourFourTime2, Integer.valueOf(1));
            beatsInABar = 4;
            halfNoteInterval = 90;
            quarterNoteInterval = 45;
            eighthNoteInterval = 22;
            halfRestValue = 2;
            wholeRestValue = 4; //half rest and whole rest value return to their more commonly accepted values (2 and 4 respectively)
        }
        beatsLeftPerBar = beatsInABar; //setting the beats left per bar as the number of beats per bar. This will later be displayed in a JLabel and used by the program to ensure that the user does not add too many beats to a bar

        beatsLeft = new JLabel(); //label that tells user how many beats they have remaining in a bar
        beatsLeft.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        beatsLeft.setBounds(380, 250, 230, 30);
        frame.add(beatsLeft);

        if (whereIsThisFrom == 1) { //whereIsThisFrom integer is used to track where it came from. 1 means it was from a previous rhythm
            if(beatsLeft != null) {
                beatsLeft.setText(""); //since I had trouble setting this JLabel visible to false, I just set the text to an empty string so the JLabel seems invisible
            }
            addForReadingFromFile(); //this method puts the notes from the txt file onto the staff
        }

        String[] notesAndRests = {" ", "eighth note", "eighth rest", "quarter note", "quarter rest", "half note", "half rest", "whole note", "whole rest"}; //string array of possible notes and rests for the JComboBox
        notesAndRestsComboBox = new JComboBox<>(notesAndRests);
        notesAndRestsComboBox.setBackground(Color.white);
        notesAndRestsComboBox.setBounds(475, 200, 125, 25);

        message.setFont(new Font("Times New Roman", Font.PLAIN, 20)); //this message label delivers the important message of "this note cannot be added"
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setBounds(300, 310, 300, 27);
        frame.add(message);

        rhythmCreatorButton.setBounds(200, 400, 150, 60); //changing the bounds of the button so that it is not in the way of the staff. It does the same thing as the other rhythmCreatorButton
        rhythmCreatorButton.setBackground(new Color(0xcde7ec));
        rhythmCreatorButton.setFocusable(false);
        rhythmCreatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RhythmCreator();
            }
        });

        //save button to save your new rhythm
        save.setBounds(580, 400, 100, 60);
        save.setFocusable(false);
        save.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        save.setBackground(new Color(0xcde7ec));
        save.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        save.setForeground(new Color(0x4a6267));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numOfBarsLeft == 0 && beatsLeftPerBar == 0) { //this is to check if the staff has been filled
                    rhythmName = JOptionPane.showInputDialog(frame, "Please name your new rhythm"); //asks for the user to give their rhythm a name
                    if(rhythmName != null) { //if they input something, the system checks that the name is available. Case sensitive
                        for (String temp : rhythms.keySet()) {
                            if (rhythmName.equals(temp)) { //if there is already a rhythm under that name, it will show an error message
                                JOptionPane.showMessageDialog(frame, "There is already a rhythm that exists under that name", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        rewriteRhythms();
                    } //and if they don't input anything, nothing happens
                } else { //if measures are not filled before pressing save
                    JOptionPane.showMessageDialog(frame, "Please fill in all the measures before saving", "saving error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add.setFocusable(false);
        add.setBounds(350, 400, 100, 60);
        add.setBackground(new Color(0xcde7ec));
        add.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        add.setForeground(new Color(0x4a6267));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addForNewRhythm();  //goes to the method that adds the notes to the staff that is specifically for when the user creates their own rhythm
            }
        });

        undo.setFocusable(false);
        undo.setBackground(new Color(0xcde7ec));
        undo.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        undo.setForeground(new Color(0x4a6267));
        undo.setBounds(450, 400, 100, 60);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newRhythm.length() == 3 || newRhythm.equals("2/4") || newRhythm.equals("4/4")) { //ensures that the program will not delete the time signature
                    message.setText("no notes to remove");
                    return;
                }

                String lastNote = "";
                lastNote = newRhythm.substring(newRhythm.length() - 2); //the last note is the last 2 characters of the newRhythm string
                newRhythm = newRhythm.substring(0, newRhythm.length() - 3); //new rhythm string then removes the last 3 characters (2 of which are the actual note/rest, the third character being the space that separates the notes/rests)

                rhythmForUndo.get(rhythmForUndo.size()-1).setVisible(false); //sets the last jlabel visible to false, effectively "removing" the note
                rhythmForUndo.remove(rhythmForUndo.size()-1); //removes the last jlabel from the arraylist

                //switch is used to reset the beatsLeftPerBar and the x-coordinates so that the next note is in the correct place
                switch (lastNote) {
                    case "EN", "ER" -> {
                        beatsLeftPerBar += 0.5;
                        xCoordinateInterval -= eighthNoteInterval;
                        break;
                    }
                    case "QN", "QR" -> {
                        beatsLeftPerBar++;
                        xCoordinateInterval -= quarterNoteInterval;
                        break;
                    }
                    case "HN", "HR" -> {
                        beatsLeftPerBar += 2;
                        xCoordinateInterval -= halfNoteInterval;
                        break;
                    }
                    case "WN" -> {
                        beatsLeftPerBar += 4;
                        xCoordinateInterval -= 180;
                        break;
                    }
                    case "WR" -> {
                        beatsLeftPerBar += wholeRestValue;
                        xCoordinateInterval -= 180;
                        break;
                    }
                }
                //if the program has run out of beats in a certain bar but has other bars to fill out, it resets beats left per bar to the amt of beats per bar and takes away a bar
                if (beatsLeftPerBar == 0 && numOfBarsLeft > 0) {
                    beatsLeftPerBar = beatsInABar;
                    numOfBarsLeft--;
                    barNumber++;
                }

                //if you are adding back beats into a bar, it is possible that a note or rest is held over a bar line. This if statement helps distribute the beats evenly, filling up one bar before attempting to fill up another
                if (beatsLeftPerBar > beatsInABar) {
                    beatsLeftPerBar -= beatsInABar;
                    numOfBarsLeft++;
                    barNumber--;
                }
                beatsLeft.setText("Beats left in bar " + barNumber + ": " + beatsLeftPerBar); //beats left label updates the user
            }
        });

        frame.add(rhythmCreatorButton);
        frame.add(save);
        frame.add(notesAndRestsLabel);
        frame.add(undo);
        frame.add(add);
        frame.add(notesAndRestsComboBox);
        frame.add(staff);
        frame.repaint();
    }

    private void addForNewRhythm() { //the following 2 methods on adding notes to a bar are virtually the same, they just did not work when I put them together
        message.setText(""); //clears the message so that nothing is displayed on it
        if (notesAndRestsComboBox.getSelectedItem() == "eighth note") {
            if (beatsLeftPerBar - 0.5 < 0) { //checks if there is enough beats left in the bar for this note to exist
                message.setText("you cannot add an eighth note"); //message tells them they cannot add an eighth note if there isn't enough room for it and returns
                return;
            }
            newRhythm += " EN"; //this string keeps track of the rhythm as it is being created. The space is added for readability
            eighthNoteWithFlag2 = new JLabel(eighthNoteWithFlag); //next 3 lines add the note onto the staff
            eighthNoteWithFlag2.setBounds(xCoordinateInterval, 41, 22, 40);
            staff.add(eighthNoteWithFlag2, Integer.valueOf(2));
            rhythmForUndo.add(eighthNoteWithFlag2); //label is then added to the rhythmForUndo arraylist in case the user ever wants to delete it
            beatsLeftPerBar -= 0.5; //updates beats left per bar variable
            xCoordinateInterval += eighthNoteInterval; //ensures the next note is properly spaced from this note
        } else if (notesAndRestsComboBox.getSelectedItem() == "eighth rest") {
            if (beatsLeftPerBar - 0.5 < 0) {
                message.setText("you cannot add an eighth rest");
                return;
            }
            newRhythm += " ER";
            eighthRest2 = new JLabel(eighthRest);
            eighthRest2.setBounds(xCoordinateInterval, 73, 9, 13);
            staff.add(eighthRest2, Integer.valueOf(2)); //way too small
            rhythmForUndo.add(eighthRest2);
            beatsLeftPerBar -= 0.5;
            xCoordinateInterval += eighthNoteInterval;
        } else if (notesAndRestsComboBox.getSelectedItem() == "quarter note") {
            if (beatsLeftPerBar - 1 < 0) {
                message.setText("you cannot add a quarter note");
                return;
            }
            newRhythm += " QN";
            quarterNote2 = new JLabel(quarterNote);
            quarterNote2.setBounds(xCoordinateInterval, 41, 15, 40); //114
            staff.add(quarterNote2, Integer.valueOf(2));
            rhythmForUndo.add(quarterNote2);
            beatsLeftPerBar--;
            xCoordinateInterval += quarterNoteInterval;
        } else if (notesAndRestsComboBox.getSelectedItem() == "quarter rest") {
            if (beatsLeftPerBar - 1 < 0) {
                message.setText("you cannot add a quarter rest");
                return;
            }
            newRhythm += " QR";
            quarterRest2 = new JLabel(quarterRest);
            quarterRest2.setBounds(xCoordinateInterval, 54, 13, 40);
            staff.add(quarterRest2, Integer.valueOf(2));
            rhythmForUndo.add(quarterRest2);
            beatsLeftPerBar--;
            xCoordinateInterval += quarterNoteInterval;
        } else if (notesAndRestsComboBox.getSelectedItem().equals("half note")) {
            if (beatsLeftPerBar - 2 < 0) {
                message.setText("you cannot add a half note");
                return;
            }
                newRhythm += " HN";
                halfNote2 = new JLabel(halfNote);
                halfNote2.setBounds(xCoordinateInterval, 41, 15, 40); //155
                staff.add(halfNote2, Integer.valueOf(2));
                rhythmForUndo.add(halfNote2);
                beatsLeftPerBar -= 2;
                xCoordinateInterval += halfNoteInterval;
        } else if (notesAndRestsComboBox.getSelectedItem() == "half rest") {
            if (beatsLeftPerBar - halfRestValue < 0) {  //ideally if this is 2/4 time, this boolean will always be false and therefore you can't add it
                message.setText("you cannot add a half rest");
                return;
            }
            newRhythm += " HR";
            halfRest2 = new JLabel(halfRest);
            halfRest2.setBounds(xCoordinateInterval, 68, 30, 10);
            staff.add(halfRest2, Integer.valueOf(2));
            rhythmForUndo.add(halfRest2);
            beatsLeftPerBar -= 2;
            xCoordinateInterval += halfNoteInterval;
        } else if (notesAndRestsComboBox.getSelectedItem() == "whole note") { //note that this whole note and whole rests will not be available should the time signature be 2/4
            if (beatsLeftPerBar - 4 < 0) {
                message.setText("you cannot add a whole note");
                return;
            }
                newRhythm += " WN";
                wholeNote2 = new JLabel(wholeNote);
                wholeNote2.setBounds(xCoordinateInterval, 71, 15, 10);//196
                staff.add(wholeNote2, Integer.valueOf(2));
                rhythmForUndo.add(wholeNote2);
                beatsLeftPerBar -= 4;
                xCoordinateInterval += 180;
        } else if (notesAndRestsComboBox.getSelectedItem() == "whole rest") {
            if (beatsLeftPerBar - wholeRestValue < 0) {
                message.setText("you cannot add a whole rest");
                return;
            }
            newRhythm += " WR";
            wholeRest2 = new JLabel(wholeRest);
            wholeRest2.setBounds(xCoordinateInterval, 66, 30, 10); //whole rest is supposed to hang off the second line so my y-coordinate was intentional
            staff.add(wholeRest2, Integer.valueOf(2));
            rhythmForUndo.add(wholeRest2);
            beatsLeftPerBar -= wholeRestValue;
            xCoordinateInterval += 180;
        }

        if (barNumber == 4 && beatsLeftPerBar == 0) { //this is a completed rhythm w/ 4 full measures
            beatsLeft.setText("No more beats left!");
        }

        if (beatsLeftPerBar == 0 && numOfBarsLeft > 0) { //this is the same as one of the if statements in the method above. It just resets the beatsLeftPerBar at beatsPerBar if there are still empty bars
            beatsLeftPerBar = beatsInABar;
            numOfBarsLeft--;
            barNumber++;
        }

        beatsLeft.setText("Beats left in bar " + barNumber + ": " + beatsLeftPerBar);

    }

    private void addForReadingFromFile() {
        int startIndex = 4, endingIndex = 6;
        while (startIndex < valueForHashMap.length() ) { //hashmap is created in the readFromFile method
            placingNotesOnStaff = valueForHashMap.substring(startIndex, endingIndex); //placingNotesOnStaff holds the next note that needs to be put onto the staff

            message.setText(""); //ensures no message is on the screen, making it seem that this label is invisible, same goes for beatsLeft label
            if (beatsLeft != null) { //!= null is to avoid any errors and crashing of the program
                beatsLeft.setText("");
            }

            if (placingNotesOnStaff.equals("EN")) { //chain of if statements add the note onto the staff
                eighthNoteWithFlag2 = new JLabel(eighthNoteWithFlag);
                eighthNoteWithFlag2.setBounds(xCoordinateInterval, 41, 22, 40);
                staff.add(eighthNoteWithFlag2, Integer.valueOf(2));
                xCoordinateInterval += eighthNoteInterval; //adds a certain number of pixels so the x-coordinate of the next note is correctly spaced
            } else if (placingNotesOnStaff.equals("ER")) {
                eighthRest2 = new JLabel(eighthRest);
                eighthRest2.setBounds(xCoordinateInterval, 73, 9, 13);
                staff.add(eighthRest2, Integer.valueOf(2));
                xCoordinateInterval += eighthNoteInterval;
            } else if (placingNotesOnStaff.equals("QN")) {
                quarterNote2 = new JLabel(quarterNote);
                quarterNote2.setBounds(xCoordinateInterval, 41, 15, 40);
                staff.add(quarterNote2, Integer.valueOf(2));
                xCoordinateInterval += quarterNoteInterval;
            } else if (placingNotesOnStaff.equals("QR")) {
                quarterRest2 = new JLabel(quarterRest);
                quarterRest2.setBounds(xCoordinateInterval, 54, 13, 40);
                staff.add(quarterRest2, Integer.valueOf(2));
                xCoordinateInterval += quarterNoteInterval;
            } else if (placingNotesOnStaff.equals("HN")) {
                halfNote2 = new JLabel(halfNote);
                halfNote2.setBounds(xCoordinateInterval, 41, 15, 40);
                staff.add(halfNote2, Integer.valueOf(2));
                xCoordinateInterval += halfNoteInterval;
            } else if (placingNotesOnStaff.equals("HR")) {
                halfRest2 = new JLabel(halfRest);
                halfRest2.setBounds(xCoordinateInterval, 68, 30, 10);
                staff.add(halfRest2, Integer.valueOf(2));
                xCoordinateInterval += halfNoteInterval;
            } else if (placingNotesOnStaff.equals("WN")) { //note that this whole note and whole rests will not be available should the time signature be 2/4
                wholeNote2 = new JLabel(wholeNote);
                wholeNote2.setBounds(xCoordinateInterval, 71, 15, 10);
                staff.add(wholeNote2, Integer.valueOf(2));
                xCoordinateInterval += 180;
            } else if (placingNotesOnStaff.equals("WR")) {
                wholeRest2 = new JLabel(wholeRest);
                wholeRest2.setBounds(xCoordinateInterval, 66, 30, 10); //whole rest is supposed to hang off the second line so my y-coordinate was intentional
                staff.add(wholeRest2, Integer.valueOf(2));
                xCoordinateInterval += 180;
            }

            startIndex += 3; //adds 3 so that it gets the next note in the value of the hashmap
            endingIndex += 3;
        }
    }



    private String[] readFromFile () { //reads from the file and stores the info in a hashmap
        /* FILE FORMAT
        Rhythm'sName
        timeSignature*space*Note/rest*space*Note/rest, etc.
         */

            try {
                File rhythmFile = new File("rhythms.txt");
                Scanner ScanRhythmFile = new Scanner(rhythmFile);

                while (ScanRhythmFile.hasNextLine()) { //scans the file then puts into a string separating it by a new line
                    rhythmAsAString += ScanRhythmFile.nextLine() + "\n";
                }

                ScanRhythmFile.close();
            } catch (Exception e) {
                System.out.println("error has occurred");
            }

            rhythmAsAnArray = rhythmAsAString.split("\n"); //splits the string by the new line and puts it in an array

            String tempKeyForHashMap = "", tempValueForHashMapAsAString = "";

            int i = 0, j = 1;
            while (i < rhythmAsAnArray.length && j < rhythmAsAnArray.length) { //odd numbers are the keys, even numbers are the values of the hashmap
                tempKeyForHashMap = rhythmAsAnArray[i]; //creates a hashmap from the rhythmAsAnArray array

                tempValueForHashMapAsAString = rhythmAsAnArray[j];
                rhythms.put(tempKeyForHashMap, tempValueForHashMapAsAString);
                i += 2;
                j += 2;
            }

            return rhythmAsAnArray;
        }

        private void rewriteRhythms () {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("rhythms.txt", false));
                for (int i = 0; i < rhythmAsAnArray.length; i++) {
                    writer.write(rhythmAsAnArray[i] + "\n"); //rewrites the previous rhythms
                }
                writer.write(rhythmName + "\n" + newRhythm + "\n"); //adds new rhythm in the same format
                writer.close();
                JOptionPane.showMessageDialog(frame, "Rhythm is saved", "Saved!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                System.out.println("there is smth wrong w/ your buffered writer. Good luck");
            }
        }
    }
