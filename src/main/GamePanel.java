package main;

import entity.Player;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tiles
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tiles
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxScreenRow;

    // FPS
    int fps = 60; // number of update and repaint per second

    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // game thread for game loop
    public Player player = new Player(this,keyH);
    TileManager tileM = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight)); // size of window 768x576
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // increase rendering performance
        this.addKeyListener(keyH); // JPanel can recognize key inputs
        this.setFocusable(true); // game panel can be "focused" to receive key inputs
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // executed when game thread is started
    @Override
    public void run() {
        // will limit repaint rate to number of fps instead of doing it as fast as the processor can
        double drawInterval = 1000000000/fps; // 1 sec (in nanosec) divide by fps : 0.016666 sec
        double delta = 0;
        long lastTime = System.nanoTime(); // get processor time in nanoseconds
        long currentTime;
        // used for FPS display
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime  = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval; // adds to delta a fraction of the draw interval
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1) { // if accumulated time is bigger than draw interval
                // update information such as entities positions
                update();
                // draw the screen with updated information
                repaint();
                delta--;
                drawCount++;
            }

            // display FPS i.e. once 1 s is passed display number of times the panel was repainted
            if(timer >= 1000000000) {
                System.out.println("FPS : "+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // more functionalities than Graphics
        tileM.draw(g2); // tiles before player so that player is displayed on top of the tiles
        player.draw(g2);
        g2.dispose(); // dispose of Graphics to free memory
    }
}
