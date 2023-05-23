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

        TEXTURE_SHIFT_X = 15;
        TEXTURE_SHIFT_Y = 11;

        setDefaultValues();
        getPlayerImage();

        solidArea = new Rectangle(TEXTURE_SHIFT_X + x, TEXTURE_SHIFT_Y + y, 36, 38);

    }

    public void setDefaultValues(){
        x = 460;
        y = 380;
        speed = 4;
        direction = "standing";
    }

    public void getPlayerImage(){
        try {
            standing = ImageIO.read(new File("res/spaceship/nave_spaziale_ferma.png"));
            up1 = ImageIO.read(new File("res/spaceship/nave_spaziale_accesa_2.png"));
            down1 = ImageIO.read(new File("res/spaceship/nave_spaziale_alto_2.png"));
            left1 = ImageIO.read(new File("res/spaceship/nave_spaziale_left_2.png"));
            right1 = ImageIO.read(new File("res/spaceship/nave_spaziale_right_2.png"));
            bonus = ImageIO.read(new File("res/spaceship/esplosione_nave.png"));

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(!direction.equals("explosion")) {
            if (keyHandler.upPressed) {
                direction = "up";
                y -= speed;
            } else if (keyHandler.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyHandler.leftPressed) {
                direction = "left";
                x -= speed;
            } else if (keyHandler.rightPressed) {
                direction = "right";
                x += speed;
            } else {
                direction = "standing";
            }
        }

        if (x > gamePanel.screenWidth - gamePanel.SCREEN_SHIFT_X) {
            x = -gamePanel.SCREEN_SHIFT_X;
        } else if(x < -gamePanel.SCREEN_SHIFT_X){
            x = gamePanel.screenWidth - gamePanel.SCREEN_SHIFT_X;
        }
        if(y > gamePanel.screenHeight - gamePanel.SCREEN_SHIFT_Y){
            y = gamePanel.screenHeight - gamePanel.SCREEN_SHIFT_Y;
        } else if(y < 0){
            y = 0;
        }

        updateSolidArea();
        collision = false;
        gamePanel.collisionChecker.checkCollision(this);
        if(collision){
            direction = "explosion";
           // gamePanel.gameThread = null;

            System.out.println("COLLISION!");
        }

    }

    public void draw(Graphics2D graphics2D){

        BufferedImage image = null;

        switch(direction){
            case "standing" -> image = standing;
            case "up" -> image = up1;
            case "down" -> image = down1;
            case "left" -> image = left1;
            case "right" -> image = right1;
            case "explosion" -> image = bonus;
        }
        graphics2D.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}