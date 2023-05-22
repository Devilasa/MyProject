package main;

import entity.Asteroid;
import entity.Entity;

import java.util.ArrayList;

public class CollisionChecker {
    GamePanel gamePanel;
    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void checkCollision(Entity entity){
        for(Entity e : gamePanel.entitiesList){
            if(e != entity) {
                if (entity.solidArea.intersects(e.solidArea)) {
                    entity.collision = true;
                    break;
                }
            }
        }
    }
}
