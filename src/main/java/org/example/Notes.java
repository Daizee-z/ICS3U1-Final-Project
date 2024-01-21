package org.example;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Notes extends JFrame {
    public static int numOfExercises, correctAnswers, wrongAnswers, numOfTimesGivenUp;
    int noteName, saveNoteName, saveSharpOrFlat;
    Random rand = new Random();
    JLayeredPane staff = new JLayeredPane();
    ImageIcon wholeNoteTC = new ImageIcon("src/wholeNoteTC.png");
    JLabel wholeNoteTC2 = new JLabel(wholeNoteTC), correctMessage = new JLabel("Correct!"), incorrectAnswer, correctAnswer;
    JFrame frame = new JFrame();
    JButton viewScore = new JButton("View Score"), tellMe = new JButton("Tell Me"), nextExercise = new JButton("Next Exercise");
    JButton c = new JButton("C"), cSharp = new JButton("#"), dFlat = new JButton("♭"), d = new JButton("D"), dSharp = new JButton("#"), eFlat = new JButton("♭"), e = new JButton("E"), f = new JButton("F"), fSharp = new JButton("#"), gFlat = new JButton("♭"), g = new JButton("G"), gSharp = new JButton("#"), aFlat = new JButton("♭"), a = new JButton("A"), aSharp = new JButton("#"), bFlat = new JButton("♭"), b = new JButton("B");
    JButton[] notes = {c, cSharp, dFlat, d, dSharp, eFlat, e, f, fSharp, gFlat, g, gSharp, aFlat, a, aSharp, bFlat, b};
    String saveNoteName2, usersAnswer;

     public static File g2 = new File("src/g2.wav"), gSharp2 = new File("src/gSharp2.wav"), a2 = new File("src/a2.wav"), aSharp2 = new File("src/bFlat2.wav"), b2 = new File("src/b2.wav"), c3 = new File("src/c3.wav"), cSharp3 = new File("src/cSharp3.wav"), d3 = new File("src/d3.wav"), dSharp3 = new File("src/eFlat3.wav"), e3 = new File("src/e3.wav"), f3 = new File("src/f3.wav"), fSharp3 = new File("src/fSharp3.wav");
     public static File[] firstOctaveNotes = {g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3};
     public static AudioInputStream[] audioStreamFirstOctave = new AudioInputStream[12];
     public static File g3 = new File("src/g3.wav"), gSharp3 = new File("src/gSharp3.wav"), a3 = new File("src/a3.wav"), aSharp3 = new File("src/aSharp3.wav"), b3 = new File("src/b3.wav"), c4 = new File("src/c4.wav"), cSharp4 = new File("src/cSharp4.wav"), d4 = new File("src/d4.wav"), dSharp4 = new File("src/dSharp4.wav"), e4 = new File("src/e4.wav"), f4 = new File("src/f4.wav"), fSharp4 = new File("src/fSharp4.wav");
     public static File[] secondOctaveNotes = {g3, gSharp3, a3, aSharp3, b3, c4, cSharp4, d4, dSharp4, e4, f4, fSharp4};
     public static AudioInputStream[] audioStreamSecondOctave = new AudioInputStream[12];
     public static File g4 = new File("src/g4.wav"), gSharp4 = new File("src/gSharp4.wav"), a4 = new File("src/a4.wav"), aSharp4 = new File("src/aSharp4.wav"), b4 = new File("src/b4.wav"), c5 = new File("src/c5.wav"), cSharp5 = new File("src/cSharp5.wav"), d5 = new File("src/d5.wav"), dSharp5 = new File("src/dSharp5.wav"), e5 = new File("src/e5.wav"), f5 = new File("src/f5.wav"), fSharp5 = new File("src/fSharp5.wav");
     public static File[] thirdOctaveNotes = {g4, gSharp4, a4, aSharp4, b4, c5, cSharp5, d5, dSharp5, e5, f5, fSharp5};
     public static AudioInputStream[] audioStreamThirdOctave = new AudioInputStream[12];

     public Notes() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        frame.setSize(900, 780);
        frame.setTitle("Daisy's Ear Training Course");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(new JLabel(new ImageIcon("src/background.jpg")));

        /*
        noteNames for the whole program:
        1 = G
        2 = G#/Ab
        3 = A
        4 = A#/Bb
        5 = B
        6 = C
        7 = C#/Db
        8 = D
        9 = D#
        10 = E
        11 = F
        12 = F#/Gb
         */

        //create the buttons
        c.setBounds(80, 413, 90, 78);
        c.setFocusable(false);
        c.setBackground(Color.white);
        c.addActionListener(e -> {
            usersAnswer = "C"; //saves user answer in case they get it wrong
            if (saveNoteName == 6) { //if the user is correct, it goes to correct, otherwise it goes to the wrong method
                correct();
            } else {
                wrong();
            }
        });
        cSharp.setBounds(80, 310, 90, 78);
        cSharp.setFocusable(false);
        cSharp.setBackground(Color.white);
        cSharp.addActionListener(e -> {
            usersAnswer = "C sharp";
            if (saveNoteName == 7 && saveSharpOrFlat == 1) {
                correct();
            } else {
                wrong();
            }
        });
        dFlat.setBounds(190, 516, 90, 78);
        dFlat.setFocusable(false);
        dFlat.setBackground(Color.white);
        dFlat.addActionListener(e -> {
            usersAnswer = "D flat";
            if (saveNoteName == 7 && saveSharpOrFlat == 2) {
                correct();
            } else {
                wrong();
            }
        });
        d.setBounds(190, 413, 90, 78);
        d.setFocusable(false);
        d.setBackground(Color.white);
        d.addActionListener(e -> {
            usersAnswer = "D";
            if (saveNoteName == 8) {
                correct();
            } else {
                wrong();
            }
        });
        dSharp.setBounds(190, 310, 90, 78);
        dSharp.setFocusable(false);
        dSharp.setBackground(Color.white);
        dSharp.addActionListener(e -> {
            usersAnswer = "D sharp";
            if (saveNoteName == 9 && saveSharpOrFlat == 1) {
                correct();
            } else {
                wrong();
            }
        });
        eFlat.setBounds(300, 516, 90, 78);
        eFlat.setFocusable(false);
        eFlat.setBackground(Color.white);
        eFlat.addActionListener(e -> {
            usersAnswer = "E flat";
            if (saveNoteName == 9 && saveSharpOrFlat == 2) {
                correct();
            } else {
                wrong();
            }
        });
        e.setBounds(300, 413, 90, 78);
        e.setFocusable(false);
        e.setBackground(Color.white);
        e.addActionListener(e -> {
            usersAnswer = "E";
            if (saveNoteName == 10) {
                correct();
            } else {
                wrong();
            }
        });
        f.setBounds(410, 413, 90, 78);
        f.setFocusable(false);
        f.setBackground(Color.white);
        f.addActionListener(e -> {
            usersAnswer = "F";
            if (saveNoteName == 11) {
                correct();
            } else {
                wrong();
            }
        });
        fSharp.setBounds(410, 310, 90, 78);
        fSharp.setFocusable(false);
        fSharp.setBackground(Color.white);
        fSharp.addActionListener(e -> {
            usersAnswer = "F sharp";
            if (saveNoteName == 12 && saveSharpOrFlat == 1) {
                correct();
            } else {
                wrong();
            }
        });
        gFlat.setBounds(520, 516, 90, 78);
        gFlat.setFocusable(false);
        gFlat.setBackground(Color.white);
        gFlat.addActionListener(e -> {
            usersAnswer = "G flat";
            if (saveNoteName == 12 && saveSharpOrFlat == 2) {
                correct();
            } else {
                wrong();
            }
        });
        g.setBounds(520, 413, 90, 78);
        g.setFocusable(false);
        g.setBackground(Color.white);
        g.addActionListener(e -> {
            usersAnswer = "G";
            if (saveNoteName == 1) {
                correct();
            } else {
                wrong();
            }
        });
        gSharp.setBounds(520, 310, 90, 78);
        gSharp.setFocusable(false);
        gSharp.setBackground(Color.white);
        gSharp.addActionListener(e -> {
            usersAnswer = "G sharp";
            if (saveNoteName == 2 && saveSharpOrFlat == 1) {
                correct();
            } else {
                wrong();
            }
        });
        aFlat.setBounds(630, 516, 90, 78);
        aFlat.setFocusable(false);
        aFlat.setBackground(Color.white);
        aFlat.addActionListener(e -> {
            usersAnswer = "A flat";
            if (saveNoteName == 2 && saveSharpOrFlat == 2) {
                correct();
            } else {
                wrong();
            }
        });
        a.setBounds(630, 413, 90, 78);
        a.setFocusable(false);
        a.setBackground(Color.white);
        a.addActionListener(e -> {
            usersAnswer = "A";
            if (saveNoteName == 3 ) {
                correct();
            } else {
                wrong();
            }
        });
        aSharp.setBounds(630, 310, 90, 78);
        aSharp.setFocusable(false);
        aSharp.setBackground(Color.white);
        aSharp.addActionListener(e -> {
            usersAnswer = "A sharp";
            if (saveNoteName == 4 && saveSharpOrFlat == 1) {
                correct();
            } else {
                wrong();
            }
        });

        bFlat.setBounds(740, 516, 90, 78);
        bFlat.setFocusable(false);
        bFlat.setBackground(Color.white);
        bFlat.addActionListener(e -> {
            usersAnswer = "B flat";
            if (saveNoteName == 4 && saveSharpOrFlat == 2) {
                correct();
            } else {
                wrong();
            }
        });
        b.setBounds(740, 413, 90, 78);
        b.setFocusable(false);
        b.setBackground(Color.white);
        b.addActionListener(e -> {
            usersAnswer = "B";
            if (saveNoteName == 5) {
                correct();
            } else {
                wrong();
            }
        });

        viewScore.setFocusable(false);
        viewScore.setFont(new Font("Rockwell", Font.PLAIN, 20));
        viewScore.setBounds(75, 630, 350, 60);
        viewScore.setBackground(Color.white);
        viewScore.addActionListener(e -> viewScore());

        tellMe.setFocusable(false);
        tellMe.setFont(new Font("Rockwell", Font.PLAIN, 20));
        tellMe.setBounds(475, 630, 350, 60);
        tellMe.setBackground(Color.white);
        tellMe.addActionListener(e -> tellAnswer());

        frame.add(viewScore);
        frame.add(tellMe);
        frame.add(c);
        frame.add(cSharp);
        frame.add(dFlat);
        frame.add(d);
        frame.add(dSharp);
        frame.add(eFlat);
        frame.add(e);
        frame.add(f);
        frame.add(fSharp);
        frame.add(gFlat);
        frame.add(g);
        frame.add(gSharp);
        frame.add(aFlat);
        frame.add(a);
        frame.add(aSharp);
        frame.add(bFlat);
        frame.add(b);

        frame.setVisible(true);
        setStaff();
    }

     private void tellAnswer() {
         numOfTimesGivenUp++; //adds to the counters so that they can be displayed later on
         numOfExercises++;
         viewScore.setVisible(false); //sets all the irrelevant buttons to false to make space for the answer
         tellMe.setVisible(false);
         correctMessage.setVisible(false);
         for (int i = 0; i < 17; i++) {
             notes[i].setVisible(false);
         }

         String saveNoteName2 = getAnswerAsAString(saveNoteName);

         //displays the answer to the user
         JLabel giveUpCorrectAnswer = new JLabel("Correct Answer: " + saveNoteName2);
         giveUpCorrectAnswer.setFont(new Font("Rockwell", Font.PLAIN, 20));
         giveUpCorrectAnswer.setHorizontalAlignment(SwingConstants.CENTER);
         giveUpCorrectAnswer.setBounds(250, 310, 400, 60);

         frame.add(giveUpCorrectAnswer);
         nextExercise();
     }

     private String getAnswerAsAString(int saveNoteName){
         switch (saveNoteName) { //converts the answer to a string
             case 1:
                 saveNoteName2 = "G";
                 break;
             case 2:
                 if (saveSharpOrFlat == 1) {
                     saveNoteName2 = "G sharp";
                 } else {
                     saveNoteName2 = "A flat";
                 }
                 break;
             case 3:
                 saveNoteName2 = "A";
                 break;
             case 4:
                 if (saveSharpOrFlat == 1) {
                     saveNoteName2 = "A sharp";
                 } else {
                     saveNoteName2 = "B flat";
                 }
                 break;
             case 5:
                 saveNoteName2 = "B";
                 break;
             case 6:
                 saveNoteName2 = "C";
                 break;
             case 7:
                 if (saveSharpOrFlat == 1) {
                     saveNoteName2 = "C sharp";
                 } else {
                     saveNoteName2 = "D flat";
                 }
                 break;
             case 8:
                 saveNoteName2 = "D";
                 break;
             case 9:
                 if (saveSharpOrFlat == 1) {
                     saveNoteName2 = "D sharp";
                 } else {
                     saveNoteName2 = "E flat";
                 }
                 break;
             case 10:
                 saveNoteName2 = "E";
                 break;
             case 11:
                 saveNoteName2 = "F";
                 break;
             case 12:
                 if (saveSharpOrFlat == 1) {
                     saveNoteName2 = "F sharp";
                 } else {
                     saveNoteName2 = "G flat";
                 }
                 break;
         }
         return saveNoteName2;
     }
     private void viewScore() {
        staff.setVisible(false); //makes all the irrelevant items invisible
        viewScore.setVisible(false);
        tellMe.setVisible(false);
        nextExercise.setVisible(false);
        correctMessage.setVisible(false);
        if (incorrectAnswer != null) {
            incorrectAnswer.setVisible(false);
        }
        if (correctAnswer != null) {
            correctAnswer.setVisible(false);
        }
        for (int i = 0; i < 17; i++) {
            notes[i].setVisible(false);
        }

        //gives the user their score
        JLabel exercises = new JLabel("Number of Exercises today: " + numOfExercises);
        exercises.setFont(new Font("Rockwell", Font.PLAIN, 30));
        exercises.setHorizontalAlignment(SwingConstants.CENTER);
        exercises.setBounds(150, 50, 600, 75);

        JLabel rightAnswers = new JLabel("Right Answers: " + correctAnswers);
        rightAnswers.setFont(new Font("Rockwell", Font.PLAIN, 30));
        rightAnswers.setHorizontalAlignment(SwingConstants.CENTER);
        rightAnswers.setBounds(250, 100, 400, 75);

        JLabel wrongAnswersLabel = new JLabel("Wrong Answers: " + wrongAnswers);
        wrongAnswersLabel.setFont(new Font("Rockwell", Font.PLAIN, 30));
        wrongAnswersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wrongAnswersLabel.setBounds(250, 150, 400, 75);

        JLabel timesGivenUp = new JLabel("Number of Times Given Up: " + numOfTimesGivenUp);
        timesGivenUp.setHorizontalAlignment(SwingConstants.CENTER);
        timesGivenUp.setFont(new Font("Rockwell", Font.PLAIN, 30));
        timesGivenUp.setBounds(150, 200, 600, 75);

        //button to let them return to ear training page
        JButton earTraining = new JButton("Ear Training");
        earTraining.setFocusable(false);
        earTraining.setBackground(Color.white);
        earTraining.setBounds(300, 410, 300, 90);
        earTraining.addActionListener(e -> {
            frame.dispose();
            new EarTraining();
        });

        frame.add(earTraining);
        frame.add(timesGivenUp);
        frame.add(exercises);
        frame.add(rightAnswers);
        frame.add(wrongAnswersLabel);
    }

    public void setStaff() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int octave = rand.nextInt(1, 4), noteName = rand.nextInt(1, 13);
        String whereIsMiddleC = "no";

        if (octave == 2 && noteName == 6 || octave == 2 && noteName == 7) { //reassigning octave, so it chooses between bass clef and treble clef for middle c only
            octave = rand.nextInt(1, 3);
            if(octave == 1) {
                whereIsMiddleC = "bass"; //reassigning this variable, so I know where to put the c later on
            } else if (octave == 2) {
                octave = 3;
                whereIsMiddleC = "treble";
            }
        }

        //actaully setting up the staff
        if (octave == 1 || octave == 2 && noteName < 6) {
            ImageIcon bassClef = new ImageIcon("src/bassClef.jpg");
            JLabel bassClef2 = new JLabel(bassClef);
            bassClef2.setBounds(0, 0, 600, 210);
            staff.add(bassClef2, Integer.valueOf(0));
        } else if (octave == 2 || octave == 3){
            ImageIcon trebleClef = new ImageIcon("src/treble-clef.jpg");
            JLabel trebleClef2 = new JLabel(trebleClef);
            trebleClef2.setSize(600, 229);
            trebleClef2.setBounds(0, 0, 600, 223);
            staff.add(trebleClef2, Integer.valueOf(0));
        }
        staff.setBounds(185, 50, 530, 210);
        getNoteName(octave, noteName, whereIsMiddleC);
    }

    public void getNoteName(int octave, int noteName, String whereIsMiddleC) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
         //this method switches the program to the right method based on the noteName.
        switch (noteName) {
            case 1:
                Gs(octave, 0, 0);
                break;
            case 2, 4, 7, 9, 12:
                choosingSharpOrFlat(octave, noteName, whereIsMiddleC); //where is middleC is only useful if it is case 7 (C#) otherwise it is just "no" to indicate that it is not a C
                break;
            case 3:
                As(octave, 0, 0);
                break;
            case 5:
                Bs(octave, 0);
                break;
            case 6:
                Cs(octave, whereIsMiddleC, 0);
                break;
            case 8:
                Ds(octave, 0, 0);
                break;
            case 10:
                Es(octave, 0);
                break;
            case 11:
                Fs(octave, 0);
                break;
        }
    }
    public void Gs(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //setting bounds of the note, then adding it to the staff (JLayeredPane) at an integer value of 1 so that the label rests on top of the staff
         if (octave == 1) {
            wholeNoteTC2.setBounds(230, 153, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(sharp == 1) {  //adjusting the noteName integer based on whether it is sharp or flat so that it will go into the correct set of octave clips
                noteName = 2;
            } else if (flat == 1) {
                noteName = 12;
            } else { //else is there to ensure the correct number is passed in
                noteName = 1;
            }
            octave1Notes(noteName);
        } else if (octave == 2) {
            wholeNoteTC2.setBounds(230, 64, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(sharp == 1) {
                noteName = 2;
            } else if (flat == 1) {
                noteName = 12;
            } else {
                noteName = 1;
            }
            if(noteName == 12) {
                octave1Notes(12); //although this is in octave 2, since the note that has been chosen is technically an F# with the noteName 12, it goes into octave 1 notes. **each octave covers G - F#**
            } else {
                octave2Notes(noteName); //whether it is sharp or flat, it is still in the same octave, therefore it will go into octave 2 notes
            }
        } else if (octave == 3) {
            wholeNoteTC2.setBounds(150, 99, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(sharp != 0) {
                noteName = 2;
            } else if (flat != 0) {
                noteName = 12;
            } else {
                noteName = 1;
            }
            if (noteName == 12) {
                octave2Notes(12);
            } else {
                octave3Notes(noteName);
            }
        } else if (octave == 4) { //this G was created for Gb5, therefore it will always go into octave3Notes with a noteName of 12
            wholeNoteTC2.setBounds(150, 21, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            octave3Notes(12);
        }
        frame.add(staff);
    }

    //similar logic applies to all the other methods that set the bounds of the notes, except there is no need to change octaves when calling the play notes method since the sharps and flats of the note are in the same octave
    public void As(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNoteTC2.setBounds(230, 141, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(sharp == 1) {
                noteName = 4;
            } else if (flat == 1) {
                noteName = 2;
            } else {
                noteName = 3;
            }
            octave1Notes(noteName);
        } else if (octave == 2) {
            wholeNoteTC2.setBounds(230, 51, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(sharp != 0) {
                noteName = 4;
            } else if (flat != 0) {
                noteName = 2;
            } else {
                noteName = 3;
            }
            octave2Notes(noteName);
        } else {
            wholeNoteTC2.setBounds(150, 88, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(sharp != 0) {
                noteName = 4;
            } else if (flat != 0) {
                noteName = 2;
            } else {
                noteName = 3;
            }
            octave3Notes(noteName);
        }
        frame.add(staff);
    }

    public void Bs(int octave, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException { //Bs don't get sharps or flats
        if (octave == 1) {
            wholeNoteTC2.setBounds(230, 128, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if (flat == 1) {
                noteName = 4;
            } else {
                noteName = 5;
            }
            octave1Notes(noteName);
        } else if (octave == 2) {
            wholeNoteTC2.setBounds(230, 40, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(flat == 1) {
                noteName = 4;
            } else {
                noteName = 5;
            }
            octave2Notes(noteName);
        } else {
            wholeNoteTC2.setBounds(150, 77, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(flat == 1) {
                noteName = 4;
            } else {
                noteName = 5;
            }
            octave3Notes(noteName);
        }
        frame.add(staff);
    }

    public void Cs(int octave, String whereIsMiddleC, int sharp) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        ImageIcon middleCTC = new ImageIcon("src/middleCTC.png");
        JLabel middleCTC2 = new JLabel(middleCTC);
        if (octave == 1 && whereIsMiddleC.equals("no")) {
            wholeNoteTC2.setBounds(230, 116, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(sharp == 1) {
                noteName = 7;
            } else {
                noteName = 6;
            }
            octave1Notes(noteName);
        } else if (octave == 1 && whereIsMiddleC.equals("bass")) {
            middleCTC2.setBounds(230, 22, 87, 31);
            staff.add(middleCTC2, Integer.valueOf(1));
            if (sharp == 1) {
                noteName = 7;
            } else {
                noteName = 6;
            }
            octave2Notes(noteName);
        } else if (octave == 3 && whereIsMiddleC.equals("treble")) {
            middleCTC2.setBounds(150, 144, 64, 23);
            staff.add(middleCTC2, Integer.valueOf(1));
            if (sharp == 1) {
                noteName = 7;
            } else {
                noteName = 6;
            }
            octave2Notes(noteName);
        } else {
            wholeNoteTC2.setBounds(150, 66, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if (sharp == 1) {
                noteName = 7;
            } else {
                noteName = 6;
            }
            octave3Notes(noteName);
        }
        frame.add(staff);
    }
    public void Ds(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNoteTC2.setBounds(230, 103, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(sharp == 1) {
                noteName = 9;
            } else if (flat == 1) {
                noteName = 7;
            } else {
                noteName = 8;
            }
            octave1Notes(noteName);
        } else if (octave == 2) {
            wholeNoteTC2.setBounds(150, 133, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if (sharp == 1) {
                noteName = 9;
            } else if (flat == 1) {
                noteName = 7;
            }else {
                noteName = 8;
            }
            octave2Notes(noteName);
        } else {
            wholeNoteTC2.setBounds(150, 55, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if (sharp == 1) {
                noteName = 9;
            } else if (flat == 1) {
                noteName = 7;
            }else {
                noteName = 8;
            }
            octave3Notes(noteName);
        }
        frame.add(staff);
    }
    public void Es(int octave, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNoteTC2.setBounds(230, 90, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if (flat == 1) {
                noteName = 9;
            } else {
                noteName = 10;
            }
            octave1Notes(noteName);
        } else if (octave == 2) {
            wholeNoteTC2.setBounds(150, 122, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if (flat == 1) {
                noteName = 9;
            }else {
                noteName = 10;
            }
            octave2Notes(noteName);
        } else {
            wholeNoteTC2.setBounds(150, 43, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if (flat == 1) {
                noteName = 9;
            }else {
                noteName = 10;
            }
            octave3Notes(noteName);
        }
        frame.add(staff);
    }

    public void Fs(int octave, int sharp) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNoteTC2.setBounds(230, 77, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if(sharp == 1) {
                noteName = 12;
            } else {
                noteName = 11;
            }
            octave1Notes(noteName);
        } else if (octave == 2) {
            wholeNoteTC2.setBounds(150, 110, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if (sharp == 1) {
                noteName = 12;
            } else {
                noteName = 11;
            }
            octave2Notes(noteName);
        } else {
            wholeNoteTC2.setBounds(150, 32, 33, 23);
            staff.add(wholeNoteTC2, Integer.valueOf(1));
            if (sharp == 1) {
                noteName = 12;
            }else {
                noteName = 11;
            }
            octave3Notes(noteName);
        }
        frame.add(staff);
    }

    public void choosingSharpOrFlat(int octave, int noteName, String whereIsMiddleC) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        int sharpOrFlat = rand.nextInt(1, 3);  // 1 is sharp, 2 is flat
        if (sharpOrFlat == 1) {
            sharps(octave, noteName, whereIsMiddleC);
        } else {
            flats(octave, noteName);
        }
    }

    public void sharps(int octave, int noteName, String whereIsMiddleC) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        ImageIcon sharpBC = new ImageIcon("src/sharpSignBC.png");
        JLabel sharpBC2 = new JLabel(sharpBC);
        ImageIcon sharpTC = new ImageIcon("src/sharpSignTC.png");
        JLabel sharpTC2 = new JLabel(sharpTC);

        switch(noteName) { //places the sharp on the staff then calls the corresponding note name method to place the actual note
            case 2: //G#
                if (octave == 1) {
                    sharpBC2.setBounds(200, 133, 25, 65);
                    staff.add(sharpBC2, Integer.valueOf(2));
                    Gs(1, 1, 0);
                } else if (octave == 2) {
                    sharpBC2.setBounds(200, 44, 25, 65);
                    staff.add(sharpBC2, Integer.valueOf(2));
                    Gs(2, 1, 0);
                } else if (octave == 3) {
                    sharpTC2.setBounds(125, 82, 22, 57);
                    staff.add(sharpTC2, Integer.valueOf(2));
                    Gs(3, 1, 0);
                }
                break;
            case 4: //A#
                if (octave == 1) {
                    sharpBC2.setBounds(200, 122, 25, 65);
                    staff.add(sharpBC2, Integer.valueOf(2));
                    As(1, 1, 0);
                } else if (octave == 2) {
                    sharpBC2.setBounds(200, 32, 25, 65);
                    staff.add(sharpBC2, Integer.valueOf(2));
                    As(2, 1, 0);
                } else if (octave == 3) {
                    sharpTC2.setBounds(125, 70, 22, 57);
                    staff.add(sharpTC2, Integer.valueOf(2));
                    As(3, 1, 0);
                }
                break;
            case 7: //C#
                if (octave == 1 && whereIsMiddleC.equals("no")) {
                    sharpBC2.setBounds(200, 95, 25, 65);
                    staff.add(sharpBC2, Integer.valueOf(2));
                    Cs(1, "no", 1);
                } else if (octave == 1 && whereIsMiddleC.equals("bass")) {
                    sharpBC2.setBounds(200, 10, 25, 65);
                    staff.add(sharpBC2, Integer.valueOf(2));
                    Cs(1, "bass", 1);
                } else if (octave == 3 && whereIsMiddleC.equals("treble")) {
                    sharpTC2.setBounds(125, 125, 22, 57);
                    staff.add(sharpTC2, Integer.valueOf(2));
                    Cs(3, "treble", 1);
                } else if (octave == 3 && whereIsMiddleC.equals("no")) {
                    sharpTC2.setBounds(125, 48, 22, 57);
                    staff.add(sharpTC2, Integer.valueOf(2));
                    Cs(3, "no", 1);
                }
                break;
            case 9: //D#
                if (octave == 1) {
                    sharpBC2.setBounds(200, 82, 25, 65);
                    staff.add(sharpBC2, Integer.valueOf(2));
                    Ds(1, 1, 0);
                } else if (octave == 2) {
                    sharpTC2.setBounds(125, 115, 22, 57);
                    staff.add(sharpTC2, Integer.valueOf(2));
                    Ds(2, 1, 0);
                } else if (octave == 3) {
                    sharpTC2.setBounds(125, 37, 22, 57);
                    staff.add(sharpTC2, Integer.valueOf(2));
                    Ds(3, 1, 0);
                }
                break;
            case 12: //F#
                if (octave == 1) {
                    sharpBC2.setBounds(200, 57, 25, 65);
                    staff.add(sharpBC2, Integer.valueOf(2));
                    Fs(1, 1);
                } else if (octave == 2) {
                    sharpTC2.setBounds(125, 93, 22, 57);
                    staff.add(sharpTC2, Integer.valueOf(2));
                    Fs(2, 1);
                } else if (octave == 3) {
                    sharpTC2.setBounds(125, 15, 22, 57);
                    staff.add(sharpTC2, Integer.valueOf(2));
                    Fs(3, 1);
                }
                break;
        }
        saveSharpOrFlat = 1;
        frame.add(staff);
    }

    public void flats(int octave, int noteName) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        ImageIcon flatTC = new ImageIcon("src/flatSignTC.png");
        JLabel flatTC2 = new JLabel(flatTC);

        switch (noteName) { //once again, places the flats on the staff then calls the corresponding note name method to place the whole note
            case 2: //Ab
                if (octave == 1) {
                    flatTC2.setBounds(200, 120, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    As(1, 0, 1);
                } else if (octave == 2) {
                    flatTC2.setBounds(200, 33, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    As(2, 0, 1);
                } else if (octave == 3) {
                    flatTC2.setBounds(115, 68, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    As(3, 0, 1);
                }
                break;
            case 4: //Bb
                if (octave == 1) {
                    flatTC2.setBounds(200, 110, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Bs(1, 1);
                } else if (octave == 2) {
                    flatTC2.setBounds(200, 21, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Bs(2, 1);
                } else if (octave == 3) {
                    flatTC2.setBounds(115, 58, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Bs(3, 1);
                }
                break;
            case 7: //Db
                if (octave == 1) {
                    flatTC2.setBounds(200, 84, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Ds(1, 0, 1);
                } else if (octave == 2) {
                    flatTC2.setBounds(115, 112, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Ds(2, 0, 1);
                } else if (octave == 3) {
                    flatTC2.setBounds(115, 35, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Ds(3, 0, 1);
                }
                break;
            case 9: //Eb
                if (octave == 1) {
                    flatTC2.setBounds(200, 70, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Es(1, 1);
                } else if (octave == 2) {
                    flatTC2.setBounds(115, 103, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Es(2, 1);
                } else if (octave == 3) {
                    flatTC2.setBounds(115, 23, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Es(3, 1);
                }
                break;
            case 12: //Gb
                if (octave == 1) {
                    flatTC2.setBounds(200, 44, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Gs(2, 0, 1);
                } else if (octave == 2) {
                    flatTC2.setBounds(115, 80, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Gs(3, 0, 1);
                } else if (octave == 3) {
                    flatTC2.setBounds(115, 0, 18, 44);
                    staff.add(flatTC2, Integer.valueOf(2));
                    Gs(4, 0, 1);
                }
                break;
        }
        saveSharpOrFlat = 2;
        frame.add(staff);
    }

    public void octave1Notes(int noteName) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        saveNoteName = noteName; //saveNoteName is a global variable that is used to check if the user's answer is correct

        for(int i = 0; i < 12; i++) { //gets the audio input stream of the array of files and puts it in an array of audio input stream using a for loop
            audioStreamFirstOctave[i] = AudioSystem.getAudioInputStream(firstOctaveNotes[i]);
        }
        Clip firstOctave = AudioSystem.getClip();
        firstOctave.open(audioStreamFirstOctave[noteName - 1]); //opens and play the clip. noteName - 1 to get the index
        firstOctave.start();
    }

    public void octave2Notes(int noteName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        saveNoteName = noteName;

        for(int i = 0; i < 12; i++) {
            audioStreamSecondOctave[i] = AudioSystem.getAudioInputStream(secondOctaveNotes[i]);
        }
        Clip secondOctave = AudioSystem.getClip();
        secondOctave.open(audioStreamSecondOctave[noteName - 1]);
        secondOctave.start();
    }

    public void octave3Notes(int noteName) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        saveNoteName = noteName;

        for(int i = 0; i < 12; i++) {
            audioStreamThirdOctave[i] = AudioSystem.getAudioInputStream(thirdOctaveNotes[i]);
        }

        Clip thirdOctave = AudioSystem.getClip();
        thirdOctave.open(audioStreamThirdOctave[noteName - 1]);
        thirdOctave.start();
    }

    public void correct() { //this method just displays the word "correct" in big, bright green letters
        correctAnswers++; //keeps track of the numbers for the view score method
        numOfExercises++;
        for (int i = 0; i < 17; i++) {
            notes[i].setVisible(false); //set all the possible answer buttons to false
        }
        correctMessage.setFont(new Font("Gill Sans", Font.BOLD, 30));
        correctMessage.setForeground(new Color(0x32CD32));
        correctMessage.setBounds(390, 310, 200, 90);

        frame.add(correctMessage);
        nextExercise();
    }

    public void wrong() {
        String saveNoteName2 = getAnswerAsAString(saveNoteName); //gets the answer as a string
        wrongAnswers++; //keeps track for the view score method
        numOfExercises++;
        for (int i = 0; i < 17; i++) {
            notes[i].setVisible(false); //sets answer buttons visible to false
        }
        incorrectAnswer = new JLabel("incorrect answer: " + usersAnswer); //shows user what they put as the answer
        incorrectAnswer.setFont(new Font("Rockwell", Font.PLAIN, 30));
        incorrectAnswer.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectAnswer.setBounds(250, 310, 400, 50);

        correctAnswer = new JLabel("correct answer: " + saveNoteName2); //shows user what the correct answer is
        correctAnswer.setFont(new Font("Rockwell", Font.PLAIN, 30));
        correctAnswer.setHorizontalAlignment(SwingConstants.CENTER);
        correctAnswer.setBounds(250, 340, 400, 50);

        frame.add(incorrectAnswer);
        frame.add(correctAnswer);
        nextExercise();
    }
    public void nextExercise() { //creates a button that says next exercise and disposes the old frame, so a new one can load with a new question
        tellMe.setVisible(false); //tell me is set visible as false to avoid any problems with the previous note playing alongside the next note

        nextExercise.setFont(new Font("Georgia", Font.PLAIN, 25));
        nextExercise.setBounds(300, 410, 300, 90);
        nextExercise.setFocusable(false);
        nextExercise.setBackground(Color.white);
        nextExercise.addActionListener(e -> {
            frame.dispose();
            try {
                new Notes();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        frame.add(nextExercise);
    }
}

