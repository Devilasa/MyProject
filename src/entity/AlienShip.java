package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AlienShip extends Asteroid {
    public AlienShip(GamePanel gamePanel) {
        super(gamePanel);

        TEXTURE_SHIFT_X = 8;
        TEXTURE_SHIFT_Y = 23;
        RESPAWN_COUNTER_TARGET = 6;

        setDefaultValue();
        getAlienImage();

        solidArea = new Rectangle(x + TEXTURE_SHIFT_X, y + TEXTURE_SHIFT_Y, 50, 29);

    }

    @Override
    public void setDefaultValue() {
        Random rng = new Random();
        x = gamePanel.screenWidth + 40;
        y = rng.nextInt(10, gamePanel.screenHeight - 20);
        speed = 2;
        direction = "up";
    }

    @Override
    public void respawn() {
        Random rng = new Random();
        x = gamePanel.screenWidth + 40;
        y = rng.nextInt(10, gamePanel.screenHeight - 20);
        updateSolidArea();
        ++respawnCounter;
    }


    public void getAlienImage() {
        try {
            up = ImageIO.read(new File("res/asteroid/alienship_1.png"));
            down = ImageIO.read(new File("res/asteroid/alienship_2.png"));
            bonus = ImageIO.read(new File("res/asteroid/explosion.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if(direction.equals("explosion")) {
            solidArea.y = -100;
            x += 1;
            y += 1;
        } else {
            x -= speed;
        }
        if (x < -gamePanel.screenWidth*2) {
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
            if (collisionCounter == 60) {
                collisionCounter = 0;
                collision = false;
                direction = "up";
                respawn();
            } else {
                collisionCounter++;
                System.out.println("Boom!");
            }
        }

        spriteCounter++;
        if(spriteCounter>28) {
            switch (direction) {
                case "up" -> direction = "down";
                case "down" -> direction = "up";
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
