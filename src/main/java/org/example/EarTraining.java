package org.example; //ALL DONE
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class EarTraining extends JFrame{
    public EarTraining() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 650);
        this.setTitle("Daisy's Ear Training Course");
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.white);

        // creating the logo
        ImageIcon musicNotes = new ImageIcon("src/musicNote.png");
        JLabel logo = new JLabel(musicNotes);
        logo.setFont(new Font("Broadway", Font.BOLD, 60));
        logo.setBounds(75, 30, 70, 70);

        // label for my slide
        JLabel heading = new JLabel("Ear Training");
        heading.setBounds(150, 30, 357, 80);
        heading.setFont(new Font("Apple Casual", Font.BOLD, 60));
        heading.setForeground(Color.BLACK);

        // the creation of return to home screen button
        JButton returnToHomeScreen = new JButton("home");
        returnToHomeScreen.setBounds(725, 30, 100, 50);
        returnToHomeScreen.setFocusable(false);
        returnToHomeScreen.setBackground(Color.WHITE);
        returnToHomeScreen.setFont(new Font("Serif", Font.PLAIN, 20));
        returnToHomeScreen.setForeground(new Color(0x5954a8));
        returnToHomeScreen.setBorderPainted(false);
        returnToHomeScreen.setHorizontalAlignment(SwingConstants.CENTER);
        returnToHomeScreen.addActionListener(e ->  {
            this.dispose();
            new HomeScreen();
        });

        // now making the giant buttons
        JButton notes = new JButton("Notes");
        notes.setFocusable(false);
        notes.setBackground(Color.WHITE);
        notes.setBorderPainted(false);
        notes.setFont(new Font("Serif", Font.PLAIN, 30));
        notes.setForeground(new Color(0x5954a8));
        notes.setHorizontalAlignment(SwingConstants.LEFT);
        notes.setBounds(75, 135, 750, 100);
        notes.addActionListener(e -> {
            try {
                this.dispose();
                new Notes();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton intervals = new JButton("Interval");
        intervals.setFocusable(false);
        intervals.setBackground(Color.WHITE);
        intervals.setBorderPainted(false);
        intervals.setFont(new Font("Serif", Font.PLAIN, 30));
        intervals.setForeground(new Color(0x5954a8));
        intervals.setHorizontalAlignment(SwingConstants.LEFT);
        intervals.setBounds(75, 295, 750, 100);
        intervals.addActionListener(e -> {
            try {
                this.dispose();
                new Intervals();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton chordQuality = new JButton("Chord Quality");
        chordQuality.setFocusable(false);
        chordQuality.setBackground(Color.WHITE);
        chordQuality.setBorderPainted(false);
        chordQuality.setFont(new Font("Serif", Font.PLAIN, 30));
        chordQuality.setForeground(new Color(0x5954a8));
        chordQuality.setHorizontalAlignment(SwingConstants.LEFT);
        chordQuality.setBounds(75, 455, 750, 100);
        chordQuality.addActionListener(e -> {
            this.dispose();
            try {
                new Chords();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            }
        });

        //adding giant buttons onto the jframe
        this.add(logo);
        this.add(heading);
        this.add(notes);
        this.add(intervals);
        this.add(chordQuality);
        this.add(returnToHomeScreen);

        this.setVisible(true);
    }
}
