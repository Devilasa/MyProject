package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
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
        speed = 1;
        direction = "up";
    }

    public void getPlayerImage(){
        try {

            up1 = ImageIO.read(new File("res/player/nave_spaziale_spenta(2).png")); //standing
            up2 = ImageIO.read(new File("res/player/nave_spaziale_accesa.png"));
            down1 = ImageIO.read(new File("res/player/nave_spaziale_spenta.png"));
            down2 = ImageIO.read(new File("res/player/Space Shuttle 32x32.png"));
            left1 = ImageIO.read(new File("res/player/nave_spaziale_right(2).png"));
            left2 = ImageIO.read(new File("res/player/Space Shuttle 32x32.png"));
            right1 = ImageIO.read(new File("res/player/nave_spaziale_left.png"));
            right2 = ImageIO.read(new File("res/player/Space Shuttle 32x32.png"));

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
    }

    public void draw(Graphics2D graphics2D){
        // graphics2D.setColor(Color.white);
        // graphics2D.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);

        BufferedImage image = null;

        switch(direction){
            case "standing" -> image = up1;
            case "up" -> image = up2;
            case "down" -> image = down1;
            case "left" -> image = left1;
            case "right" -> image = right1;
        }
        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}