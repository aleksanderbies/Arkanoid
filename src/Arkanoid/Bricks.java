package Arkanoid;

import java.awt.*;
import java.util.Random;

public class Bricks {
    public int[][] bricks;
    public Color[][] bricksColors;
    public int brickWidth = 45;
    public int brickHeight = 20;

    public Bricks(int row, int col) {
        bricks = new int[row][col];
        bricksColors = new Color[row][col];

        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                bricks[i][j] = 1;
                bricksColors[i][j] = randomColor();
            }
        }
    }

    public void drawBricks(Graphics2D g2d) {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j] > 0) {
                    g2d.setColor(bricksColors[i][j]);
                    g2d.fillRect(j * brickWidth + 32 + 10 * j, i * brickHeight + 80 + 10 * i, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setNewValueToBrick(int value, int row, int col) {
        bricks[row][col] = value;
    }

    public Color randomColor() {
        Random random = new Random();
        int number = random.nextInt((6 + 1));
        Color color;
        switch (number) {
            case 0:
                color = Color.red;
                break;
            case 1:
                color = Color.green;
                break;
            case 2:
                color = Color.orange;
                break;
            case 3:
                color = Color.yellow;
                break;
            case 4:
                color = Color.magenta;
                break;
            case 5:
                color = Color.cyan;
                break;
            case 6:
                color = new Color(255, 111, 0);
                break;
            default:
                color = Color.black;
                break;
        }
        return color;
    }
}
