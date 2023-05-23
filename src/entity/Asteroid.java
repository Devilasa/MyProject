package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

abstract public class Asteroid extends Entity{

    GamePanel gamePanel;
    int respawnCounter = 0;
    static int collisionCounter = 0;
    public Asteroid(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setDefaultValue(){
    }

    public void respawn(){
    }


    public void getAsteroidImage() {
    }

    public void update(){
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
