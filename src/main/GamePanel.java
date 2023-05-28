package main;

import entity.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
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
    public final int NEW_ENTITY_JOINS_GAME = 200;
    public int newEntityCounter = 0;
    public Integer score = 0;
    public static String panel = "dashboard";
    public static boolean retry = false;

    static final Rectangle dashboardPlayButton = new Rectangle(831, 257, 179, 48);
    static final Rectangle leaderboardBackButton = new Rectangle(855, 700, 139, 33);
    static final Rectangle exitButton = new Rectangle(375, 632, 238, 71);
    static final Rectangle dashboardLeaderboardButton = new Rectangle(179, 482, 743, 71);
    static final Rectangle tryAgainButton = new Rectangle(31, 414, 485, 48);
    static final Rectangle gameOverBackButton = new Rectangle(665, 409, 263, 58);
    TileManager tileManager1 = new TileManager(this);
    TileManager tileManager2 = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();
    public Thread gameThread;

    Sound sound = new Sound();

    public static boolean switchSong = false;

    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Rectangle scoreDisplay = new Rectangle(screenWidth / 2 - SCREEN_SHIFT_X, SCREEN_SHIFT_Y, 100, 50);
    public ArrayList<Entity> entitiesList = new ArrayList<>();
    public ArrayList<Entity> inGameEntitiesList = new ArrayList<>();
    public Spaceship spaceship = new Spaceship(this, keyHandler);
    public AlienShip alienShip1 = new AlienShip(this);
    public AlienShip alienShip2 = new AlienShip(this);
    public AlienShip alienShip3 = new AlienShip(this);
    public AlienShip alienShip4 = new AlienShip(this);
    public AlienShip alienShip5 = new AlienShip(this);
    public AlienShip alienShip6 = new AlienShip(this);
    public Asteroid blueAsteroid1 = new BlueAsteroid(this);
    public Asteroid blueAsteroid2 = new BlueAsteroid(this);
    public Asteroid topAsteroid1 = new TopAsteroid(this);
    public Asteroid topAsteroid2 = new TopAsteroid(this);
    public Asteroid topAsteroid3 = new TopAsteroid(this);
    public Asteroid topAsteroid4 = new TopAsteroid(this);
    public Asteroid topRightAsteroid1 = new TopRightAsteroid(this);
    public Asteroid topRightAsteroid2 = new TopRightAsteroid(this);
    public Asteroid topRightAsteroid3 = new TopRightAsteroid(this);
    public Asteroid topRightAsteroid4 = new TopRightAsteroid(this);
    public FlyingKeyboard flyingKeyboard = new FlyingKeyboard(this);
    public GameOverDisplay displayGameOver = new GameOverDisplay(this);
    public TryAgainDisplay tryAgainDisplay = new TryAgainDisplay(this);
    public BackDisplay backDisplay = new BackDisplay(this);


    //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_video", "root", "Raviolo0_");
    //Statement statement = connection.createStatement();




    public GamePanel() throws SQLException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.addMouseListener(mouseHandler);

        tileManager2.y = -screenHeight;

        entitiesList.add(spaceship);

        entitiesList.add(topAsteroid1);
        entitiesList.add(topAsteroid2);
        entitiesList.add(topAsteroid3);
        entitiesList.add(topAsteroid4);

        entitiesList.add(blueAsteroid1);
        entitiesList.add(blueAsteroid2);

        entitiesList.add(topRightAsteroid1);
        entitiesList.add(topRightAsteroid2);
        entitiesList.add(topRightAsteroid3);
        entitiesList.add(topRightAsteroid4);

        entitiesList.add(alienShip1);
        entitiesList.add(alienShip2);
        entitiesList.add(alienShip3);
        entitiesList.add(alienShip4);
        entitiesList.add(alienShip5);
        entitiesList.add(alienShip6);

        entitiesList.add(flyingKeyboard);

        inGameEntitiesList.add(spaceship);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
        sound.playMusic();

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
        if (retry) {
            resetGame();
            retry = false;
        }
        if(switchSong){
            sound.switchSong();
            switchSong = false;
        }
        if (panel.equals("game")) {
            for(Entity entity : inGameEntitiesList) {
                entity.update();
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


        switch (panel) {
            case "gameover" -> {
                displayGameOver.draw(graphics2D);
                tryAgainDisplay.draw(graphics2D);
                backDisplay.draw(graphics2D);
                graphics2D.setColor(Color.white);
                graphics2D.setFont(new Font("Arial", Font.BOLD, 50));
                graphics2D.drawString(score.toString(), scoreDisplay.x, scoreDisplay.y);
                spaceship.setDefaultValues();
            }
            case "game" -> {
                for (Entity entity : inGameEntitiesList) {
                    entity.draw(graphics2D);
                }

                if (spaceship.direction.equals("death")) {
                    displayGameOver.draw(graphics2D);
                    tryAgainDisplay.draw(graphics2D);
                    backDisplay.draw(graphics2D);
                }

                graphics2D.setColor(Color.white);
                graphics2D.setFont(new Font("Arial", Font.BOLD, 50));
                graphics2D.drawString(score.toString(), scoreDisplay.x, scoreDisplay.y);

                if (spaceship.spriteCounter == 0) {
                    score++;
                    newEntityCounter++;
                }

                if (newEntityCounter == NEW_ENTITY_JOINS_GAME) {
                    if (inGameEntitiesList.size() < entitiesList.size()) {
                        inGameEntitiesList.add(entitiesList.get(inGameEntitiesList.size()));
                        newEntityCounter = 0;
                    }
                }

                if (displayGameOver.scale == 500) {
                    inGameEntitiesList = new ArrayList<>();

                    ++displayGameOver.scale;
                    ++tryAgainDisplay.scale;
                    ++backDisplay.scale;

                    mouseHandler.panelType = "gameover";
                }
            }
            case "dashboard" -> {
                Rectangle user = new Rectangle(screenWidth / 2 - 200 - SCREEN_SHIFT_X, SCREEN_SHIFT_Y + 50, 200, 100);
                graphics2D.setColor(Color.yellow);
                graphics2D.setFont(new Font("Algerian", Font.BOLD, 100));
                graphics2D.drawString("PLayer :", user.x, user.y);
                Rectangle leaderboard = new Rectangle(screenWidth / 2 - 300 - SCREEN_SHIFT_X, SCREEN_SHIFT_Y + 500, 200, 100);
                graphics2D.setColor(Color.yellow);
                graphics2D.setFont(new Font("Algerian", Font.BOLD, 100));
                graphics2D.drawString("Leaderboard", leaderboard.x, leaderboard.y);
                Rectangle name = new Rectangle(screenWidth / 2 - 100 - SCREEN_SHIFT_X, SCREEN_SHIFT_Y + 650, 200, 100);
                graphics2D.setColor(Color.yellow);
                graphics2D.setFont(new Font("Algerian", Font.BOLD, 100));
                graphics2D.drawString("Exit", name.x, name.y);
                if (true) {
                    Rectangle play = new Rectangle(screenWidth / 2 + 350 - SCREEN_SHIFT_X, SCREEN_SHIFT_Y + 250, 100, 50);
                    graphics2D.setColor(Color.yellow);
                    graphics2D.setFont(new Font("Algerian", Font.BOLD, 65));
                    graphics2D.drawString("Play", play.x, play.y);
                }
            }
            case "leaderboard" -> {
                try {
                    //ResultSet resultSet = statement.executeQuery("select * from videogame order by punteggio desc");
                    int counts = 1;
                    int dec = 0; // for some reasons the actual screen get shifted of a little value near 32 pixels, this refers to the X coordinate (ScreenWidth)
                    while (true) {
                        String usr = counts + ".";
                        String usr1 = "gatto"; //resultSet.getString("nome");
                        String usr2 = "ciao"; //resultSet.getString("punteggio");
                        Rectangle user = new Rectangle(screenWidth / 2 - 450 - SCREEN_SHIFT_X, SCREEN_SHIFT_Y + dec, 100, 50);
                        graphics2D.setColor(Color.yellow);
                        graphics2D.setFont(new Font("Algerian", Font.BOLD, 50));
                        graphics2D.drawString(usr, user.x, user.y);
                        Rectangle user1 = new Rectangle(screenWidth / 2 - 350 - SCREEN_SHIFT_X, SCREEN_SHIFT_Y + dec, 100, 50);
                        graphics2D.setColor(Color.yellow);
                        graphics2D.setFont(new Font("Algerian", Font.BOLD, 50));
                        graphics2D.drawString(usr1, user1.x, user1.y);
                        Rectangle user2 = new Rectangle(screenWidth / 2 + 100 - SCREEN_SHIFT_X, SCREEN_SHIFT_Y + dec, 100, 50);
                        graphics2D.setColor(Color.yellow);
                        graphics2D.setFont(new Font("Algerian", Font.BOLD, 50));
                        graphics2D.drawString(usr2, user2.x, user2.y);
                        dec += 100;
                        counts++;
                        if (counts == 8) {
                            break;
                        }
                    }
                    Rectangle quit = new Rectangle(screenWidth / 2 + 375 - SCREEN_SHIFT_X, SCREEN_SHIFT_Y + 680, 100, 50);
                    graphics2D.setColor(Color.yellow);
                    graphics2D.setFont(new Font("Algerian", Font.BOLD, 50));
                    graphics2D.drawString("Back", quit.x, quit.y);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        panel = mouseHandler.panelType;
        graphics2D.dispose();
    }

    public void resetGame(){
        score = 0;
        newEntityCounter = 0;
        displayGameOver.scale = 0;
        backDisplay.scale = 0;
        tryAgainDisplay.scale = 0;
        if(inGameEntitiesList.isEmpty()){
            inGameEntitiesList.add(spaceship);
        }
        for(Entity e : entitiesList){
            e.setDefaultValue();
        }
    }

}