package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        x = 100; // declared in super class
        y = 100;
        speed = 4;
        direction = "up";
    }

    public void getPlayerImage(){
        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("res/player/Space Shuttle 32x32.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("res/player/Space Shuttle 32x32.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("res/player/nave_cactus_32x32.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("res/player/nave_cactus_32x32.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("res/player/Space Shuttle 32x32.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("res/player/Space Shuttle 32x32.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("res/player/Space Shuttle 32x32.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("res/player/Space Shuttle 32x32.png"));

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
        }
    }

    public void draw(Graphics2D graphics2D){
        // graphics2D.setColor(Color.white);
        // graphics2D.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);

        BufferedImage image = null;

        switch(direction){
            case "up" -> image = up1;
            case "down" -> image = down1;
            case "left" -> image = left1;
            case "right" -> image = right1;
        }
        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}