package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class Asteroid extends Entity{

    GamePanel gamePanel;
    int RESPAWN_COUNTER_TARGET;
    int respawnCounter = 0;
    int collisionCounter = 0;
    public Asteroid(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setDefaultValue(){
    }

    public void respawn(){
    }


    public void getAsteroidImage() {
    }

    public void draw(Graphics2D graphics2D){

        BufferedImage image = null;
        if(gamePanel.displayGameOver.scale >= 380){
            direction = "explosion";
        }

        switch (direction){
            case "up" -> image = up;
            case "right" -> image = right;
            case "down" -> image = down;
            case "left" -> image = left;
            case "explosion" -> image = bonus;
        }

        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);


    }
}
