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
    private int totalBricks = 50;
    private Bricks bricks;
    private Timer timer;
    private int delay = 8;
    private int sliderPosition = 250;
    private int ballPositionX = 300, ballPositionY = 540, ballDirectionX = -1, ballDirectionY = -2 ;
    Image background = Toolkit.getDefaultToolkit().getImage("images/bg.png");
    Image title = Toolkit.getDefaultToolkit().getImage("images/title.png");

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
        g.fillRect(sliderPosition, 740, 100, 12 );

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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        g.setFont(scoreFont);
        g.drawString("Score: " + score, 390, 50);
        g.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        play = true;
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
        if (play==true){
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
                            totalBricks--;

                            if (Color.red.equals(bricks.bricksColors[i][j])) score += 2;
                            else if (Color.green.equals(bricks.bricksColors[i][j])) score += 3;
                            else if (Color.orange.equals(bricks.bricksColors[i][j])) score += 5;
                            else if (Color.yellow.equals(bricks.bricksColors[i][j])) score += 10;
                            else if (Color.magenta.equals(bricks.bricksColors[i][j])) score += 15;
                            else if (Color.cyan.equals(bricks.bricksColors[i][j])) score += 25;

                            if (ballPositionX + 19 <= brick.x || ballPositionX + 1 >= brick.x + brick.width) {
                                ballDirectionX = -ballDirectionX;
                            } else {
                                ballDirectionY = -ballDirectionY;
                            }

                            break Collision;
                        }
                    }
                }
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
            if(ballPositionY>765){
                play = false;
            }
        }
        repaint();
    }
}
