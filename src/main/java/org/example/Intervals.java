package org.example; //COMMENTED AND REMOVED PRINTLNS
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Intervals extends JFrame {
    private static int numOfCorrectAnswers, numOfExercises, numOfIncorrectAnswers, numOfTimesGivenUp;
    JFrame frame = new JFrame();
    ImageIcon wholeNote = new ImageIcon("src/wholeNoteTC.png");
    JLabel wholeNote2 = new JLabel(wholeNote), correctMessage = new JLabel("Correct!"), correctAnswer, incorrectAnswer;
    JLayeredPane staff = new JLayeredPane();
    Random rand = new Random();
    int interval = rand.nextInt(1, 12), octave = rand.nextInt(1, 4), bassNote = rand.nextInt(1, 13), noOfSemitones = 0, secondNote, secondNoteOctave, saveBassNote, saveTopNote, saveOctave, saveSecondNoteOctave, controlTimer = 1, absoluteBassNote;
    ImageIcon speaker = new ImageIcon("src/speaker.jpg");
    JButton replay = new JButton(speaker), nextExercise = new JButton("Next Exercise"), viewScore = new JButton("View Score"), tellMe = new JButton("Tell Me");
    JButton minor2 = new JButton("minor 2"), major2 = new JButton("major 2"), minor3 = new JButton("minor 3"), major3 = new JButton("major 3"), perfect4 = new JButton("perfect 4"), perfect5 = new JButton("perfect 5"), minor6 = new JButton("minor 6"), major6 = new JButton("major 6"), minor7 = new JButton("minor 7"), major7 = new JButton("major 7"), perfectOctave = new JButton("perfect 8");
    JButton[] intervalButtons = {minor2, major2, minor3, major3, perfect4, perfect5, minor6, major6, minor7, major7, perfectOctave};
    String usersAnswer;
    public static ImageIcon sharp = new ImageIcon("src/sharpSignTC.png"), middleCTC = new ImageIcon("src/middleCTC.png");
    public static JLabel sharp2 = new JLabel(sharp), middleCTC2 = new JLabel(middleCTC);
    public static ImageIcon flatTC = new ImageIcon("src/flatSignTC.png");
    public static JLabel flatTC2 = new JLabel(flatTC);
    public Intervals() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        frame.setSize(900, 780);
        frame.setTitle("Daisy's Ear Training Course");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(new JLabel(new ImageIcon("src/background.jpg")));

        frame.setVisible(true);

        //set up the possible answer buttons
        minor2.setBounds(75, 290, 233, 71);
        minor2.setBackground(Color.white);
        minor2.setFocusable(false);
        minor2.addActionListener(e -> {
            usersAnswer = "minor 2"; //saves user answer for if they get it wrong
            if (noOfSemitones == 1) { //checks if they are correct
                try {
                    correct();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        major2.setBounds(333, 290, 233, 71);
        major2.setBackground(Color.white);
        major2.setFocusable(false);
        major2.addActionListener(e -> {
            usersAnswer = "major 2";
            if (noOfSemitones == 2) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        minor3.setBounds(591, 290, 233, 71);
        minor3.setBackground(Color.white);
        minor3.setFocusable(false);
        minor3.addActionListener(e -> {
            usersAnswer = "minor 3";
            if (noOfSemitones == 3) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        major3.setBounds(75, 376, 233, 71);
        major3.setBackground(Color.white);
        major3.setFocusable(false);
        major3.addActionListener(e -> {
            usersAnswer = "major 3";
            if (noOfSemitones == 4) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        perfect4.setBounds(333, 376, 233, 71);
        perfect4.setBackground(Color.white);
        perfect4.setFocusable(false);
        perfect4.addActionListener(e -> {
            usersAnswer = "perfect 4";
            if (noOfSemitones == 5) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        perfect5.setBounds(591, 376, 233, 71);
        perfect5.setBackground(Color.white);
        perfect5.setFocusable(false);
        perfect5.addActionListener(e -> {
            usersAnswer = "perfect 5";
            if (noOfSemitones == 7) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        minor6.setBounds(75, 463, 233, 71);
        minor6.setBackground(Color.white);
        minor6.setFocusable(false);
        minor6.addActionListener(e -> {
            usersAnswer = "minor 6";
            if (noOfSemitones == 8) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        major6.setBounds(333, 463, 233, 71);
        major6.setBackground(Color.white);
        major6.setFocusable(false);
        major6.addActionListener(e -> {
            usersAnswer = "major 6";
            if (noOfSemitones == 9) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        minor7.setBounds(591, 463, 233, 71);
        minor7.setBackground(Color.white);
        minor7.setFocusable(false);
        minor7.addActionListener(e -> {
            usersAnswer = "minor 7";
            if (noOfSemitones == 10) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        major7.setBounds(205, 550, 233, 71);
        major7.setBackground(Color.white);
        major7.setFocusable(false);
        major7.addActionListener(e -> {
            usersAnswer = "major 7";
            if (noOfSemitones == 11) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        perfectOctave.setBounds(462, 550, 233, 71);
        perfectOctave.setBackground(Color.white);
        perfectOctave.setFocusable(false);
        perfectOctave.addActionListener(e -> {
            usersAnswer = "perfect octave";
            if (noOfSemitones == 12) {
                try {
                    correct();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    wrong();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        viewScore.setFocusable(false);
        viewScore.setBackground(Color.white);
        viewScore.setFont(new Font("Rockwell", Font.PLAIN, 20));
        viewScore.setBounds(75, 640, 350, 60);
        viewScore.addActionListener(e -> viewScore());

        tellMe.setFocusable(false);
        tellMe.setBackground(Color.white);
        tellMe.setFont(new Font("Rockwell", Font.PLAIN, 20));
        tellMe.setBounds(475, 640, 350, 60);
        tellMe.addActionListener(e -> {
            try {
                tellAnswer();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });

        frame.add(tellMe);
        frame.add(viewScore);
        frame.add(minor2);
        frame.add(major2);
        frame.add(minor3);
        frame.add(major3);
        frame.add(perfect4);
        frame.add(perfect5);
        frame.add(minor6);
        frame.add(major6);
        frame.add(minor7);
        frame.add(major7);
        frame.add(perfectOctave);
        chooseInterval();
    }

    public void tellAnswer() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String intervalName = getIntervalName();
        numOfTimesGivenUp++; //keeps track of the score
        numOfExercises++;
        viewScore.setVisible(false); //sets all the previous buttons visible to false
        tellMe.setVisible(false);
        correctMessage.setVisible(false);
        for (int i = 0; i < 11; i++) {
            intervalButtons[i].setVisible(false);
        }

        //gives user the answer
        JLabel giveUpCorrectAnswer = new JLabel("Correct Answer: " + intervalName);
        giveUpCorrectAnswer.setFont(new Font("Rockwell", Font.PLAIN, 25));
        giveUpCorrectAnswer.setHorizontalAlignment(SwingConstants.CENTER);
        giveUpCorrectAnswer.setBounds(150, 310, 600, 50);

        frame.add(giveUpCorrectAnswer);
        nextExercise(); //creates next exercise button
    }

    public void viewScore() {
        staff.setVisible(false); //sets all other buttons and labels visible to false
        replay.setVisible(false);
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
        for (int i = 0; i < 11; i++) {
            intervalButtons[i].setVisible(false);
        }

        //gives them their stats
        JLabel exercises = new JLabel("Exercises: " + numOfExercises);
        exercises.setHorizontalAlignment(SwingConstants.CENTER);
        exercises.setFont(new Font("Rockwell", Font.PLAIN, 20));
        exercises.setBounds(325, 50, 250, 75);

        JLabel rightAnswers = new JLabel("Right Answers: " + numOfCorrectAnswers);
        rightAnswers.setHorizontalAlignment(SwingConstants.CENTER);
        rightAnswers.setFont(new Font("Rockwell", Font.PLAIN, 20));
        rightAnswers.setBounds(325, 100, 250, 75);

        JLabel wrongAnswersLabel = new JLabel("Wrong Answers: " + numOfIncorrectAnswers);
        wrongAnswersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wrongAnswersLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
        wrongAnswersLabel.setBounds(325, 150, 250, 75);

        JLabel timesGivenUp = new JLabel("Number of Times Given Up: " + numOfTimesGivenUp);
        timesGivenUp.setHorizontalAlignment(SwingConstants.CENTER);
        timesGivenUp.setFont(new Font("Rockwell", Font.PLAIN, 20));
        timesGivenUp.setBounds(250, 200, 400, 75);

        JButton earTraining = new JButton("Ear Training");
        earTraining.setBounds(300, 410, 300, 90);
        earTraining.setForeground(new Color(0x5954a8));
        earTraining.setBackground(Color.WHITE);
        earTraining.addActionListener(e -> {
            frame.dispose(); //disposes the frame and opens Ear Training
            new EarTraining();
        });

        frame.add(earTraining);
        frame.add(timesGivenUp);
        frame.add(exercises);
        frame.add(rightAnswers);
        frame.add(wrongAnswersLabel);

    }

    public String getIntervalName() { //converts the interval int into a string
        String intervalString;
        intervalString = switch (interval) {
            case 1 -> "minor second";
            case 2 -> "major second";
            case 3 -> "minor third";
            case 4 -> "major third";
            case 5 -> "perfect fourth";
            case 6 -> "perfect fifth";
            case 7 -> "minor sixth";
            case 8 -> "major sixth";
            case 9 -> "minor seventh";
            case 10 -> "major seventh";
            case 11 -> "perfect octave";
            default -> "";
        };
        return intervalString;
    }
    public void wrong() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String intervalName = getIntervalName(); //gets the interval's name as a string so it can be given to the user
        numOfIncorrectAnswers++;
        numOfExercises++;
        for (int i = 0; i < 11; i++) {
            intervalButtons[i].setVisible(false);
        }

        //labels to give the correct and incorrect answer
        incorrectAnswer = new JLabel("incorrect answer: " + usersAnswer);
        incorrectAnswer.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectAnswer.setFont(new Font("Rockwell", Font.PLAIN, 20));
        incorrectAnswer.setBounds(300, 310, 320, 50);

        correctAnswer = new JLabel("correct answer: " + intervalName);
        correctAnswer.setHorizontalAlignment(SwingConstants.CENTER);
        correctAnswer.setFont(new Font("Rockwell", Font.PLAIN, 20));
        correctAnswer.setBounds(300, 340, 320, 50);

        frame.add(incorrectAnswer);
        frame.add(correctAnswer);
        nextExercise();
    }

    public void correct() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        numOfCorrectAnswers++; //for the view score method
        numOfExercises++;
        for (int i = 0; i < 11; i++) {
            intervalButtons[i].setVisible(false); //sets all buttons visible to false to make room for the correct message
        }
        correctMessage.setFont(new Font("Gill Sans", Font.BOLD, 30));
        correctMessage.setForeground(new Color(0x32CD32));
        correctMessage.setBounds(390, 310, 200, 90);

        frame.add(correctMessage);
        nextExercise();
    }

    private void nextExercise() {
        tellMe.setVisible(false); //sets the tell me button visible to false since pressing tell me would mess up the next interval
        nextExercise.setFont(new Font("Georgia", Font.PLAIN, 25));
        nextExercise.setBackground(Color.white);
        nextExercise.setBounds(300, 410, 300, 90);
        nextExercise.addActionListener(e -> {
            frame.dispose();
            try {
                new Intervals();  //reloads for a new question
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        frame.add(nextExercise);
    }

    public void chooseInterval() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        noOfSemitones = switch (interval) { //number of semitones helps place the next note since the interval does not correspond to the number of semitones
            case 1 -> //minor 2
                    1;
            case 2 -> //major 2
                    2;
            case 3 -> //minor 3
                    3;
            case 4 -> //major 3
                    4;
            case 5 -> //perfect 4
                    5;
            case 6 -> //perfect 5
                    7;
            case 7 -> //minor 6
                    8;
            case 8 -> //major 6
                    9;
            case 9 -> //minor 7
                    10;
            case 10 -> //major 7
                    11;
            case 11 -> //perfect octave
                    12;
            default -> noOfSemitones;
        };

        secondNote = bassNote + noOfSemitones; //gets note name of the second note. Refer to notes for the full list
        secondNoteOctave = octave;
        if (secondNote > 12) {
            secondNote -= 12; //note names only go up until 12 so it has to reset and adds another octave
            secondNoteOctave = octave + 1;
            if (secondNoteOctave > 3) { //octave cannot be greater than 3 or else it will be off the staff, so I move it down
                octave--;
                secondNoteOctave--;
            }
        }
        if (octave == 2 && bassNote < 7 && secondNote >= 7 || octave == 2 && bassNote < 7 && secondNote < 7 && secondNoteOctave == 3) { // has to stay on one clef, and going up from c just doesn't work in bass clef
            octave--;
            secondNoteOctave--; //moving them all down an octave so they fit on one staff
        }
        if (octave == 1 && bassNote >= 7 && bassNote <= 12 && secondNote > 6 && secondNoteOctave == 2) { //straddles the 2 clefs so it moves the interval up an octave
            octave++;
            secondNoteOctave++;
        }
        saveBassNote = bassNote; //saving variables for playing the notes
        saveOctave = octave;
        saveTopNote = secondNote;
        saveSecondNoteOctave = secondNoteOctave;

        setStaff(octave, bassNote);
    }

    private void setStaff(int octave, int bassNote) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    //sets the clef depending on the notes
        if (octave == 1 || octave == 2 && secondNote <= 6 && secondNoteOctave != 3) {
            ImageIcon bassClef = new ImageIcon("src/bassClef.jpg");
            JLabel bassClef2 = new JLabel(bassClef);
            bassClef2.setBounds(0, 0, 600, 223); //could change to 223 so it matches treble but would have to adjust all of the bass clef notes
            staff.add(bassClef2, Integer.valueOf(0));
            staff.setBounds(195, 50, 530, 210); //width use to be 550 if anything happens
        } else if (octave == 2 && bassNote >= 6 || octave == 3) {
            ImageIcon trebleClef = new ImageIcon("src/treble-clef.jpg");
            JLabel trebleClef2 = new JLabel(trebleClef);
            trebleClef2.setSize(600, 229); //one below 600 x 223
            trebleClef2.setBounds(0, 0, 600, 223);//they height and width do not match bc its cropped
            staff.add(trebleClef2, Integer.valueOf(0));
            staff.setBounds(195, 50, 530, 210);
        }
        getBassNote(octave, bassNote);
    }
    public void getBassNote(int octave, int bassNote) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //just redirects the bassNote to its method
        switch (bassNote) {
            case 1:
                Gs(octave, 0, 0);
                break;
            case 2, 4, 7, 9, 12:
                choosingSharpOrFlatBassNote(octave, bassNote);
                break;
            case 3:
                As(octave, 0, 0);
                break;
            case 5:
                Bs(octave, 0, 0);
                break;
            case 6:
                Cs(octave, 0, 0);
                break;
            case 8:
                Ds(octave, 0, 0);
                break;
            case 10:
                Es(octave, 0, 0);
                break;
            case 11:
                Fs(octave, 0, 0);
                break;
        }
    }

    public void Gs(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //sets the bounds of the note and alters the noteName if there is a sharp or flat, so that the playing notes methods play the right note.
        if (octave == 1) {
            wholeNote2.setBounds(230, 159, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 2; //Gb cannot exist in this octave
            } else { //else is there to ensure the correct number is passed in
                bassNote = 1;
            }
            octave1NotesIntervals(bassNote);
        } else if (octave == 2) {
            wholeNote2.setBounds(230, 70, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 2;
            } else if (flat == 1) {
                bassNote = 12;
            } else {
                bassNote = 1;
            }
            if(bassNote == 12) {
                octave1NotesIntervals(12);
            } else {
                octave2NotesIntervals(bassNote);
            }
        } else if (octave == 3) {
            wholeNote2.setBounds(150, 99, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp != 0) {
                bassNote = 2;
            } else if (flat != 0) {
                bassNote = 12;
            } else {
                bassNote = 1;
            }
            if (bassNote == 12) {
                octave2NotesIntervals(12);
            } else {
                octave3NotesIntervals(bassNote);
            }
        } else if (octave == 4) { //always going to be a g flat
            wholeNote2.setBounds(150, 21, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            octave3NotesIntervals(12);
        }
        frame.add(staff);
    }

    public void As(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNote2.setBounds(230, 147, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 4;
            } else if (flat == 1) {
                bassNote = 2;
            } else {
                bassNote = 3;
            }
            octave1NotesIntervals(bassNote);
        } else if (octave == 2) {
            wholeNote2.setBounds(230, 58, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp != 0) {
                bassNote = 4;
            } else if (flat != 0) {
                bassNote = 2;
            } else {
                bassNote = 3;
            }
            octave2NotesIntervals(bassNote);
        } else if (octave == 3) {
            wholeNote2.setBounds(150, 88, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp != 0) {
                bassNote = 4;
            } else if (flat != 0) {
                bassNote = 2;
            } else {
                bassNote = 3;
            }
            octave3NotesIntervals(bassNote);
        }

        frame.add(staff);
    }
    public void Bs(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNote2.setBounds(230, 134, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 6;
            } else if (flat == 1) {
                bassNote = 4;
            } else {
                bassNote = 5;
            }
            octave1NotesIntervals(bassNote);
        } else if (octave == 2) {
            wholeNote2.setBounds(230, 46, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 6;
            } else if (flat == 1) {
                bassNote = 4;
            } else {
                bassNote = 5;
            }
            octave2NotesIntervals(bassNote);
        } else if (octave == 3) {
            wholeNote2.setBounds(150, 77, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 6;
            } else if (flat == 1) {
                bassNote = 4;
            } else {
                bassNote = 5;
            }
            octave3NotesIntervals(bassNote);
        }
        frame.add(staff);
    }
    public void Cs(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNote2.setBounds(230, 121, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 7;
            } else if (flat == 1) {
                bassNote = 5;
            } else {
                bassNote = 6;
            }
            octave1NotesIntervals(bassNote);
        } else if (octave == 2) {
            middleCTC2.setBounds(150, 144, 64, 23);
            staff.add(middleCTC2, Integer.valueOf(1));
            if (sharp == 1) {
                bassNote = 7;
            } else if (flat == 1) {
                bassNote = 5;
            } else {
                bassNote = 6;
            }
            octave2NotesIntervals(bassNote);
        } else if (octave == 3) {
            wholeNote2.setBounds(150, 66, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if (sharp == 1) {
                bassNote = 7;
            } else if (flat == 1) {
                bassNote = 5;
            } else {
                bassNote = 6;
            }
            octave3NotesIntervals(bassNote);
        }
        frame.add(staff);
    }
    public void Ds(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNote2.setBounds(230, 109, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 9;
            } else if (flat == 1) {
                bassNote = 7;
            } else {
                bassNote = 8;
            }
            octave1NotesIntervals(bassNote);
        } else if (octave == 2) {
            wholeNote2.setBounds(150, 133, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if (sharp == 1) {
                bassNote = 9;
            } else if (flat == 1) {
                bassNote = 7;
            } else {
                bassNote = 8;
            }
            octave2NotesIntervals(bassNote);
        } else if (octave == 3){
            wholeNote2.setBounds(150, 55, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if (sharp == 1) {
                bassNote = 9;
            } else if (flat == 1) {
                bassNote = 7;
            } else {
                bassNote = 8;
            }
            octave3NotesIntervals(bassNote);
        }
        frame.add(staff);
    }
    public void Es(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNote2.setBounds(230, 96, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if (sharp == 1) {
               bassNote = 11;
            } else if (flat == 1) {
                bassNote = 9;
            } else {
                bassNote = 10;
            }
            octave1NotesIntervals(bassNote);
        } else if (octave == 2) {
            wholeNote2.setBounds(150, 122, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if (sharp == 1) {
                bassNote = 11;
            } else if (flat == 1) {
                bassNote = 9;
            } else {
                bassNote = 10;
            }
            octave2NotesIntervals(bassNote);
        } else if (octave == 3) {
            wholeNote2.setBounds(150, 43, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if (sharp == 1) {
                bassNote = 11;
            } else if (flat == 1) {
                bassNote = 9;
            } else {
                bassNote = 10;
            }
            octave3NotesIntervals(bassNote);
        }
        frame.add(staff);
    }
    public void Fs(int octave, int sharp, int flat) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (octave == 1) {
            wholeNote2.setBounds(230, 83, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 12;
            } else if (flat == 1) {
                bassNote = 10;
            } else {
                bassNote = 11;
            }
            octave1NotesIntervals(bassNote);
        } else if (octave == 2) {
            wholeNote2.setBounds(150, 110, 33, 23);
            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 12;
            } else if (flat == 1) {
                bassNote = 10;
            } else {
                bassNote = 11;
            }
            octave2NotesIntervals(bassNote);
        } else if (octave == 3) {
            wholeNote2.setBounds(150, 32, 33, 23);

            staff.add(wholeNote2, Integer.valueOf(1));
            if(sharp == 1) {
                bassNote = 12;
            } else if (flat == 1) {
                bassNote = 10;
            } else {
                bassNote = 11;
            }
            octave3NotesIntervals(bassNote);
        }
        frame.add(staff);
    }

    public void octave3NotesIntervals(int bassNote) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File g4 = new File("src/g4.wav"); //creating the files
        File gSharp4 = new File("src/gSharp4.wav");
        File a4 = new File("src/a4.wav");
        File aSharp4 = new File("src/aSharp4.wav");
        File b4 = new File("src/b4.wav");
        File c5 = new File("src/c5.wav");
        File cSharp5 = new File("src/cSharp5.wav");
        File d5 = new File("src/d5.wav");
        File dSharp5 = new File("src/dSharp5.wav");
        File e5 = new File("src/e5.wav");
        File f5 = new File("src/f5.wav");
        File fSharp5 = new File("src/fSharp5.wav");

        File[] thirdOctaveNotes = {g4, gSharp4, a4, aSharp4, b4, c5, cSharp5, d5, dSharp5, e5, f5, fSharp5}; //putting the files into an array so that it's easier to make them an audioStream
        AudioInputStream[] audioStream = new AudioInputStream[12];

        for(int i = 0; i < 12; i++) { //getting the audio input stream
            audioStream[i] = AudioSystem.getAudioInputStream(thirdOctaveNotes[i]);
        }

        Clip clip3 = AudioSystem.getClip();
        clip3.open(audioStream[bassNote - 1]); //opening the correct clip at the correct index
        clip3.start();
        ActionListener taskPerformer = e -> {
            try {
                playSecondNote();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        };
        Timer timer = new Timer(1655, taskPerformer);// waiting 1.655 seconds before playing the next note since all the audio files are 1.655 seconds
        timer.setRepeats(false);
        if(controlTimer == 1) { //control timer ensures that the computer knows to stop after playing both notes once
            controlTimer++;
            timer.start();
        }
    }

    public void octave2NotesIntervals(int bassNote) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File g3 = new File("src/g3.wav");
        File gSharp3 = new File("src/gSharp3.wav");
        File a3 = new File("src/a3.wav");
        File aSharp3 = new File("src/aSharp3.wav");
        File b3 = new File("src/b3.wav");
        File c4 = new File("src/c4.wav");
        File cSharp4 = new File("src/cSharp4.wav");
        File d4 = new File("src/d4.wav");
        File dSharp4 = new File("src/dSharp4.wav");
        File e4 = new File("src/e4.wav");
        File f4 = new File("src/f4.wav");
        File fSharp4 = new File("src/fSharp4.wav");

        File[] secondOctaveNotes = {g3, gSharp3, a3, aSharp3, b3, c4, cSharp4, d4, dSharp4, e4, f4, fSharp4};
        AudioInputStream[] audioStream = new AudioInputStream[12];

        for(int i = 0; i < 12; i++) {
            audioStream[i] = AudioSystem.getAudioInputStream(secondOctaveNotes[i]);
        }
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream[bassNote - 1]);
        clip.start();
        ActionListener taskPerformer = e -> {
            try {
                playSecondNote();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        };
        Timer timer = new Timer(1655, taskPerformer);
        timer.setRepeats(false);
        if(controlTimer == 1) {
            controlTimer++; //the user resets it back to one when the user clicks next exercise
            timer.start();
        }
    }

    public void octave1NotesIntervals(int bassNote) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File g2 = new File("src/g2.wav");
        File gSharp2 = new File("src/gSharp2.wav");
        File a2 = new File("src/a2.wav");
        File aSharp2 = new File("src/bFlat2.wav");
        File b2 = new File("src/b2.wav");
        File c3 = new File("src/c3.wav");
        File cSharp3 = new File("src/cSharp3.wav");
        File d3 = new File("src/d3.wav");
        File dSharp3 = new File("src/eFlat3.wav");
        File e3 = new File("src/e3.wav");
        File f3 = new File("src/f3.wav");
        File fSharp3 = new File("src/fSharp3.wav");

        File[] firstOctaveNotes = {g2, gSharp2, a2, aSharp2, b2, c3, cSharp3, d3, dSharp3, e3, f3, fSharp3};
        AudioInputStream[] audioStream = new AudioInputStream[12];

        for(int i = 0; i < 12; i++) {
            audioStream[i] = AudioSystem.getAudioInputStream(firstOctaveNotes[i]);
        }
        Clip clip1 = AudioSystem.getClip();
        clip1.open(audioStream[bassNote - 1]);
        clip1.start();
        ActionListener taskPerformer = e -> {
            try {
                playSecondNote();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        };
        Timer timer = new Timer(1655, taskPerformer);
        timer.setRepeats(false);
        if(controlTimer == 1) {
            controlTimer++;
            timer.start();
        }
    }

    public void playSecondNote() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        switch(saveTopNote) { //redirects saveTopNote to its method to play the second note, passing in the bass note
            case 1: //all for Gs
                if (saveSecondNoteOctave == 2) { //there is no perfect 1 in my program so save second octave will not be in octave 1
                    octave2NotesIntervals(1);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(1);
                }
                break;
            case 2:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(2);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(2);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(2);
                }
                break;
            case 3:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(3);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(3);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(3);
                }
                break;
            case 4:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(4);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(4);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(4);
                }
                break;
            case 5:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(5);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(5);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(5);
                }
                break;
            case 6:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(6);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(6);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(6);
                }
                break;
            case 7:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(7);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(7);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(7);
                }
                break;
            case 8:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(8);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(8);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(8);
                }
                break;
            case 9:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(9);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(9);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(9);
                }
                break;
            case 10:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(10);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(10);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(10);
                }
                break;
            case 11:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(11);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(11);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(11);
                }
                break;
            case 12:
                if(saveSecondNoteOctave == 1) {
                    octave1NotesIntervals(12);
                } else if (saveSecondNoteOctave == 2) {
                    octave2NotesIntervals(12);
                } else if (saveSecondNoteOctave == 3) {
                    octave3NotesIntervals(12);
                }
                break;
        }
    }

    public void choosingSharpOrFlatBassNote(int octave, int bassNote) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        int sharpOrFlat = rand.nextInt(1, 3); //random int for sharp or flat for the bass note and places the sharp or flat symbol on the correct line. 1 = sharp, 2 = flat
        if (sharpOrFlat == 1) { //sharp
            switch (bassNote) {
                case 2: //G#
                    absoluteBassNote = 1;
                    if (octave == 1) {
                        sharp2.setBounds(200, 139, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                        Gs(1, 1, 0);
                    } else if (octave == 2) {
                        sharp2.setBounds(200, 49, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                        Gs(2, 1, 0);
                    } else if (octave == 3) {
                        sharp2.setBounds(125, 82, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                        Gs(3, 1, 0);
                    }
                    break;
                case 4: //A#
                    absoluteBassNote = 2;
                    if (octave == 1) {
                        sharp2.setBounds(200, 126, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                        As(1, 1, 0);
                    } else if (octave == 2) {
                        sharp2.setBounds(200, 38, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                        As(2, 1, 0);
                    } else if (octave == 3) {
                        sharp2.setBounds(125, 70, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                        As(3, 1, 0);
                    }
                    break;
                case 7: //C#
                    absoluteBassNote = 4;
                    if (octave == 1) {
                        sharp2.setBounds(200, 100, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                        Cs(1, 1, 0);
                    } else if (octave == 2) {
                        sharp2.setBounds(125, 125, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                        Cs(2, 1, 0);
                    } else if (octave == 3) {
                        sharp2.setBounds(125, 48, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                        Cs(3, 1, 0);
                    }
                    break;
                case 9: //D#
                    absoluteBassNote = 5;
                    if (octave == 1) {
                        sharp2.setBounds(200, 88, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                        Ds(1, 1, 0);
                    } else if (octave == 2) {
                        sharp2.setBounds(125, 115, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                        Ds(2, 1, 0);
                    } else if (octave == 3) {
                        sharp2.setBounds(125, 37, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                        Ds(3, 1, 0);
                    }
                    break;
                case 12: //F#
                    absoluteBassNote = 7;
                    if (octave == 1) {
                        sharp2.setBounds(200, 62, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                        Fs(1, 1, 0);
                    } else if (octave == 2) {
                        sharp2.setBounds(125, 93, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                        Fs(2, 1, 0);
                    } else if (octave == 3) {
                        sharp2.setBounds(125, 15, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                        Fs(3, 1, 0);
                    }
                    break;
            }
            frame.add(staff);
        } else {
            switch (bassNote) {
                case 2: //Ab
                    absoluteBassNote = 2;
                    if (octave == 1) {
                        flatTC2.setBounds(200, 127, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        As(1, 0, 1);
                    } else if (octave == 2) {
                        flatTC2.setBounds(200, 39, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        As(2, 0, 1);
                    } else if (octave == 3) {
                        flatTC2.setBounds(115, 68, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        As(3, 0, 1);
                    }
                    break;
                case 4: //Bb
                    absoluteBassNote = 3;
                    if (octave == 1) {
                        flatTC2.setBounds(200, 115, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        Bs(1, 0, 1);
                    } else if (octave == 2) {
                        flatTC2.setBounds(200, 25, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        Bs(2, 0, 1);
                    } else if (octave == 3) {
                        flatTC2.setBounds(115, 58, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        Bs(3, 0, 1);
                    }
                    break;
                case 7: //Db
                    absoluteBassNote = 5;
                    if (octave == 1) {
                        flatTC2.setBounds(200, 89, 18, 44);
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
                    absoluteBassNote = 6;
                    if (octave == 1) {
                        flatTC2.setBounds(200, 76, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        Es(1, 0, 1);
                    } else if (octave == 2) {
                        flatTC2.setBounds(115, 103, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        Es(2, 0, 1);
                    } else if (octave == 3) {
                        flatTC2.setBounds(115, 23, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        Es(3, 0, 1);
                    }
                    break;
                case 12: //Gb
                    absoluteBassNote = 1;
                    if (octave == 1) {
                        flatTC2.setBounds(200, 51, 18, 44);
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
            frame.add(staff);
        }

    }
}

