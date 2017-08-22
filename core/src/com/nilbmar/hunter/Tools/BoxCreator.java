package com.nilbmar.hunter.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.nilbmar.hunter.Entities.Boxes.Box;
import com.nilbmar.hunter.Entities.Boxes.SimpleBox;
import com.nilbmar.hunter.Entities.Bullets.Bullet;
import com.nilbmar.hunter.Entities.Bullets.BulletA;
import com.nilbmar.hunter.Entities.Bullets.FireBullet;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 8/22/17.
 */

public class BoxCreator {
    private PlayScreen screen;
    private Pool<Box> boxesPool;
    private Array<Box> boxes;

    private BulletType type;

    private float stateTime;
    private float coolOffTime;
    private boolean readyToFire;
    private int boxesOnScreen;
    private float rotation;


    // TODO: MIGHT NOT NEED THESE
    private int bulletsPerShot;
    private int bulletsAllowedOnScreen = 6;

    public BoxCreator(PlayScreen screen) {
        this.screen = screen;
        boxes = new Array<Box>();
        stateTime = 0;
        rotation = 0;
        readyToFire = true;
        coolOffTime = 0;
        boxesOnScreen = 0;
        bulletsPerShot = 0;
    }

    public void spawnBullet(float x, float y, Vector2 d) {
        if (readyToFire) {
            final float posX = x;
            final float posY = y;
            final Vector2 direction = d;

            this.boxesPool = new Pool<Box>() {
                @Override
                protected Box newObject() {
                    // TODO: MAKE GETTER AND DETERMINE TYPE OF BULLET
                    return getBullet(type, posX, posY, direction);
                }
            };

            Box box = boxesPool.obtain();
            box.init(posX, posY);
            coolOffTime = box.getCoolOffTime();
            boxes.add(box);
            boxesOnScreen += 1;

            // Used in update to determine if cool off has been reached
            stateTime = 0;
        }
    }

    // TODO: MIGHT NOT NEED THESE FUNCTIONS
    public void reduceBulletCount() {
        boxesOnScreen = boxesOnScreen - 1;
    }
    public void setBulletsPerShot(int bulletsPerShot) {
        this.bulletsPerShot = bulletsPerShot;
    }


    public void update(float deltaTime) {
        if (stateTime >= coolOffTime) {
            readyToFire = true;
        } else {
            readyToFire = false;
        }

        stateTime += deltaTime;
    }

    private Box getBullet(BulletType type, float posX, float posY, Vector2 direction) {
        Box box = null;
        switch(type) {
            case FIRE:
                box = new SimpleBox(screen, posX, posY, direction, rotation);
                break;
            case BALL:
            default:
                box = new SimpleBox(screen, posX, posY, direction, rotation);
                break;
        }

        return box;
    }

    public Pool<Box> getAllBoxesPool() { return boxesPool; }
    public Array<Box> getAllBoxesArray() { return boxes; }

    public void setBulletType(BulletType type) { this.type = type; }
    public void setRotation(float rotation) { this.rotation = rotation; }
}

