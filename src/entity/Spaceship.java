package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Spaceship extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Spaceship(GamePanel gamePanel, KeyHandler keyHandler){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 1;
        direction = "up";
    }

    public void getPlayerImage(){
        try {
            standing = ImageIO.read(new File("res/spaceship/nave_spaziale_ferma.png"));
            up1 = ImageIO.read(new File("res/spaceship/nave_spaziale_accesa_2.png"));
            up2 = ImageIO.read(new File("res/spaceship/nave_spaziale_accesa_2.png"));
            down1 = ImageIO.read(new File("res/spaceship/nave_spaziale_alto_2.png"));
            down2 = ImageIO.read(new File("res/spaceship/nave_spaziale_alto_2.png"));
            left1 = ImageIO.read(new File("res/spaceship/nave_spaziale_left_2.png"));
            left2 = ImageIO.read(new File("res/spaceship/nave_spaziale_left_2.png"));
            right1 = ImageIO.read(new File("res/spaceship/nave_spaziale_right_2.png"));
            right2 = ImageIO.read(new File("res/spaceship/nave_spaziale_right_2.png"));

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyHandler.upPressed){
            direction = "up";
            y -= speed;
        } else if (keyHandler.downPressed) {
            direction = "down";
            y += speed;
        } else if (keyHandler.leftPressed){
            direction = "left";
            x -= speed;
        } else if (keyHandler.rightPressed){
            direction = "right";
            x += speed;
        } else {
            direction = "standing";
        }

        spriteCounter++;
        if(spriteCounter > 10){
            if(spriteNumber == 1){
                spriteNumber = 2;
            } else {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D graphics2D){
        // graphics2D.setColor(Color.white);
        // graphics2D.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);

        BufferedImage image = null;

        switch(direction){
            case "standing" -> image = standing;
            case "up" -> {
                if(spriteNumber == 1){
                    image = up1;
                }
                if (spriteNumber == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if(spriteNumber == 1){
                    image = down1;
                }
                if (spriteNumber == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if(spriteNumber == 1){
                    image = left1;
                }
                if (spriteNumber == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if(spriteNumber == 1){
                    image = right1;
                }
                if (spriteNumber == 2) {
                    image = right2;
                }
            }
        }
        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}