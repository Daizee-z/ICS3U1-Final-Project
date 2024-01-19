package org.example;  //ALL DONE
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.*;
import java.io.IOException;
import javax.swing.border.BevelBorder;

public class HomeScreen extends JFrame{
    public HomeScreen() {
        this.getContentPane().setBackground(new Color(0xE5D8CE));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(450, 300);
        this.setTitle("Daisy's Ear Training");
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        // constructing my title ----------------------------------------------------------
        JLabel welcomeMessage = new JLabel("Welcome!");
        welcomeMessage.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 60));
        welcomeMessage.setBounds(89, 40, 277, 52);
        welcomeMessage.setForeground(new Color(0x000000));
        JLabel welcomeMessage2 = new JLabel("to Daisy's Ear Training");
        welcomeMessage2.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 18));
        welcomeMessage2.setBounds(140, 85, 171, 40);

        // constructing my buttons ---------------------------------------------------------
        JButton ETButton = new JButton("Ear Training");
        ETButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        ETButton.setBounds(45, 160, 165, 30);
        ETButton.setBackground(new Color(0xFFE0E9));
        ETButton.setForeground(new Color(0x3E2E32));
        ETButton.setFocusable(false);
        ETButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        ETButton.addActionListener(e -> {
            this.dispose();
            new EarTraining();
        });

        JButton RCButton = new JButton("Rhythm Creator");
        RCButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 18)); //45-405
        RCButton.setBounds(240, 160, 165, 30);
        RCButton.setBackground(new Color(0xFFE0E9));
        RCButton.setForeground(new Color(0x3E2E32));
        RCButton.setFocusable(false);
        RCButton.setBorder(new BevelBorder(BevelBorder.RAISED));
        RCButton.addActionListener(e -> {
            this.dispose();
            new RhythmCreator();
        });

        // adding my components onto my jframe (makeshift joptionpane)
        this.add(welcomeMessage);
        this.add(welcomeMessage2);
        this.add(ETButton);
        this.add(RCButton);

        this.setVisible(true);
    }

}
