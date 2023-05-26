package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameOverDisplay extends Entity{

    public int scale = 0;
    GamePanel gamePanel;

    public GameOverDisplay(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        getImage();
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 480;
        y = 360;
    }

    public void getImage(){
        try {
            end = ImageIO.read(new File("res/spaceship/game_over.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(end, x - scale / 2, y - scale / 2, end.getWidth() + scale, end.getHeight() + scale, null);
        if(scale < 500) {
            scale += 2;
        }
    }
}
