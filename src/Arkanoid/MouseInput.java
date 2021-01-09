package Arkanoid;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseX >= 0 && mouseX <= 600 && mouseY >= 0 && mouseY <= 800 && !Game.play) {
            Game.gameStatus = Game.IN_GAME_STATE;
            Game.play = true;
            Game.ballPositionX = 300;
            Game.ballPositionY = 540;
            Game.ballDirectionX = -1;
            Game.ballDirectionY = -2;
            Game.totalBricks = 50;
            Game.score = 0;
            Game.bricks = new Bricks(5, 10);
            Game.timer = new Timer(Game.delay, (ActionListener) this);
            Game.timer.start();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
