package main;

import entity.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel  implements Runnable {
    //SCREEN SETTINGS
    public final int originalTileSize = 16; // 16x16 tile
    public final int scale = 4;
    public final int tileSize = originalTileSize * scale; // 64x64 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels
    public final int SCREEN_SHIFT_X = 32; // for some reasons the actual screen get shifted of a little value near 32 pixels, this refers to the X coordinate (ScreenWidth)
    public final int SCREEN_SHIFT_Y = 56;
    public final int tileSpeed = 1;
    public final int FPS = 120;

    TileManager tileManager1 = new TileManager(this);
    TileManager tileManager2 = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    public Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public ArrayList<Entity> entitiesList = new ArrayList<>();
    public Spaceship spaceship = new Spaceship(this, keyHandler);
    public Asteroid blueAsteroid1 = new BlueAsteroid(this);
    public Asteroid topAsteroid1 = new TopAsteroid(this);
    public Asteroid topRightAsteroid1 = new TopRightAsteroid(this);
    public GameOverDisplay displayGameOver = new GameOverDisplay(this);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        tileManager2.y = -screenHeight;

        entitiesList.add(spaceship);
        entitiesList.add(blueAsteroid1);
        entitiesList.add(topAsteroid1);
        entitiesList.add(topRightAsteroid1);

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
                //System.out.println("FPS:"+drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){

       for(Entity entity : entitiesList){
           entity.update();
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

        for(Entity entity : entitiesList){
            entity.draw(graphics2D);
        }
        if(spaceship.direction.equals("death")) displayGameOver.draw(graphics2D);
        graphics2D.dispose();
    }

}