package main;


import entity.Entity;


public class CollisionChecker {
    GamePanel gamePanel;
    public CollisionChecker(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void checkCollision(Entity entity){
        for(Entity e : gamePanel.inGameEntitiesList){
            if(e != entity) {
                if (entity.solidArea.intersects(e.solidArea)) {
                    entity.collision = true;
                    break;
                }
            }
        }
    }
}
