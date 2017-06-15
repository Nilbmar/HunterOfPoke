package com.nilbmar.hunter.Entities.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.nilbmar.hunter.Components.BodyComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.BulletCreator;

/**
 * Created by sysgeek on 4/22/17.
 */

public abstract class Bullet  extends Entity implements Poolable {
    protected World world;
    protected PlayScreen screen;
    protected BulletCreator bulletCreator;

    protected Vector2 velocity;

    protected MoveComponent movement;
    protected float acceleration = 1;

    protected float lifespan;
    protected boolean setToDestroy;
    protected boolean destroyed;

    protected float stateTime;
    protected float coolOffTime;

    public Bullet(PlayScreen screen, float startInWorldX, float startInWorldY, Vector2 v) {
        super(screen, startInWorldX, startInWorldY);
        this.world = screen.getWorld();
        this.screen = screen;
        this.bulletCreator = screen.getBulletCreator();

        setPosition(startInWorldX, startInWorldY);
    }

    public TextureRegion getRotatedRegion(int originX, int originY, int width, int height) {
        Sprite sprite = new Sprite(screen.getBulletAtlas().findRegion(regionName).getTexture());
        sprite.setRegion(originX, originY, width, height);
        sprite.setOrigin(originX + .5f, originY + .5f);
        sprite.rotate90(true);

        TextureRegion region = new TextureRegion(
                screen.getBulletAtlas().findRegion(regionName).getTexture(), originX, originY, width, height);
        return region;
    }

    public float getCoolOffTime() { return coolOffTime; }
    protected void setCoolOffTime(float coolOffTime) {
        this.coolOffTime = coolOffTime;
    }

    @Override
    public float getSpawnOtherX() {
        return getX() + getWidth() / 2;
    }

    @Override
    public float getSpawnOtherY() {
        return getY() + getHeight() / 2;
    }

    @Override
    protected void defineShape() {
        //bodyComponent.setFixtureDef(Shape.Type.Circle, 5); // CircleShape - radius of 5
        bodyComponent.setFixtureDef(Shape.Type.Polygon, 5, 5); // PolygonShape - Set as Box
    }

    @Override
    protected void defineBody() {
        createBody(startInWorldX, startInWorldY);
        defineShape();
        defineBits((short) (HunterOfPoke.GROUND_BIT | HunterOfPoke.ENEMY_BIT));
        finalizeBody();
    }

    @Override
    protected void defineBits(short maskBits) {
        bodyComponent.setCategoryBit(HunterOfPoke.BULLET_BIT);
        bodyComponent.setMaskBits(maskBits);
    }

    private void move() {
        if (velocity.x < 0) {
            velocity.x -= 1;
        } else if (velocity.x > 0) {
            velocity.x +=1;
        }

        if (velocity.y < 0) {
            velocity.y -= 1;
        } else if (velocity.y > 0) {
            velocity.y +=1;
        }

        movement.move(velocity, 2);
    }

    protected void timeout() {
        if (stateTime >= lifespan && !destroyed) {
            setToDestroy = true;
        }
    }

    public void onHit() {
        if (!destroyed) {
            setToDestroy = true;
        }
    }

    //@Override
    private void destroy() {
        destroyed = true;

        stateTime = 0;
        bulletCreator.reduceBulletCount();
        screen.getBulletCreator().getAllBulletsPool().free(this);
        screen.getBulletCreator().getAllBulletsArray().removeValue(this, true);
        world.destroyBody(b2Body);
    }

    // Resets so can be pooled without values carrying over
    @Override
    public void reset() {
        // TODO: NO IDEA ABOUT POSITION, SETTING IT TO OFFSCREEN?
        this.setPosition(-2, -2);
        this.stateTime = 0;
        this.setToDestroy = false;
        this.destroyed = false;
    }

    // Initialize after getting from pool
    public void init(float posX, float posY) {
        this.setPosition(posX, posY);
        this.stateTime = 0;
        this.setToDestroy = false;
        this.destroyed = false;
    }

    public void update(float deltaTime) {

        if (setToDestroy && !destroyed) {
            destroy();
        } else if (!destroyed) {
            move();
            setPosition(b2Body.getPosition().x - getWidth() / 2,
                    b2Body.getPosition().y - getHeight() / 2);
            stateTime += deltaTime;
            timeout();
        }
    }

    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
        }
    }
}
