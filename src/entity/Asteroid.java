package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Asteroid extends Entity{

    GamePanel gamePanel;

    int AsteroidType;
    static int collisionCounter = 0;
    public Asteroid(GamePanel gamePanel, int AsteroidType) {
        this.gamePanel = gamePanel;
        this.AsteroidType = AsteroidType;
        TEXTURE_SHIFT_X = 4;
        TEXTURE_SHIFT_Y = 3;

        setDefaultValue();
        solidArea = new Rectangle( x + TEXTURE_SHIFT_X, y + TEXTURE_SHIFT_Y, 26, 26);

        getAsteroidImage();
    }

    public void setDefaultValue(){
        Random rng = new Random();
        x = rng.nextInt(0, gamePanel.screenWidth);
        y = -30;
        speed = 2;
        direction = "up";
    }

    public void respawn(){
        Random rng = new Random();
        if(AsteroidType == 3) {
            x = rng.nextInt(0, gamePanel.screenWidth / 2);
        } else if (AsteroidType == 2) {
            x = rng.nextInt(gamePanel.screenWidth / 2, gamePanel.screenWidth);
        } else {
            x = rng.nextInt(0, gamePanel.screenWidth);
        }
        y = -30;
        updateSolidArea();
    }


    public void getAsteroidImage() {
        switch (AsteroidType) {
            case 1 -> {
                try {
                    up1 = ImageIO.read(new File("res/asteroid/asteroide_fuoco_sopra1.png"));
                    down1 = ImageIO.read(new File("res/asteroid/asteroide_fuoco_sopra2.png"));
                    left1 = ImageIO.read(new File("res/asteroid/asteroide_fuoco_sopra3.png"));
                    right1 = ImageIO.read(new File("res/asteroid/asteroide_fuoco_sopra4.png"));
                    bonus = ImageIO.read(new File("res/asteroid/esplosione.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case 2 -> {
                try {
                    up1 = ImageIO.read(new File("res/asteroid/asteroide_fuoco1.png"));
                    right1 = ImageIO.read(new File("res/asteroid/asteroide_fuoco2.png"));
                    down1 = ImageIO.read(new File("res/asteroid/asteroide_fuoco3.png"));
                    left1 = ImageIO.read(new File("res/asteroid/asteroide_fuoco4.png"));
                    bonus = ImageIO.read(new File("res/asteroid/esplosione.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case 3 -> {
                try {
                    up1 = ImageIO.read(new File("res/asteroid/asteroide_blu1.png"));
                    right1 = ImageIO.read(new File("res/asteroid/asteroide_blu2.png"));
                    down1 = ImageIO.read(new File("res/asteroid/asteroide_blu3.png"));
                    left1 = ImageIO.read(new File("res/asteroid/asteroide_blu2.png"));
                    bonus = ImageIO.read(new File("res/asteroid/esplosione.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void update(){
        if(direction.equals("explosion")) {
           solidArea.y = -100;
           x += 1;
           y += 1;
        } else {
            if(AsteroidType == 3) {
                x += speed + 1;
            } else if(AsteroidType == 2){
                x -= speed;
            }
            y += speed;
            if (x > gamePanel.screenWidth*2) {
                respawn();
            }
            if (y > gamePanel.screenHeight*2) {
                respawn();
            }
            updateSolidArea();
        }

        gamePanel.collisionChecker.checkCollision(this);
        if(collision){
            direction = "explosion";
            if(collisionCounter == 60){
                collisionCounter = 0;
                collision = false;
                direction = "up";
                respawn();
            } else {
                collisionCounter++;
                System.out.println("SCOPPIO!");
            }
        }



        spriteCounter++;
        if(spriteCounter>10) {
            switch (direction) {
                case "up" -> direction = "right";
                case "right" -> direction = "down";
                case "down" -> direction = "left";
                case "left" -> direction = "up";
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D graphics2D){

        BufferedImage image = null;

        switch (direction){
            case "up" -> image = up1;
            case "right" -> image = right1;
            case "down" -> image = down1;
            case "left" -> image = left1;
            case "explosion" -> image = bonus;
        }

        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);


    }
}
