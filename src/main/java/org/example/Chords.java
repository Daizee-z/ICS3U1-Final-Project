package org.example;  //COMMENTS ARE IN AND PRINTLNS ARE OUT
import javax.sound.sampled.*; //there is one combo that break it  augmented chord B in bass clef
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import static org.example.Intervals.*;
import static org.example.Notes.*;


public class Chords extends JFrame{
JFrame frame = new JFrame();
Random rand = new Random();
int chordQuality = rand.nextInt(1, 5), root = rand.nextInt(1, 13), rootName = 0, thirdNoteOctave = 0, fifthNoteOctave = 0, thirdsSemitone = 0, fifthsSemitone = 0, absoluteThirdNote = 0, absoluteFifthNote = 0, third = 0, fifth = 0, octave = rand.nextInt(1, 4), saveRoot = 0;
public static int numOfCorrectAnswers, numOfExercises, numOfIncorrectAnswers;
JLayeredPane staff = new JLayeredPane();
ImageIcon wholeNote = new ImageIcon("src/wholeNoteTC.png");
JLabel wholeNote2 = new JLabel(wholeNote), correctMessage = new JLabel("Correct!"), correctAnswer, incorrectAnswer;
JButton diminished = new JButton("diminished"),  minor = new JButton("minor"), major = new JButton("major"), augmented = new JButton("augmented"), nextExercise = new JButton("Next Exercise"), viewScore = new JButton("View Score"), tellMe = new JButton("Tell Me");
JButton[] chords = {diminished, minor, major, augmented};
String userAnswer;

    public Chords() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
       frame.setSize(900, 650);
       frame.setTitle("Daisy's Ear Training Course");
       frame.setResizable(false);
       frame.setLayout(null);
       frame.setLocationRelativeTo(null);
       frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
       frame.setContentPane(new JLabel(new ImageIcon("src/background.jpg")));

       //creating my possible answer buttons
        diminished.setFocusable(false);
        diminished.setBackground(Color.white);
        diminished.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
        diminished.setBounds(75, 330, 168, 100);
        diminished.addActionListener(e -> {
            userAnswer = "diminished"; //user answer will be used if they get it wrong
            checkAnswer(1);
        });

        minor.setFocusable(false);
        minor.setBackground(Color.white);
        minor.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
        minor.setBounds(268, 330, 168, 100);
        minor.addActionListener(e -> {
            userAnswer = "minor";
            checkAnswer(2);
        });

        major.setFocusable(false);
        major.setBackground(Color.white);
        major.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
        major.setBounds(461, 330, 168, 100);
        major.addActionListener(e -> {
            userAnswer = "major";
            checkAnswer(3);
        });

        augmented.setFocusable(false);
        augmented.setBackground(Color.white);
        augmented.setFont(new Font("Book Antiqua", Font.PLAIN, 18));
        augmented.setBounds(654, 330, 168, 100);
        augmented.addActionListener(e -> {
            userAnswer = "augmented";
            checkAnswer(4);
        });

        viewScore.setFocusable(false); //not using the other viewScore buttons bc they have different bounds
        viewScore.setFont(new Font("Rockwell", Font.PLAIN, 20));
        viewScore.setBackground(Color.white);
        viewScore.setBounds(75, 490, 350, 60);
        viewScore.addActionListener(e -> {
            viewScore();
        });

        tellMe.setFocusable(false);
        tellMe.setFont(new Font("Rockwell", Font.PLAIN, 20));
        tellMe.setBackground(Color.white);
        tellMe.setBounds(475, 490, 350, 60);
        tellMe.addActionListener(e -> {
            try {
                tellMe();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        });

        frame.add(tellMe);
        frame.add(viewScore);
        frame.add(augmented);
        frame.add(minor);
        frame.add(major);
        frame.add(diminished);
        frame.setVisible(true);
        getAllNotes();
   }

    private void tellMe() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        numOfTimesGivenUp++;  //keeps track for view score
        numOfExercises++;
        viewScore.setVisible(false); //makes the irrelevant buttons and label invisible
        tellMe.setVisible(false);
        correctMessage.setVisible(false);
        for (int i = 0; i < 4; i++) {
            chords[i].setVisible(false);
        }

        String chordQuality2 = switch (chordQuality) { //gets the answer in a string
            case 1 -> "diminished";
            case 2 -> "minor";
            case 3 -> "major";
            case 4 -> "augmented";
            default -> "";
        };

        //displays answer to the user
        JLabel giveUpCorrectAnswer = new JLabel("Correct Answer: " + chordQuality2);
        giveUpCorrectAnswer.setFont(new Font("Rockwell", Font.PLAIN, 20));
        giveUpCorrectAnswer.setHorizontalAlignment(SwingConstants.CENTER);
        giveUpCorrectAnswer.setBounds(250, 310, 400, 60);

        frame.add(giveUpCorrectAnswer);
        nextExercise();
    }

    private void viewScore() {
        staff.setVisible(false); //makes space for the labels
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
        for (int i = 0; i < 4; i++) {
            chords[i].setVisible(false);
        }

        //reports to the user their score
        JLabel exercises = new JLabel("Number of Exercises today: " + numOfExercises);
        exercises.setFont(new Font("Rockwell", Font.PLAIN, 30));
        exercises.setHorizontalAlignment(SwingConstants.CENTER);
        exercises.setBounds(150, 50, 600, 75);

        JLabel rightAnswers = new JLabel("Right Answers: " + numOfCorrectAnswers);
        rightAnswers.setFont(new Font("Rockwell", Font.PLAIN, 30));
        rightAnswers.setHorizontalAlignment(SwingConstants.CENTER);
        rightAnswers.setBounds(250, 100, 400, 75);

        JLabel wrongAnswersLabel = new JLabel("Wrong Answers: " + numOfIncorrectAnswers);
        wrongAnswersLabel.setFont(new Font("Rockwell", Font.PLAIN, 30));
        wrongAnswersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wrongAnswersLabel.setBounds(250, 150, 400, 75);

        JLabel timesGivenUp = new JLabel("Number of Times Given Up: " + numOfTimesGivenUp);
        timesGivenUp.setHorizontalAlignment(SwingConstants.CENTER);
        timesGivenUp.setFont(new Font("Rockwell", Font.PLAIN, 30));
        timesGivenUp.setBounds(150, 200, 600, 75);

        JButton earTraining = new JButton("Ear Training"); //ear training disposes old frame so new one can come in w/ a new question
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

    private void checkAnswer(int button) { //button is the users answer which is represented by a number
        if(button != chordQuality) { //if they are wrong
            String chordQuality2 = getChordQuality();
            numOfIncorrectAnswers++;
            numOfExercises++;
            viewScore.setVisible(false);
            tellMe.setVisible(false);
            for (int i = 0; i < 4; i++) {
                chords[i].setVisible(false);
            }
            incorrectAnswer = new JLabel("incorrect answer: " + userAnswer);
            incorrectAnswer.setFont(new Font("Rockwell", Font.PLAIN, 20));
            incorrectAnswer.setHorizontalAlignment(SwingConstants.CENTER);
            incorrectAnswer.setBounds(290, 310, 320, 50);

            correctAnswer = new JLabel("correct answer: " + chordQuality2);
            correctAnswer.setFont(new Font("Rockwell", Font.PLAIN, 20));
            correctAnswer.setHorizontalAlignment(SwingConstants.CENTER);
            correctAnswer.setBounds(290, 340, 320, 50);

            frame.add(incorrectAnswer);
            frame.add(correctAnswer);
        } else { //if they are right
            numOfCorrectAnswers++;
            numOfExercises++;
            for (int i = 0; i < 4; i++) {
                chords[i].setVisible(false);
            }
            viewScore.setVisible(false);
            tellMe.setVisible(false);
            correctMessage.setFont(new Font("Gill Sans", Font.BOLD, 30));
            correctMessage.setForeground(new Color(0x32CD32));
            correctMessage.setBounds(390, 310, 200, 90);

            frame.add(correctMessage);
        }
       frame.repaint();
       try { //whether they are right or wrong, they are prompted to click the next exercise button
           nextExercise();
       } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
           throw new RuntimeException(e);
       }

   }
    private void nextExercise() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        //creation of a giant button
        nextExercise.setFont(new Font("Georgia", Font.PLAIN, 25));
        nextExercise.setBounds(300, 410, 300, 90);
        nextExercise.setBackground(Color.white);
        nextExercise.addActionListener(e -> {
            frame.dispose();
            try {
                new Chords(); //reloads the class, therefore new exercise
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        frame.add(nextExercise);
        frame.repaint();
        playNotes(saveRoot, third, fifth);
    }


    private String getChordQuality() {
        String chordQualityString;
        chordQualityString = switch (chordQuality) {
            case 1 -> "diminished";
            case 2 -> "minor";
            case 3 -> "major";
            case 4 -> "augmented";
            default -> "";
        };
        return chordQualityString;
    }

    public void getAllNotes() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

       /*Chord Qualities
       1 = diminish
       2 = minor
       3 = major
       4 = augmented*/

       if (chordQuality == 1) { //assigns the correct amt of semitones to create the chord
           thirdsSemitone = 3;
           fifthsSemitone = 6;
       } else if (chordQuality == 2) {
           thirdsSemitone = 3;
           fifthsSemitone = 7;
       } else if (chordQuality == 3) {
           thirdsSemitone = 4;
           fifthsSemitone = 7;
       } else {
           thirdsSemitone = 4;
           fifthsSemitone = 8;
       }

       saveRoot = root; //saves the root as it could be changed in the following if statement

       if(root == 2 || root == 4 || root == 8 || root == 10 || root == 12) { //black keys
           int sharpOrFlat = rand.nextInt(1, 3);
           if(sharpOrFlat == 1) { //sharp
               root -= 1;
           } else if(sharpOrFlat == 2) { //flat
               if(root == 12) {
                   root = 1;
               } else {
                   root += 1;
               }
           }
       }

        rootName = switch(root) { //this ensures that the chord is indeed made up of thirds and fifths since intervals are relative
            case 1 -> 1;
            case 3 -> 2;
            case 5 -> 3;
            case 6 -> 4;
            case 7 -> 5;
            case 9 -> 6;
            case 11 -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + root);
        };

        absoluteThirdNote = rootName + 2; //makes sure that the note is written as a third higher than the root. eg. Quality: minor. root is G. G + 3 = B. G + 3 semitones = Bb or A#, but since it has to be a B, it is going to be Bb
        absoluteFifthNote = rootName + 4;

        /* ABSOLUTE NOTES
        1 = G
        2 = A
        3 = B
        4 = C
        5 = D
        6 = E
        7 = F
         */
        if(absoluteFifthNote > 7) { //if it is greater than a 7 (F) then it subtracts 7 so it restarts at G
            absoluteFifthNote -= 7;
        }

        if(absoluteThirdNote > 7) { //same thing for this
            absoluteThirdNote -= 7;
        }

        thirdNoteOctave = octave; //set the octaves as the same to begin with
        fifthNoteOctave = octave;

        third = saveRoot + thirdsSemitone; //getting the notename of the third and the fifth. noteName chart is in Notes class
        fifth = saveRoot + fifthsSemitone;

        if(third > 12) { //if it is greater than noteName 12, then it is in the next octave
            third -= 12;
            thirdNoteOctave++;
        }

        if(fifth > 12) { //similar situation in this
            fifth -= 12;
            fifthNoteOctave++;
        }

        //this if statement ensure that the full chord stays on the treble clef
        if(thirdNoteOctave == 4 || fifthNoteOctave == 4) { //I have no 4th octave, so I move the chord one octave lower
            octave--;
            thirdNoteOctave--;
            fifthNoteOctave--;
        }
        //the following if statement ensure that the chord will not straddle the two clefs -----------------------------------
        if(octave == 2 && root < 6 && fifthNoteOctave == 2 && fifth > 6) {
            octave--;
            thirdNoteOctave--;
            fifthNoteOctave--;
        }

        //actually setting the staff now
        if (octave == 1 || fifth <= 6 && fifthNoteOctave == 2) {
            ImageIcon bassClef = new ImageIcon("src/bassClef.jpg");
            JLabel bassClef2 = new JLabel(bassClef);
            bassClef2.setBounds(0, 0, 600, 223);
            staff.add(bassClef2, Integer.valueOf(0));
        } else if (octave == 2 && saveRoot >= 6 || octave == 3) {
            ImageIcon trebleClef = new ImageIcon("src/treble-clef.jpg");
            JLabel trebleClef2 = new JLabel(trebleClef);
            trebleClef2.setBounds(0, 0, 600, 223);
            staff.add(trebleClef2, Integer.valueOf(0));
        }
        staff.setBounds(185, 50, 530, 210);
        setNotes();
    }

    private void setNotes() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(saveRoot == 2 || saveRoot == 4 || saveRoot == 7 || saveRoot == 9 || saveRoot == 12) { //first setting bounds of the flats if there is any
            if(octave == 1){
                if(saveRoot == 2) {
                    if(rootName == 1) { //G#
                        sharp2.setBounds(200, 139, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 2){ //Ab
                        flatTC2.setBounds(200, 127, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if(saveRoot == 4) {
                    if(rootName == 2) { //A#
                        sharp2.setBounds(200, 126, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 3){ //Bb
                        flatTC2.setBounds(200, 115, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if(saveRoot == 7) {
                    if(rootName == 4) { //C#
                        sharp2.setBounds(200, 100, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 5){ //Db
                        flatTC2.setBounds(200, 89, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if (saveRoot == 9) {
                    if(rootName == 5) { //D#
                        sharp2.setBounds(200, 88, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 6){ //Eb
                        flatTC2.setBounds(200, 76, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if (saveRoot == 12) {
                    if(rootName == 7) { //F#
                        sharp2.setBounds(200, 62, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 1) { //Gb
                        flatTC2.setBounds(200, 51, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        octave++; //octave++ makes it so that the G is in the correct octave and not the one below it. Same goes for all the other Gbs
                    }
                }
            } else if(octave == 2){
                if(saveRoot == 2) {
                    if(rootName == 1) { //G#
                        sharp2.setBounds(200, 49, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 2){ //Ab
                        flatTC2.setBounds(200, 39, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if(saveRoot == 4) {
                    if(rootName == 2) { //A#
                        sharp2.setBounds(200, 38, 25, 65);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 3){ //Bb
                        flatTC2.setBounds(200, 25, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if(saveRoot == 7) {
                    if(rootName == 4) { //C#
                        sharp2.setBounds(125, 125, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 5){ //Db
                        flatTC2.setBounds(115, 112, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if (saveRoot == 9) {
                    if(rootName == 5) { //D#
                        sharp2.setBounds(125, 115, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 6){ //Eb
                        flatTC2.setBounds(115, 103, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if (saveRoot == 12) {
                    if(rootName == 7) { //F#
                        sharp2.setBounds(125, 93, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 1) { //Gb
                        flatTC2.setBounds(115, 80, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        octave++;
                    }
                }
            } else if(octave == 3) {
                if(saveRoot == 2) {
                    if(rootName == 1) { //G#
                        sharp2.setBounds(125, 82, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 2){ //Ab
                        flatTC2.setBounds(115, 68, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if(saveRoot == 4) {
                    if(rootName == 2) { //A#
                        sharp2.setBounds(125, 70, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 3){ //Bb
                        flatTC2.setBounds(115, 58, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if(saveRoot == 7) {
                    if(rootName == 4) { //C#
                        sharp2.setBounds(125, 48, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 5){ //Db
                        flatTC2.setBounds(115, 35, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if (saveRoot == 9) {
                    if(rootName == 5) { //D#
                        sharp2.setBounds(125, 37, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 6){ //Eb
                        flatTC2.setBounds(115, 23, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                    }
                } else if (saveRoot == 12) {
                    if(rootName == 7) { //F#
                        sharp2.setBounds(125, 15, 22, 57);
                        staff.add(sharp2, Integer.valueOf(2));
                    } else if (rootName == 1) { //Gb
                        flatTC2.setBounds(115, 0, 18, 44);
                        staff.add(flatTC2, Integer.valueOf(2));
                        octave++;
                    }
                }
            }
            frame.add(staff);
        }

        //placing notes on the staff
        if(rootName == 1) { //Gs
            if (octave == 1) {
                wholeNote2.setBounds(230, 159, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 2) {
                wholeNote2.setBounds(230, 70, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 3) {
                wholeNote2.setBounds(150, 99, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 4) {
                wholeNote2.setBounds(150, 21, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            }
        } else if(rootName == 2) { //As
            if (octave == 1) {
                wholeNote2.setBounds(230, 147, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 2) {
                wholeNote2.setBounds(230, 58, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 3) {
                wholeNote2.setBounds(150, 88, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            }
        } else if(rootName == 3) { //Bs
            if (octave == 1) {
                wholeNote2.setBounds(230, 134, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 2) {
                wholeNote2.setBounds(230, 46, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 3) {
                wholeNote2.setBounds(150, 77, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            }
        } else if(rootName == 4) { //Cs
            if (octave == 1) {
                wholeNote2.setBounds(230, 121, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 2) {
                middleCTC2.setBounds(150, 144, 64, 23);
                staff.add(middleCTC2, Integer.valueOf(1));
            } else if (octave == 3) {
                wholeNote2.setBounds(150, 66, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            }
        } else if(rootName == 5) { //Ds
            if (octave == 1) {
                wholeNote2.setBounds(230, 109, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 2) {
                wholeNote2.setBounds(150, 133, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 3){
                wholeNote2.setBounds(150, 55, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            }
        } else if(rootName == 6) { //Es
            if (octave == 1) {
                wholeNote2.setBounds(230, 96, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 2) {
                wholeNote2.setBounds(150, 122, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 3) {
                wholeNote2.setBounds(150, 43, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            }
        } else if(rootName == 7) { //Fs
            if (octave == 1) {
                wholeNote2.setBounds(230, 83, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 2) {
                wholeNote2.setBounds(150, 110, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            } else if (octave == 3) {
                wholeNote2.setBounds(150, 32, 33, 23);
                staff.add(wholeNote2, Integer.valueOf(1));
            }

        }
        frame.add(staff);
        playNotes(saveRoot, third, fifth);
    }


    public void playNotes(int saveRoot, int third, int fifth) throws LineUnavailableException, IOException, UnsupportedAudioFileException, IllegalStateException { //need octave and saveRoot that I can get from the global variables, however gotta be extra careful w/ the Gs
        Clip rootNote = AudioSystem.getClip(); //creating 3 separate clips for each note in the chord
        Clip thirdNote = AudioSystem.getClip();
        Clip fifthNote = AudioSystem.getClip();

        rootNote.stop(); //stopping and closing the clip to fix an error
        thirdNote.stop();
        fifthNote.stop();
        rootNote.close();
        thirdNote.close();
        fifthNote.close();
        for(int i = 0; i < 12; i++) { //convert ot audioInputStream arrays
            audioStreamFirstOctave[i] = AudioSystem.getAudioInputStream(firstOctaveNotes[i]);
        }
        for(int i = 0; i < 12; i++) {
            audioStreamSecondOctave[i] = AudioSystem.getAudioInputStream(secondOctaveNotes[i]);
        }
        for(int i = 0; i < 12; i++) {
            audioStreamThirdOctave[i] = AudioSystem.getAudioInputStream(thirdOctaveNotes[i]);
        }

        if(saveRoot == 12 && root == 1) { //for the Gb that have a saveRoot of 12, but need to play the Gb from the octave below it
            octave--;
        }

        //opens the clip for each of the 3 notes in the chord
        if(octave == 1) {
            rootNote.open(audioStreamFirstOctave[saveRoot - 1]);
        } else if(octave == 2) {
            rootNote.open(audioStreamSecondOctave[saveRoot - 1]);
        } else if(octave == 3) {
            rootNote.open(audioStreamThirdOctave[saveRoot - 1]);
        }
        //for opening the audio stream of the third
        if(thirdNoteOctave == 1) {
            thirdNote.open(audioStreamFirstOctave[third - 1]);
        } else if(thirdNoteOctave == 2) {
            thirdNote.open(audioStreamSecondOctave[third - 1]);
        } else if(thirdNoteOctave == 3) {
            thirdNote.open(audioStreamThirdOctave[third - 1]);
        }
        //same thing for the fifth
        if(fifthNoteOctave == 1) {
            fifthNote.open(audioStreamFirstOctave[fifth - 1]);
        } else if(fifthNoteOctave == 2) {
            fifthNote.open(audioStreamSecondOctave[fifth - 1]);
        } else if(fifthNoteOctave == 3) {
            fifthNote.open(audioStreamThirdOctave[fifth - 1]);
        }
        //where the notes are actually played
        rootNote.start();
        thirdNote.start();
        fifthNote.start();
    }


}

