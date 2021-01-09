package Arkanoid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Game extends JPanel implements MouseMotionListener, ActionListener {
    static boolean play = false;
    static int score = 0;
    static int totalBricks = 50;
    static Bricks bricks;

    //CONST OF GAME STATUS
    static final int BEFORE_GAME_STATE = 1;
    static final int IN_GAME_STATE = 2;
    static final int LOSE_GAME_STATE = 3;
    static final int WIN_GAME_STATE = 4;

    //GAME STATUS VAR
    static int gameStatus = BEFORE_GAME_STATE;

    static Timer timer;
    static int delay = 8;
    static int sliderPosition = 250;
    static int ballPositionX = 300, ballPositionY = 540, ballDirectionX = -1, ballDirectionY = -2 ;

    Image background = Toolkit.getDefaultToolkit().getImage("images/bg.png");
    Image title = Toolkit.getDefaultToolkit().getImage("images/title.png");
    Image mouseMove = Toolkit.getDefaultToolkit().getImage("images/move_mouse.png");
    Image loseGame = Toolkit.getDefaultToolkit().getImage("images/loser.png");
    Image winGame = Toolkit.getDefaultToolkit().getImage("images/winner.png");
    Image restart = Toolkit.getDefaultToolkit().getImage("images/restart.png");

    public Game(){
        bricks = new Bricks(5, 10);
        addMouseMotionListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        // background image
        g.drawImage(background,0,0,this);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(10,60, 3, 705);
        g.fillRect(10,60, 580, 3);
        g.fillRect(590,60, 3, 705);
        g.fillRect(10,765, 583, 3);

        // bricks
        bricks.drawBricks((Graphics2D) g);

        // slider
        g.setColor(Color.RED);
        g.fillRect(sliderPosition, 730, 100, 12 );

        // ball
        g.setColor(Color.white);
        g.fillOval(ballPositionX, ballPositionY, 20, 20);

        // title draw
        g.drawImage(title, 5, 5, this);

        // score print
        g.setColor(Color.yellow);
        Font scoreFont = null;
        try {
            scoreFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/PEPSI_pl.ttf")).deriveFont(35f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(scoreFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        g.setFont(scoreFont);
        g.drawString("Score: " + score, 380, 50);

        switch (gameStatus){
            case BEFORE_GAME_STATE:
                g.drawImage(mouseMove, 100, 300, this);
                break;
            case IN_GAME_STATE:
                break;
            case LOSE_GAME_STATE:
                g.drawImage(loseGame, 180, 300, this);
                g.drawImage(restart, 100, 355, this);
                play = false;
                break;
            case WIN_GAME_STATE:
                g.drawImage(winGame, 180, 300, this);
                g.drawImage(restart, 100, 355, this);
                play = false;
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

            Collision: for (int i = 0; i < bricks.bricks.length; i++) {
                for (int j = 0; j < bricks.bricks[0].length; j++) {
                    if (bricks.bricks[i][j] > 0) {
                        int brickX = j * bricks.brickWidth + 32 + 10 * j;
                        int brickY = i * bricks.brickHeight + 80 + 10 * i;

                        Rectangle brick = new Rectangle(brickX, brickY, bricks.brickWidth, bricks.brickHeight);
                        Rectangle ball = new Rectangle(ballPositionX, ballPositionY, 20, 20);

                        if (ball.intersects(brick)) {
                            bricks.setNewValueToBrick(0, i, j);

                            if (Color.red.equals(bricks.bricksColors[i][j])) score += 2;
                            else if (Color.green.equals(bricks.bricksColors[i][j])) score += 3;
                            else if (Color.orange.equals(bricks.bricksColors[i][j])) score += 5;
                            else if (Color.yellow.equals(bricks.bricksColors[i][j])) score += 10;
                            else if (Color.magenta.equals(bricks.bricksColors[i][j])) score += 15;
                            else if (Color.cyan.equals(bricks.bricksColors[i][j])) score += 25;

                            totalBricks--;
                            if (totalBricks <= 0) {
                                gameStatus = WIN_GAME_STATE;
                            }

                            if (ballPositionX + 19 <= brick.x || ballPositionX + 1 >= brick.x + brick.width) {
                                ballDirectionX = -ballDirectionX;
                            } else {
                                ballDirectionY = -ballDirectionY;
                            }

                            // *************************************************
                            // Speed up
                            if (totalBricks % 5 == 0) {
                                if (ballDirectionX >= 0) {
                                    ballDirectionX += 1;
                                } else {
                                    ballDirectionX -= 1;
                                }

                                if (ballDirectionY >= 0) {
                                    ballDirectionY += 1;
                                } else {
                                    ballDirectionY-= 1;
                                }
                            }
                            // *************************************************

                            break Collision;
                        }
                    }
                }
            }

            ballPositionX += ballDirectionX;
            ballPositionY += ballDirectionY;

            if (ballPositionX < 13) {
                ballDirectionX *= (-1);
            }

            if (ballPositionY < 63) {
                ballDirectionY *= (-1);
            }

            if (ballPositionX > 570) {
                ballDirectionX *= (-1);
            }

            if (ballPositionY > 745) {
                ballDirectionY = 0;
                ballDirectionX = 0;
                ballPositionY = 745;
                gameStatus = LOSE_GAME_STATE;
                play = false;
            }

            if (gameStatus == WIN_GAME_STATE) {
                ballDirectionX = 0;
                ballDirectionY = 0;
                play = false;
            }
        }
        repaint();
    }
}
