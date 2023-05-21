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


    public Asteroid(GamePanel gamePanel, int AsteroidType) {
        this.gamePanel = gamePanel;
        this.AsteroidType = AsteroidType;
        setDefaultValue();
        getAsteroidImage();
    }

    public void setDefaultValue(){
        Random rng = new Random();
        x = rng.nextInt(0, gamePanel.screenWidth);
        y = 0;
        speed = 2;
        direction = "up";
    }


    public void getAsteroidImage() {
        switch (AsteroidType) {
            case 1 -> {
                try {
                    up1 = ImageIO.read(new File("res/asteroid/asteroide_grigio_1.png"));
                    down1 = ImageIO.read(new File("res/asteroid/asteroide_grigio_2.png"));
                    left1 = ImageIO.read(new File("res/asteroid/asteroide_grigio_3.png"));
                    right1 = ImageIO.read(new File("res/asteroid/asteroide_grigio_4.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case 2 -> {
                try {
                    up1 = ImageIO.read(new File("res/asteroid/asteroide_rosso_1.png"));
                    right1 = ImageIO.read(new File("res/asteroid/asteroide_rosso_2.png"));
                    down1 = ImageIO.read(new File("res/asteroid/asteroide_rosso_3.png"));
                    left1 = ImageIO.read(new File("res/asteroid/asteroide_rosso_4.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case 3 -> {
                try {
                    up1 = ImageIO.read(new File("res/asteroid/asteroide_infuocato_1.png"));
                    right1 = ImageIO.read(new File("res/asteroid/asteroide_infuocato_2.png"));
                    down1 = ImageIO.read(new File("res/asteroid/asteroide_infuocato_3.png"));
                    left1 = ImageIO.read(new File("res/asteroid/asteroide_infuocato_4.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void update(){
        if(y > gamePanel.screenHeight) y = 0;
        y += speed;
        if(x > gamePanel.screenWidth){
            Random rng = new Random();
            x = rng.nextInt(0, gamePanel.screenWidth);
            y = 0;
        }
        x += speed;

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
        }
        graphics2D.drawImage(image, x, y, gamePanel.tileSize / 2 , gamePanel.tileSize, null);
    }
}
