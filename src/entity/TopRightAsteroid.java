package entity;


import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TopRightAsteroid extends Asteroid{
    public TopRightAsteroid (GamePanel gamePanel){
        super(gamePanel);

        TEXTURE_SHIFT_X = 12;
        TEXTURE_SHIFT_Y = 30;

        RESPAWN_COUNTER_TARGET = 6;

        setDefaultValue();
        getAsteroidImage();

        solidArea = new Rectangle( x + TEXTURE_SHIFT_X, y + TEXTURE_SHIFT_Y, 30, 29);

    }

    @Override
    public void setDefaultValue() {
        Random rng = new Random();
        x = rng.nextInt(gamePanel.screenWidth / 2, gamePanel.screenWidth);
        y = -50;
        speed = 2;
        direction = "up";
        respawnCounter = 0;
        collisionCounter = 0;
    }

    @Override
    public void respawn() {
        Random rng = new Random();
        x = rng.nextInt(gamePanel.screenWidth / 2, gamePanel.screenWidth + 40);
        y = -50;
        updateSolidArea();
        ++respawnCounter;
    }

    @Override
    public void getAsteroidImage() {
        try {
            up = ImageIO.read(new File("res/asteroid/asteroid_top_right_1.png"));
            right = ImageIO.read(new File("res/asteroid/asteroid_top_right_2.png"));
            down = ImageIO.read(new File("res/asteroid/asteroid_top_right_3.png"));
            left = ImageIO.read(new File("res/asteroid/asteroid_top_right_4.png"));
            bonus = ImageIO.read(new File("res/asteroid/explosion.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(direction.equals("explosion")) {
            solidArea.y = -100;
            x += 1;
            y += 1;
        } else {
            y += speed;
            x -= speed;
        }
        if (x > gamePanel.screenWidth*2) {
            respawn();
        }
        if (y > gamePanel.screenHeight*2) {
            respawn();
        }
        if(!direction.equals("explosion")){
            updateSolidArea();
        }

        gamePanel.collisionChecker.checkCollision(this);
        if(collision) {
            direction = "explosion";
            if(collisionCounter == 1){
                solidArea.y = -100;
            }
            if (collisionCounter == 55) {
                collisionCounter = 0;
                collision = false;
                direction = "up";
                respawn();
            } else {
                collisionCounter++;
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

        if(respawnCounter > RESPAWN_COUNTER_TARGET){
            respawnCounter = 0;
            ++speed;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
    }
}
