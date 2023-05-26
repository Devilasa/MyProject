package main;

import entity.Entity;
import tile.TileManager;
import tile.TileManagerMenu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MenuPanel extends JPanel implements Runnable{
    public final int originalTileSize = 16; // 16x16 tile
    public final int scale = 4;
    public final int tileSize = originalTileSize * scale; // 64x64 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels
    public final int FPS = 120;
    public final int tileSpeed = 1;

    TileManagerMenu tileManager1 = new TileManagerMenu(this);
    TileManagerMenu tileManager2 = new TileManagerMenu(this);

    KeyHandler keyHandler = new KeyHandler();

    Sound sound = new Sound();


    public Thread menuThread;
    public MenuPanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        tileManager2.y = -screenHeight;
    }

    public void startMenuThread(){
        playMusic();
        menuThread = new Thread(this);
        menuThread.start();
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS; // 1 second = 1000000000 nanosecond. It represents how many times per second you want the event (refresh) to happen.
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(menuThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                //System.out.println("FPS:"+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;


        if(tileManager1.y < screenHeight) {
            tileManager2.y += tileSpeed;
            tileManager1.y += tileSpeed;
        } else {

            tileManager1.mapTileNumber = tileManager2.mapTileNumber;
            tileManager2.loadMap();

            tileManager2.y = -screenHeight;
            tileManager1.y = 0;
        }

        tileManager1.draw(graphics2D);
        tileManager2.draw(graphics2D);


        graphics2D.dispose();
    }
    public void playMusic(){
        sound.setFile();
        sound.play();
        sound.loop();
    }
    public void stopMusic(){
        sound.stop();
    }
}
