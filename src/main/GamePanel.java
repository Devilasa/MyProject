package main;

import entity.Spaceship;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel  implements Runnable {
    //SCREEN SETTINGS
    public final int originalTileSize = 16; // 16x16 tile
    public final int scale = 4;
    public final int tileSize = originalTileSize * scale; // 64x64 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels

    //FPS
    public final int FPS = 120;

    static int counter = 0;

    TileManager tileManager1 = new TileManager(this);
    TileManager tileManager2 = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Spaceship spaceship = new Spaceship(this, keyHandler);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        tileManager1.y = -screenHeight;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS; // 1 second = 1000000000 nanosecond. It represents how many times per second you want the event (refresh) to happen.
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS:"+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){

        spaceship.update();

    }
    public void paintComponent(Graphics graphics) {
        counter++;
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;


        if(tileManager1.y < 0) {
            tileManager1.y += 1;
            tileManager2.y += 1;
        }
        else {
            tileManager2.mapTileNumber = tileManager1.mapTileNumber;
            tileManager1.loadMap();
            tileManager1.y = -screenHeight;
            tileManager2.y = 0;
        }



        if(counter >= tileManager1.tilesVelocity) {
            //tileManager.updateMap();


            counter = 0;
        }
        tileManager1.draw(graphics2D);
        tileManager2.draw(graphics2D);
        spaceship.draw(graphics2D);
        graphics2D.dispose();

    }

}