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

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        // player position on the screen, here basically always at the center
        screenX = (gp.screenWidth-gp.tileSize) / 2;
        screenY = (gp.screenHeight-gp.tileSize) / 2;

        // hitbox x & y are chosen with the *3 scale in mind
        hitbox = new Rectangle(12,21,27,24);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        // player start position in the whole map
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
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
        // set which direction the player will be looking according to key press
        if(keyH.upPressed || keyH.leftPressed || keyH.downPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";
            }
            else if(keyH.leftPressed) {
                direction = "left";
            }
            else if(keyH.downPressed) {
                direction = "down";
            }
            else if(keyH.rightPressed) {
                direction = "right";
            }

            // check tile collision
            collisionOn = false;
            gp.cManager.checkTile(this);

            // if no collision, player can move
            if (collisionOn == false) {
                switch(direction) {
                    case "up":
                        worldY -= speed;  // y increases while going down
                        break;
                    case "left":
                        worldX -= speed; // x increases while going right
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // draw image at (x,y) with size tileSize, image observer at null
    }
}
