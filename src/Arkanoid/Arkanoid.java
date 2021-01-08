package Arkanoid;

import javax.swing.*;
import java.awt.*;

public class Arkanoid extends JFrame{
    private Game gamePlay;
    public Arkanoid(){
        super.setTitle("Arkanoid by Aleksander Bies & Adam Chylaszek");
        super.
        setSize(600, 800);
        gamePlay = new Game();
        add(gamePlay);
        //setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main (String[] args){
        Arkanoid game = new Arkanoid();

    }
}
