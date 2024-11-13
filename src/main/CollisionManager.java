package main;

import entity.Entity;

public class CollisionManager {
    GamePanel gp;

    public CollisionManager(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // retrieve coordinates of the 4 corners of entity hitbox
        int entityLeftWorldX = entity.worldX + entity.hitbox.x;
        int entityRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.worldY + entity.hitbox.y;
        int entityBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

        // then get which column and row each corner of the hitbox is in
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // will represent the tiles we need to check collision for (only need 2 per direction)
        int tileNum1, tileNum2;

        // redefines rows and columns of hitbox: where will it be in the future if the player tries to move in a direction
        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                // get top left and top right tiles touched by the hitbox
                tileNum1 = gp.tileM.mapTileNumbers[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumbers[entityRightCol][entityTopRow];
                // checks if any of said 2 tiles are solid
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                // get top left and bottom left tiles touched by the hitbox
                tileNum1 = gp.tileM.mapTileNumbers[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumbers[entityLeftCol][entityBottomRow];
                // checks if any of said 2 tiles are solid
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                // get bottom left and bottom right tiles touched by the hitbox
                tileNum1 = gp.tileM.mapTileNumbers[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNumbers[entityRightCol][entityBottomRow];
                // checks if any of said 2 tiles are solid
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                // get top right and bottom right tiles touched by the hitbox
                tileNum1 = gp.tileM.mapTileNumbers[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumbers[entityRightCol][entityBottomRow];
                // checks if any of said 2 tiles are solid
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean isPlayer) {
        int index = 999;
        for (int i = 0; i < gp.obj.length; i++) {
            // checks only non-null object
            if (gp.obj[i] != null) {
                // get entity's hitbox position
                entity.hitbox.x = entity.worldX + entity.hitbox.x;
                entity.hitbox.y = entity.worldY + entity.hitbox.y;
                // get object's hitbox position
                gp.obj[i].hitbox.x = gp.obj[i].worldX + gp.obj[i].hitbox.x;
                gp.obj[i].hitbox.y = gp.obj[i].worldY + gp.obj[i].hitbox.y;

                switch(entity.direction) {
                    // calculates future hitbox coordinates if entity tries to move in a direction
                    // checks collision between entity and object hitboxes
                    case "up":
                        entity.hitbox.y -= entity.speed;
                        if(entity.hitbox.intersects(gp.obj[i].hitbox)) {
                            // object will stop the entity if it's solid
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            // return index of collisionned object only if player touches it because only player can pick up objects
                            if(isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.hitbox.x -= entity.speed;
                        if(entity.hitbox.intersects(gp.obj[i].hitbox)) {
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.hitbox.y += entity.speed;
                        if(entity.hitbox.intersects(gp.obj[i].hitbox)) {
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.hitbox.x += entity.speed;
                        if(entity.hitbox.intersects(gp.obj[i].hitbox)) {
                            if(gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if(isPlayer == true) {
                                index = i;
                            }
                        }
                        break;
                }
                // resets hitbox positions after calculations
                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
                gp.obj[i].hitbox.x = gp.obj[i].hitboxDefaultX;
                gp.obj[i].hitbox.y = gp.obj[i].hitboxDefaultY;
            }
        }
        return index;
    }
}
