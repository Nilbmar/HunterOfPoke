package com.nilbmar.hunter.Tools;

import com.badlogic.gdx.math.Vector2;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.HunterOfPoke;

/**
 * Created by sysgeek on 8/22/17.
 */

public class BoxPatternHandler {
    private BoxCreator boxCreator;

    public BoxPatternHandler(BoxCreator boxCreator) {
        this.boxCreator = boxCreator;

    }

    private Vector2 getDirToShoot(DirectionComponent.Direction dir) {
        Vector2 dirToShoot = new Vector2();
        int move = 1;

        switch(dir) {
            case UP:
                dirToShoot.set(0, move);
                break;
            case UP_LEFT:
                dirToShoot.set(-move, move);
                break;
            case UP_RIGHT:
                dirToShoot.set(move, move);
                break;
            case DOWN:
                dirToShoot.set(0, -move);
                break;
            case DOWN_LEFT:
                dirToShoot.set(-move, -move);
                break;
            case DOWN_RIGHT:
                dirToShoot.set(move, -move);
                break;
            case LEFT:
                dirToShoot.set(-move, 0);
                break;
            case RIGHT:
                dirToShoot.set(move, 0);
                break;
            default:
                dirToShoot.set(0, 0);
                break;
        }
        return dirToShoot;
    }

    private float getBulletRotation(DirectionComponent.Direction dir) {
        float rotateBulletTexture = 270;

        switch (dir) {
            case UP:
                rotateBulletTexture = 180f;
                break;
            case UP_LEFT:
                rotateBulletTexture = 225f;
                break;
            case UP_RIGHT:
                rotateBulletTexture = 135f;
                break;
            case DOWN:
                rotateBulletTexture = 0f;
                break;
            case DOWN_LEFT:
                rotateBulletTexture = -45f;
                break;
            case DOWN_RIGHT:
                rotateBulletTexture = 45f;
                break;
            case LEFT:
                rotateBulletTexture = -90f;
                break;
            case RIGHT:
                rotateBulletTexture = 90f;
                break;
        }

        return rotateBulletTexture;
    }

    public void singleShot(BulletType type, DirectionComponent.Direction dir, float spawnX, float spawnY) {
        Vector2 direction = getDirToShoot(dir);

        // Make non-evenly shaped bullets look right
        boxCreator.setRotation(getBulletRotation(dir));

        boxCreator.setBulletType(type);
        boxCreator.setBulletsPerShot(1);

        boxCreator.spawnBullet(spawnX, spawnY, direction);
    }

    public void twinShot(BulletType type, DirectionComponent.Direction dir, float spawnX, float spawnY) {
        Vector2 direction = getDirToShoot(dir);

        // Make non-evenly shaped bullets look right
        boxCreator.setRotation(getBulletRotation(dir));


        // On Diagonals slightly rotate shots
        Vector2 altDir = new Vector2(direction.x, direction.y);
        float rotation = 44.75f;

        float offset = 5 / HunterOfPoke.PPM;
        float altSpawnX = spawnX;
        float altSpawnY = spawnY;

        switch (dir) {
            case UP:
                altSpawnX = spawnX + offset;
                altSpawnY = spawnY;
                spawnX = spawnX - offset;
                //spawnY = spawnY - offset * 2;
                break;
            case UP_LEFT:
                altSpawnX = spawnX;
                altSpawnY = spawnY - offset * 2;
                spawnX = spawnX + offset;
                //spawnY = spawnY + offset;
                altDir.rotate(rotation);
                direction.rotate(-rotation);
                break;
            case UP_RIGHT:
                altSpawnX = spawnX;
                altSpawnY = spawnY - offset * 2;
                spawnX = spawnX - offset;
                //spawnY = spawnY + offset;
                altDir.rotate(-rotation);
                direction.rotate(rotation);
                break;
            case DOWN:
                spawnX = spawnX + offset;
                altSpawnX = spawnX - offset * 2;
                altSpawnY = spawnY;
                break;
            case DOWN_LEFT:
                altSpawnX = spawnX + offset * 2;
                altSpawnY = spawnY; // + offset;
                spawnX = spawnX + offset;
                spawnY = spawnY + offset;
                altDir.rotate(rotation);
                direction.rotate(-rotation);
                break;
            case DOWN_RIGHT:
                altSpawnX = spawnX - offset * 2;
                altSpawnY = spawnY; // - offset;
                spawnX = spawnX - offset;
                spawnY = spawnY + offset;
                altDir.rotate(-rotation);
                direction.rotate(rotation);
                break;
            case LEFT:
                // Orig is top
                altSpawnX = spawnX + offset;
                spawnX = spawnX - offset;
                altSpawnY = spawnY - offset;
                spawnY = spawnY + offset;
                break;
            case RIGHT:
                // Alt is top
                altSpawnX = spawnX + offset;
                altSpawnY = spawnY + offset;
                spawnY = spawnY - offset;
                break;
        }

        // Spawn Bullets
        boxCreator.setBulletType(type);
        boxCreator.setBulletsPerShot(2);
        boxCreator.spawnBullet(spawnX, spawnY, direction);
        boxCreator.spawnBullet(altSpawnX, altSpawnY, altDir);
    }
}

