package Arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class Arkanoid extends JFrame{
    private Game gamePlay;
    public Arkanoid(){
        super.setTitle("Arkanoid by Aleksander Bies & Adam Chylaszek");
        super.
        setSize(600, 800);
        gamePlay = new Game();
        gamePlay.addMouseListener(new MouseInput());
        add(gamePlay);
        //setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main (String[] args){
        Arkanoid game = new Arkanoid();

    }
}
