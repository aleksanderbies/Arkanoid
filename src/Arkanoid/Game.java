package Arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Game extends JPanel implements MouseMotionListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private Timer timer;
    private int delay = 8;
    private int sliderPosition = 250;
    private int ballPositionX = 300, ballPositonY = 540, ballDirectionX = -1, ballDirectionY = -2 ;
    Image background = Toolkit.getDefaultToolkit().getImage("images/bg.png");
    public Game(){
        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //background image
        g.drawImage(background,0,0,this);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(10,60, 3, 705);
        g.fillRect(10,60, 580, 3);
        g.fillRect(590,60, 3, 705);
        g.fillRect(10,765, 583, 3);

        //slider
        g.setColor(Color.RED);
        g.fillRect(sliderPosition, 740, 100, 12 );

        //ball
        g.setColor(Color.white);
        g.fillOval(ballPositionX, ballPositonY, 20, 20);

        //score print
        g.setColor(Color.yellow);
        g.setFont(new Font("Times New Roman", Font.BOLD, 35));
        g.drawString("Score: "+String.valueOf(score), 430, 40);
        g.dispose();

    }
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        sliderPosition = e.getX() - 50;
        if(sliderPosition<13){
            sliderPosition = 13;
        }
        else if (sliderPosition+100>590) {
            sliderPosition=490;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }
}
