package com.nilbmar.hunter.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.nilbmar.hunter.Entities.Bullets.Bullet;
import com.nilbmar.hunter.Entities.Bullets.BulletA;
import com.nilbmar.hunter.Entities.Bullets.FireBullet;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.BulletType;

/**
 * Created by sysgeek on 4/24/17.
 */

public class BulletCreator {
    private PlayScreen screen;
    private Pool<Bullet> bulletPool;
    private Array<Bullet> bullets;

    private BulletType type;

    private float stateTime;
    private float coolOffTime;
    private boolean readyToFire;
    private int bulletsOnScreen;
    private float rotation;


    // TODO: MIGHT NOT NEED THESE
    private int bulletsPerShot;
    private int bulletsAllowedOnScreen = 6;

    public BulletCreator(PlayScreen screen) {
        this.screen = screen;
        bullets = new Array<Bullet>();
        stateTime = 0;
        rotation = 0;
        readyToFire = true;
        coolOffTime = 0;
        bulletsOnScreen = 0;
        bulletsPerShot = 0;
    }

    public void spawnBullet(float x, float y, Vector2 d, Entity f) {
        if (readyToFire) {
            final float posX = x;
            final float posY = y;
            final Vector2 direction = d;
            final Entity firedBy = f;

            this.bulletPool = new Pool<Bullet>() {
                @Override
                protected Bullet newObject() {
                    // TODO: MAKE GETTER AND DETERMINE TYPE OF BULLET
                    return getBullet(type, posX, posY, direction, firedBy);
                }
            };

            Bullet bullet = bulletPool.obtain();
            bullet.init(posX, posY);
            coolOffTime = bullet.getCoolOffTime();
            bullets.add(bullet);
            bulletsOnScreen += 1;

            // Used in update to determine if cool off has been reached
            stateTime = 0;
        }
    }

    // TODO: MIGHT NOT NEED THESE FUNCTIONS
    public void reduceBulletCount() {
        bulletsOnScreen = bulletsOnScreen - 1;
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

    private Bullet getBullet(BulletType type, float posX, float posY, Vector2 direction, Entity firedBy) {
        Bullet bullet = null;
        switch(type) {
            case FIRE:
                bullet = new FireBullet(screen, posX, posY, direction, rotation, firedBy);
                break;
            case BALL:
            default:
                bullet = new BulletA(screen, posX, posY, direction, rotation, firedBy);
                break;
        }

        return bullet;
    }

    public Pool<Bullet> getAllBulletsPool() { return bulletPool; }
    public Array<Bullet> getAllBulletsArray() { return bullets; }

    public void setBulletType(BulletType type) { this.type = type; }
    public void setRotation(float rotation) { this.rotation = rotation; }
}
