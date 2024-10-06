package entity;

import main.KeyHandler;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/monkey_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/monkey_up_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/monkey_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/monkey_left_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/monkey_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/monkey_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/monkey_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/monkey_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() { // in game loop so executed 60 times per second
        if(keyH.upPressed || keyH.leftPressed || keyH.downPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";
                y -= speed;  // y increases while going down
            }
            else if(keyH.leftPressed) {
                direction = "left";
                x -= speed; // x increases while going right
            }
            else if(keyH.downPressed) {
                direction = "down";
                y += speed;
            }
            else if(keyH.rightPressed) {
                direction = "right";
                x += speed;
            }
            // sprite animation
            spriteCounter++;
            if(spriteCounter > 10) { // changes sprite every 10 frames or 6 times per second
                if(spriteNumber == 1) {
                    spriteNumber = 2;
                }
                else if(spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch(direction) { // draw player character sprite depending on moving direction
            case "up":
                if(spriteNumber == 1) {
                    image = up1;
                }
                if(spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "left":
                if(spriteNumber == 1) {
                    image = left1;
                }
                if(spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "down":
                if(spriteNumber == 1) {
                    image = down1;
                }
                if(spriteNumber == 2) {
                    image = down2;
                }
                break;
            case "right":
                if(spriteNumber == 1) {
                    image = right1;
                }
                if(spriteNumber == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null); // draw image at (x,y) with size tileSize, image observer at null
    }
}
