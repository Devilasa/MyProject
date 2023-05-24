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
    static final int DEATH_ANIMATION_SPEED_MULTIPLIER = 80;


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
            standing = ImageIO.read(new File("res/spaceship/spaceship_standing.png"));
            up = ImageIO.read(new File("res/spaceship/spaceship_up.png"));
            down = ImageIO.read(new File("res/spaceship/spaceship_down.png"));
            left = ImageIO.read(new File("res/spaceship/spaceship_left.png"));
            right = ImageIO.read(new File("res/spaceship/spaceship_right.png"));
            bonus = ImageIO.read(new File("res/spaceship/spaceship_explosion.png"));

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getFinalPlayerImage(){
        try {
            up = ImageIO.read(new File("res/spaceship/spaceship_broken_1.png"));
            down = ImageIO.read(new File("res/spaceship/spaceship_broken_2.png"));
            left = ImageIO.read(new File("res/spaceship/spaceship_broken_3.png"));
            right = ImageIO.read(new File("res/spaceship/spaceship_broken_4.png"));
            standing = ImageIO.read(new File("res/spaceship/spaceship_broken_5.png"));

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(spriteCounter > 0) {
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER) {
                direction = "down";
            }
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER * 2) {
                direction = "explosion";
            }
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER * 3) {
                direction = "left";
                x -= 32;
                y -= 32;
            }
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER * 4) {
                direction = "right";
            }
            if(spriteCounter == DEATH_ANIMATION_SPEED_MULTIPLIER * 5) {
                direction = "standing";
                x -= 64;
                y -= 64;
                spriteCounter = 1;
            }
            if(!direction.equals("standing")){
                spriteCounter++;
            }
        } else {
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
        if(y > gamePanel.screenHeight - gamePanel.SCREEN_SHIFT_Y) {
            y = gamePanel.screenHeight - gamePanel.SCREEN_SHIFT_Y;
        } else if(y < 0){
            y = 0;
        }

        updateSolidArea();
        collision = false;
        gamePanel.collisionChecker.checkCollision(this);
        if(collision){
            if(spriteCounter == 0) {
                direction = "up";
                getFinalPlayerImage();
                spriteCounter++;
            }

            System.out.println("COLLISION!");
        }

    }

    public void draw(Graphics2D graphics2D){

        BufferedImage image = null;

        switch(direction){
            case "standing" -> image = standing;
            case "up" -> image = up;
            case "down" -> image = down;
            case "left" -> image = left;
            case "right" -> image = right;
            case "explosion" -> image = bonus;
        }

        graphics2D.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
    }
}