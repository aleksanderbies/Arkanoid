package Arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

public class Game extends JPanel implements MouseMotionListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    //CONST OF GAME STATUS
    private static final int BEFORE_GAME_STATE = 1;
    private static final int IN_GAME_STATE = 2;
    private static final int LOSE_GAME_STATE = 3;
    private static final int WIN_GAME_STATE = 4;

    //GAME STATEUS VAR
    private int gameStatus = BEFORE_GAME_STATE;

    private Timer timer;
    private int delay = 8;
    private int sliderPosition = 250;
    private int ballPositionX = 300, ballPositionY = 540, ballDirectionX = -1, ballDirectionY = -2 ;
    Image background = Toolkit.getDefaultToolkit().getImage("images/bg.png");
    Image title = Toolkit.getDefaultToolkit().getImage("images/title.png");
    Image mouseMove = Toolkit.getDefaultToolkit().getImage("images/move_mouse.png");
    Image loseGame = Toolkit.getDefaultToolkit().getImage("images/loser.png");
    Image winGame = Toolkit.getDefaultToolkit().getImage("images/winner.png");

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
        g.fillRect(sliderPosition, 730, 100, 12 );

        //ball
        g.setColor(Color.white);
        g.fillOval(ballPositionX, ballPositionY, 20, 20);

        // title draw
        g.drawImage(title, 5, 5, this);

        //score print
        g.setColor(Color.yellow);
        Font scoreFont = null;
        try {
            scoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/PEPSI_pl.ttf")).deriveFont(35f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(scoreFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        g.setFont(scoreFont);
        g.drawString("Score: " + score, 420, 50);

        switch (gameStatus){
            case BEFORE_GAME_STATE:
                g.drawImage(mouseMove, 100, 300, this);
                break;
            case IN_GAME_STATE:
                break;
            case LOSE_GAME_STATE:
                g.drawImage(loseGame, 180, 300, this);
                break;
            case WIN_GAME_STATE:
                g.drawImage(winGame, 180, 300, this);
                break;
        }



        //slider
        g.setColor(Color.RED);
        g.fillRect(sliderPosition, 730, 100, 12 );

        //ball
        g.setColor(Color.white);
        g.fillOval(ballPositionX, ballPositionY, 20, 20);


        g.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        play = true;
        if (gameStatus!=LOSE_GAME_STATE && gameStatus!=WIN_GAME_STATE){
        gameStatus = IN_GAME_STATE;
        }

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
        if (play==true){
            timer.start();
            if(new Rectangle(ballPositionX, ballPositionY, 20, 20). intersects(new Rectangle(sliderPosition,740, 100, 15))){
                ballDirectionY *= (-1);
            }
            ballPositionX += ballDirectionX;
            ballPositionY += ballDirectionY;
            if(ballPositionX < 13){
                ballDirectionX *= (-1);
            }
            if(ballPositionY < 63){
                ballDirectionY *= (-1);
            }
            if(ballPositionX > 570){
                ballDirectionX *= (-1);
            }
            if(ballPositionY>745) {
                ballDirectionY = 0;
                ballDirectionX = 0;
                ballPositionY = 745;
                gameStatus = LOSE_GAME_STATE;
                play = false;
            }
            if (gameStatus == WIN_GAME_STATE){
                ballDirectionX = 0;
                ballDirectionY = 0;
                play = false;
            }
        }
        repaint();
    }
}
