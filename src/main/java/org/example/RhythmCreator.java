package org.example; //COMMENTS COMPLETE, NEED TO DELETE SYSTEM.OUT.PRINTLN
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList; //if you add then remove it does not give back beats?????
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap; //need to somehow get rid of the jcombobox when you use previous rhythms

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.text.StyleConstants.setIcon;


public class RhythmCreator extends JFrame {
    JTextField chooseYourRhythm;
    JComboBox<String> notesAndRestsComboBox = new JComboBox<>();
    JLabel message = new JLabel(), beatsLeft;
    HashMap<String, String> rhythms = new HashMap<>();
    ArrayList<String> newRhythmAsArray = new ArrayList<>();
    ArrayList<JLabel> rhythmForUndo = new ArrayList<>();
    int time = 1;
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

        readFromFile();

        JButton home = new JButton("home");
        home.setFocusable(false);
        home.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        home.setBackground(Color.white);
        home.setBounds(700, 100, 100, 60);
        home.setBackground(new Color(0x97f0ff));
        home.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        home.setForeground(new Color(0x006874));
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

        notesAndRestsLabel.setBounds(280, 200, 166, 30);
        notesAndRestsLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        notesAndRestsLabel.setForeground(new Color(0x006874));

        String[] timeSignatureChoices = {"", "2/4", "4/4"};
        JComboBox<String> timeSignatures = new JComboBox<>(timeSignatureChoices);
        timeSignatures.setBackground(Color.white);
        timeSignatures.setBounds(475, 200, 125, 25);

        JButton continueButton = new JButton("continue");
        continueButton.setFocusable(false);
        continueButton.setBounds(400, 400, 100, 60);
        continueButton.setBackground(new Color(0x97f0ff));
        continueButton.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        continueButton.setForeground(new Color(0x006874));
        continueButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeSignatures.getSelectedItem() == "") {
                    return;
                }

                home.setVisible(false);
                previousRhythms.setVisible(false);
                System.out.println("my mom" + timeSignatures.getSelectedItem());
                notesAndRestsLabel.setVisible(true);
                timeSignatureLabel.setVisible(false);
                timeSignatures.setVisible(false);
                continueButton.setVisible(false);
                title.setVisible(false);
                if (timeSignatures.getSelectedItem() == "2/4") {
                    newRhythm = "2/4";
                    setStaff("2/4", 0);
                } else if (timeSignatures.getSelectedItem() == "4/4") {
                    newRhythm = "4/4";
                    setStaff("4/4", 0);
                }
            }
        });

        //next few things are set visible to false until the user clicks on previous rhythms------------------------------
        JTextArea rhythmList = new JTextArea();
        rhythmList.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        rhythmList.setBounds(300, 100, 400, 200);
        rhythmList.setEditable(false);
        rhythmList.setVisible(false);

        JLabel typeInYourRhythmNameLabel = new JLabel("Type in the name of the rhythm you want");
        typeInYourRhythmNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        typeInYourRhythmNameLabel.setVisible(false);
        typeInYourRhythmNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        typeInYourRhythmNameLabel.setBounds(250, 350, 400, 40);

        chooseYourRhythm = new JTextField();
        chooseYourRhythm.setBounds(250, 400, 400, 30);
        chooseYourRhythm.setVisible(false);
        chooseYourRhythm.setEditable(true);

        previousRhythms.setFocusable(false);
        previousRhythms.setBounds(75, 400, 150, 60);
        previousRhythms.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        previousRhythms.setBackground(new Color(0x97f0ff));
        previousRhythms.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        previousRhythms.setForeground(new Color(0x006874));
        previousRhythms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] rhythmsFromFile = readFromFile(); //figure this out later -- probs gonna need to extract the add button stuff into a method so that I can call it and reuse the code
                next.setVisible(false);
                timeSignatureLabel.setVisible(false);
                timeSignatures.setVisible(false);
                continueButton.setVisible(false);
                title.setVisible(false);
                rhythmList.setVisible(true);
                chooseYourRhythm.setVisible(true);
                typeInYourRhythmNameLabel.setVisible(true);
                next.setVisible(true);
                String temp = "";
                for (String key : rhythms.keySet()) {
                    temp += key + "\n";
                }
                rhythmList.setText(temp);
                previousRhythms.setVisible(false);
                }
        });

        rhythmCreatorButton.setBounds(75, 100, 150, 60);
        rhythmCreatorButton.setBackground(Color.white);
        rhythmCreatorButton.setFocusable(false);
        rhythmCreatorButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
        rhythmCreatorButton.setBackground(new Color(0x97f0ff));
        rhythmCreatorButton.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        rhythmCreatorButton.setForeground(new Color(0x006874));
        rhythmCreatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("BRO");
                frame.dispose();
                new RhythmCreator();
            }
        });


        next.setBackground(Color.white);
        next.setVisible(false);
        next.setFocusable(false);
        next.setBackground(new Color(0x97f0ff));
        next.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        next.setForeground(new Color(0x006874));
        next.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        next.setBounds(700, 400, 100, 60);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                notesAndRestsComboBox.setEnabled(false);
                notesAndRestsComboBox.setVisible(false);


//                if(notesAndRestsComboBox != null) {
//                    System.out.println("MUAY THAI");
//                    notesAndRestsComboBox.setVisible(false); //this does not work at all
//                } else {
//                    System.out.println("boxing gloves");
//                    notesAndRestsComboBox.setVisible(false);
//                }
                home.setVisible(false);
                System.out.println("CHAIR");
                notesAndRestsLabel.setVisible(false);
                save.setVisible(false);
                add.setVisible(false);
                undo.setVisible(false);
                rhythmList.getText();
                String[] tempRhythmList = rhythmList.getText().split("\n");
                for (int i = 0; i < tempRhythmList.length; i++) {
                    System.out.println("kys " + tempRhythmList[i]);
                    if (chooseYourRhythm.getText().equals(tempRhythmList[i])) { //case sensitive
                        System.out.println("happy day working");
                        rhythmList.setVisible(false);
                        previousRhythms.setVisible(false);
                        chooseYourRhythm.setVisible(false);
                        typeInYourRhythmNameLabel.setVisible(false);
                        next.setVisible(false);
                        System.out.println("Yo mama This is what is going into the setStaff method: " + rhythms.get(chooseYourRhythm.getText()).substring(0, 3));
                        valueForHashMap = rhythms.get(chooseYourRhythm.getText());
                        setStaff(rhythms.get(chooseYourRhythm.getText()).substring(0, 3), 1);
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

        if (timeSignature.equals("2/4")) {
            ImageIcon twoFourTime = new ImageIcon("src/24TimeSignature.png");
            JLabel twoFourTime2 = new JLabel(twoFourTime);
            twoFourTime2.setBounds(32, 37, 41, 80);
            staff.add(twoFourTime2, Integer.valueOf(1));
            beatsInABar = 2;
            halfNoteInterval = 180; //these intervals are just how much the x-coordinate needs to increase by each time this type of note or rest is added
            quarterNoteInterval = 90;
            eighthNoteInterval = 45;
            wholeRestValue = 2; //the whole rest value changes and does not stay at 4 because you can use whole rest in 2/4 time but not whole note; ironically, you can use a half note in 2/4 time, but not a half rest. Music is complicated :)
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
        beatsLeftPerBar = beatsInABar;

System.out.println("alarm clock " + whereIsThisFrom);
        if (whereIsThisFrom == 1) { //whereIsThisFrom integer is used to track where it came from... 1 means it was from a previous rhythm
            if(beatsLeft != null) {
                System.out.println("potatoes " + beatsLeft);
                beatsLeft.setText("");
            }
            addForReadingFromFile(); //this method puts the notes from the txt file onto the staff
        }
        System.out.println("god please help me: " + beatsLeftPerBar);
        System.out.println("APPLE SAUCE");

        String[] notesAndRests = {" ", "eighth note", "eighth rest", "quarter note", "quarter rest", "half note", "half rest", "whole note", "whole rest"};
        notesAndRestsComboBox = new JComboBox<>(notesAndRests);
        notesAndRestsComboBox.setBackground(Color.white);
        notesAndRestsComboBox.setBounds(475, 200, 125, 25);

        beatsLeft = new JLabel();
        System.out.println("CHICKEN SCRATCH " + beatsLeft);
        beatsLeft.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        beatsLeft.setBounds(380, 250, 230, 30);
        frame.add(beatsLeft);

        message.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setBounds(300, 310, 300, 27);
        frame.add(message);

        rhythmCreatorButton.setBounds(200, 400, 150, 60);
        rhythmCreatorButton.setBackground(Color.white);
        rhythmCreatorButton.setFocusable(false);
        rhythmCreatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RhythmCreator();
            }
        });
        frame.add(rhythmCreatorButton);

        //save button to save your new rhyth,
        save.setBounds(580, 400, 100, 60);
        save.setFocusable(false);
        save.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        save.setBackground(new Color(0x97f0ff));
        save.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        save.setForeground(new Color(0x006874));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numOfBarsLeft == 0 && beatsLeftPerBar == 0) {
                    rhythmName = JOptionPane.showInputDialog(frame, "Please name your new rhythm"); //asks for the user to give their rhythm a name
                    if(rhythmName != null) { //if they input something the system checks that the name is available. Case sensitive
                        System.out.println("chocolate milk " + rhythmName);
                        for (String temp : rhythms.keySet()) {
                            if (rhythmName.equals(temp)) {
                                JOptionPane.showMessageDialog(frame, "There is already a rhythm that exists under that name", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        rewriteRhythms();
                    } else {
                        System.out.println("waterbottle " + rhythmName);
                    }
                } else { //if measures are not filled before pressing save
                    JOptionPane.showMessageDialog(frame, "Please fill in all the measures before saving", "saving error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add.setFocusable(false);
        add.setBounds(350, 400, 100, 60);
        add.setBackground(new Color(0x97f0ff));
        add.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        add.setForeground(new Color(0x006874));
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addForNewRhythm();
            }
        });

        undo.setFocusable(false);
        undo.setBackground(new Color(0x97f0ff));
        undo.setBorder(BorderFactory.createLineBorder(new Color(0x6f797a), 1));
        undo.setForeground(new Color(0x006874));
        undo.setBounds(450, 400, 100, 60);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newRhythm.length() == 3 || newRhythm.equals("2/4") || newRhythm.equals("4/4")) { //ensures that the program will not delete the time signature
                    message.setText("no notes to remove");
                    return;
                }
                System.out.println("iPhone 18 is shattered this is my rhythm2 variable at the beginning " + newRhythm); //absolutely nothing
                String lastNote = "";

                System.out.println("time for you to get a watch: " + time);
                if (time == 1) { //first time: splits the newRhythm string and puts it in an array. Then the array gets put into an arraylist
                    newRhythm = newRhythm.substring(0, newRhythm.length() - 3);
                    System.out.println("dingle harper " + newRhythm);
                    String[] tempArray = newRhythm.split("\\s+");
                    for (String temp : tempArray) {
                        newRhythmAsArray.add(temp);
                    }
                    lastNote = newRhythm.substring(newRhythm.length() - 2); //last note is taken from the last 2 characters of the newRhythm string
                    System.out.println("Apple in California This is my last note: " + lastNote);
                    time++;
                } else {
                    lastNote = newRhythm.substring(newRhythm.length() - 2);
                    newRhythm = newRhythm.substring(0, newRhythm.length() - 3);
                }
                newRhythmAsArray.remove(lastNote);
                System.out.println("Wildfire new rhythm as an array now: " + newRhythmAsArray);
                System.out.println("qwerty this is my new rhythm2 variable " + newRhythm);
                System.out.println("alarm clock this is my last note variable: \"" + lastNote + "\"");
                System.out.println("light switchrhythmForUndo size: " + (rhythmForUndo.size()-1));
                System.out.println("hand wraps RhythmForUndo array: " + rhythmForUndo.toString());
                System.out.println("reading light "  + rhythmForUndo.get(rhythmForUndo.size()-1));

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
                        System.out.println("HISTORY");
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
                    System.out.println("ughhhhhhhh");
                    beatsLeftPerBar = beatsInABar;
                    numOfBarsLeft--;
                    barNumber++;
                }

                //if you are adding back beats into a bar, it is possible that a note or rest is held over a bar line. This if statement helps distribute the beats evenly, filling up one bar before attempting to fill up another
                if (beatsLeftPerBar > beatsInABar) {
                    beatsLeftPerBar -= beatsInABar;
                    numOfBarsLeft++;
                    barNumber--;
                    System.out.println("window this should be working" + beatsLeftPerBar + ", " + numOfBarsLeft);
                    beatsLeft.setText("Beats left in bar " + barNumber + ": " + beatsLeftPerBar); //beats left label updates the user
                }
                beatsLeft.setText("Beats left in bar " + barNumber + ": " + beatsLeftPerBar);
            }
        });

        frame.add(save);
        frame.add(notesAndRestsLabel);
        frame.add(undo);
        frame.add(add);
        frame.add(notesAndRestsComboBox);
        frame.add(staff);
        frame.repaint();
    }

    private void addForNewRhythm() { //the following 2 methods on adding notes to a bar are virtually the same
        System.out.println("computer placing notes on the staff variable " + placingNotesOnStaff);
        System.out.println("doorframe beatsLeftPerBar " + beatsLeftPerBar + " and bars left: " + numOfBarsLeft);
        System.out.println("receipt xCoordinate: " + xCoordinateInterval);
        message.setText("");
        if (notesAndRestsComboBox.getSelectedItem() == "eighth note") {
            if (beatsLeftPerBar - 0.5 < 0) {
                message.setText("you cannot add an eighth note");
                return;
            }
            newRhythm += " EN"; //this string keeps track of the rhythm as it is being created
            eighthNoteWithFlag2 = new JLabel(eighthNoteWithFlag); //next 3 lines add the note
            eighthNoteWithFlag2.setBounds(xCoordinateInterval, 41, 22, 40);
            staff.add(eighthNoteWithFlag2, Integer.valueOf(2));
            rhythmForUndo.add(eighthNoteWithFlag2); //label is then added to the rhythmForUndo arraylist in case the user ever wants to delete it
            beatsLeftPerBar -= 0.5; //updates beats left per bar variable
            xCoordinateInterval += eighthNoteInterval; //ensures the next note is properly spaced from this note
            System.out.println("TXT beats left per bar " + beatsLeftPerBar);
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
            System.out.println("Blackpink beats left per bar " + beatsLeftPerBar);
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
                System.out.println("Twice beats left per bar " + beatsLeftPerBar);
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
            System.out.println("Izone beats left per bar " + beatsLeftPerBar);
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
                System.out.println("BTS beats left per bar " + beatsLeftPerBar);
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
            System.out.println("GFriend beats left per bar " + beatsLeftPerBar);
        }

            System.out.println("hoodie: " + newRhythm);
            beatsLeft.setText("Beats left in bar " + barNumber + ": " + beatsLeftPerBar);

        if (barNumber == 4 && beatsLeftPerBar == 0) { //this is a completed rhythm w/ 4 full measures
            beatsLeft.setText("No more beats left!");
            System.out.println("desktop new rhythm after no beats left: " + newRhythm);
        }

        System.out.println("plastic beats left per bar: " + beatsLeftPerBar + " and number of bars left: " + numOfBarsLeft);
        if (beatsLeftPerBar == 0 && numOfBarsLeft > 0) { //this is the same as one of the if statements in the method above. It just resets the beatsLeftPerBar at beatsPerBar if there are still empty bars
            beatsLeftPerBar = beatsInABar;
            numOfBarsLeft--;
            barNumber++;
        }

    }

    private void addForReadingFromFile() {
        System.out.println("airvent now it is in the add method for reading from file");
        int startIndex = 4, endingIndex = 6;
        while (startIndex < valueForHashMap.length() ) { //hashmap is created in the readFromFile method
            System.out.println("drawer now it is in the add method INSIDE the while loop");
            placingNotesOnStaff = valueForHashMap.substring(startIndex, endingIndex); //placingNotesOnStaff holds the next note that needs to be put onto the staff
            System.out.println("artist placing notes on the staff variable " + placingNotesOnStaff);
            System.out.println("playback speed beatsLeftPerBar " + beatsLeftPerBar + " and bars left: " + numOfBarsLeft);
            System.out.println("youtube xCoordinate: " + xCoordinateInterval);
            message.setText("");
            if (beatsLeft != null) {
                beatsLeft.setText("");
            }

            if (placingNotesOnStaff.equals("EN")) { //chain of if statements add the note onto the staff
                newRhythm += " EN"; //not necessarily a new rhythm but keeps track of it nonetheless
                eighthNoteWithFlag2 = new JLabel(eighthNoteWithFlag);
                eighthNoteWithFlag2.setBounds(xCoordinateInterval, 41, 22, 40);
                staff.add(eighthNoteWithFlag2, Integer.valueOf(2));
                xCoordinateInterval += eighthNoteInterval; //adds a certain number of pixels so the x-coordinate of the next note is appropriate
                System.out.println("makeup beats left per bar " + beatsLeftPerBar);
            } else if (placingNotesOnStaff.equals("ER")) {
                newRhythm += " ER";
                eighthRest2 = new JLabel(eighthRest);
                eighthRest2.setBounds(xCoordinateInterval, 73, 9, 13);
                staff.add(eighthRest2, Integer.valueOf(2));
                xCoordinateInterval += eighthNoteInterval;
            } else if (placingNotesOnStaff.equals("QN")) {
                newRhythm += " QN";
                quarterNote2 = new JLabel(quarterNote);
                quarterNote2.setBounds(xCoordinateInterval, 41, 15, 40); //114
                staff.add(quarterNote2, Integer.valueOf(2));
                xCoordinateInterval += quarterNoteInterval;
                System.out.println("chicken wings beats left per bar " + beatsLeftPerBar);
            } else if (placingNotesOnStaff.equals("QR")) {
                newRhythm += " QR";
                quarterRest2 = new JLabel(quarterRest);
                quarterRest2.setBounds(xCoordinateInterval, 54, 13, 40);
                staff.add(quarterRest2, Integer.valueOf(2));
                xCoordinateInterval += quarterNoteInterval;
            } else if (placingNotesOnStaff.equals("HN")) {
                newRhythm += " HN";
                halfNote2 = new JLabel(halfNote);
                halfNote2.setBounds(xCoordinateInterval, 41, 15, 40); //155
                staff.add(halfNote2, Integer.valueOf(2));
                xCoordinateInterval += halfNoteInterval;
                System.out.println("apples beats left per bar " + beatsLeftPerBar);
            } else if (placingNotesOnStaff.equals("HR")) {
                newRhythm += " HR";
                halfRest2 = new JLabel(halfRest);
                halfRest2.setBounds(xCoordinateInterval, 68, 30, 10);
                staff.add(halfRest2, Integer.valueOf(2));
                xCoordinateInterval += halfNoteInterval;
                System.out.println("starfruit beats left per bar " + beatsLeftPerBar);
            } else if (placingNotesOnStaff.equals("WN")) { //note that this whole note and whole rests will not be available should the time signature be 2/4
                newRhythm += " WN";
                wholeNote2 = new JLabel(wholeNote);
                wholeNote2.setBounds(xCoordinateInterval, 71, 15, 10);//196
                staff.add(wholeNote2, Integer.valueOf(2));
                xCoordinateInterval += 180;
                System.out.println("pineapple pen beats left per bar " + beatsLeftPerBar);
            } else if (placingNotesOnStaff.equals("WR")) {
                newRhythm += " WR";
                wholeRest2 = new JLabel(wholeRest);
                wholeRest2.setBounds(xCoordinateInterval, 66, 30, 10); //whole rest is supposed to hang off the second line so my y-coordinate was intentional
                staff.add(wholeRest2, Integer.valueOf(2));
                xCoordinateInterval += 180;
                System.out.println("depression beats left per bar " + beatsLeftPerBar);
            }

            System.out.println("anxiety " + newRhythm);
            startIndex += 3; //adds 3 so that it gets the next note in the key of the hashmap
            endingIndex += 3;
        }
    }



    private String[] readFromFile () { //self explanatory again. reads from the file and stores the info in a hashmap
        /* FILE FORMAT
        Rhythm'sName
        timeSignature  *space* Note/rest  *space* Note/rest, etc.
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
            System.out.print(rhythmAsAString);

            System.out.println("ergonomics " + Arrays.toString(rhythmAsAnArray));
            System.out.println("business class " + rhythmAsAnArray.length);

            String tempKeyForHashMap = "", tempValueForHashMapAsAString = "";

            int i = 0, j = 1;
            while (i < rhythmAsAnArray.length && j < rhythmAsAnArray.length) { //odd numbers are the keys, even numbers are the values of the hashmap
                tempKeyForHashMap = rhythmAsAnArray[i];
                System.out.println("LG key to hashmap is: " + tempKeyForHashMap);

                tempValueForHashMapAsAString = rhythmAsAnArray[j];
                System.out.println("Samsung value for hashmap: " + tempValueForHashMapAsAString);
                rhythms.put(tempKeyForHashMap, tempValueForHashMapAsAString);
                i += 2;
                j += 2;
            }

            System.out.println("arrows " + rhythms); //not printing out the elements in the array but rather the array location
            System.out.println("blanket " + rhythms.get("Name Of The Rhythm"));
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
