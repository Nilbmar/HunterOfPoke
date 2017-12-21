package com.nilbmar.hunter.Entities.Bullets;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.BulletCreator;
import com.nilbmar.hunter.Enums.EntityType;

/**
 * Created by sysgeek on 4/22/17.
 *
 * Abstract Class: Bullet
 * Purpose: Base class for all Bullets
 */

public abstract class Bullet  extends Entity implements Poolable {
    protected World world;
    protected PlayScreen screen;
    protected BulletCreator bulletCreator;

    protected Vector2 velocity;

    protected MoveComponent movement;
    protected float acceleration = 1;

    protected Animation animation;
    protected Array<TextureRegion> frames;
    protected TextureRegion regionToDraw;

    protected float lifespan;
    protected boolean setToDestroy;
    protected boolean destroyed;

    protected float stateTime;
    protected float coolOffTime;
    protected float rotation;
    protected String firedBy;

    public Bullet(PlayScreen screen, float startInWorldX, float startInWorldY, Vector2 v, float rotation, String firedBy) {
        super(screen, startInWorldX, startInWorldY);
        this.world = screen.getWorld();
        this.screen = screen;
        this.bulletCreator = screen.getBulletCreator();
        this.rotation = rotation;
        this.firedBy = firedBy;

        entityType = EntityType.BULLET;
        atlas = screen.getAssetsHandler().getBulletAtlas();

        imageComponent.setPosition(startInWorldX, startInWorldY);
    }

    public void setFiredBy(String firedBy) { this.firedBy = firedBy; }
    public String getFiredBy() { return firedBy; }

    @Override
    public void prepareToDraw() {

    }

    public String getRegionName(DirectionComponent.Direction currentDirection) {
        switch (currentDirection) {

        }

        return regionName;
    }

    public TextureRegion getFrame(float deltaTime, float stateTime) {
        return (TextureRegion) animation.getKeyFrame(stateTime, true);
    }

    public float getCoolOffTime() { return coolOffTime; }
    protected void setCoolOffTime(float coolOffTime) {
        this.coolOffTime = coolOffTime;
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
        if (firedBy.contains("PLAYER")) {
            defineBits((short) (HunterOfPoke.GROUND_BIT | HunterOfPoke.ENEMY_BIT));
        } else {
            defineBits((short) (HunterOfPoke.GROUND_BIT | HunterOfPoke.PLAYER_BIT));
        }
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

    private void timeout() {
        if (stateTime >= lifespan && !destroyed) {
            setToDestroy = true;
        }
    }

    public void onHit() {
        if (!destroyed) {
            setToDestroy = true;
        }
        /* TODO: REMOVE THIS TEST CODE
        // TESTING LINE CLASS WITH POINTS BETWEEN PLAYER
        // AND BULLET FIRED BY PLAYER, AFTER IT HITS SOMETHING
        if (firedBy.contains("PLAYER")) {
            Vision line = new Vision();
            Gdx.app.log("Get Distance between points",
                    "" + line.getDistance(screen.getPlayer().getPosition().x,
                    screen.getPlayer().getPosition().y,
                    this.getPosition().x,
                    getPosition().y)
            );
        }
        */

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
        imageComponent.setPosition(-2, -2);
        stateTime = 0;
        setToDestroy = false;
        destroyed = false;
    }

    // Initialize after getting from pool
    public void init(float posX, float posY) {
        imageComponent.setPosition(posX, posY);
        this.stateTime = 0;
        this.setToDestroy = false;
        this.destroyed = false;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);

        if (setToDestroy && !destroyed) {
            destroy();
        } else if (!destroyed) {
            move();
            stateTime += deltaTime;
            timeout();
        }
    }

    public void draw(Batch batch) {
        if (!destroyed) {
            // Have to rotate Bullet instead of simply drawing
            // so commented out the super.draw
            //super.draw(batch);

            // NOT SURE IF THIS WORKS OR JUST HARD TO REPRODUCE BAD EFFECT
            // Waiting till bullet is alive and has an angle before drawing
            // because some would show up at the wrong angle/wrong offset
            if (stateTime > 1 / HunterOfPoke.PPM) {
                regionToDraw = (TextureRegion) animation.getKeyFrame(stateTime);

                // Rotate bullet and send to batch
                // rotation is handled by BulletPatternHandler's
                // private getBulletRotation(DirectionComponent.Direction dir)
                batch.draw(regionToDraw, imageComponent.getX(), imageComponent.getY(),
                        regionToDraw.getRegionWidth() / 2 / HunterOfPoke.PPM,
                        regionToDraw.getRegionHeight() / 2 / HunterOfPoke.PPM,
                        regionToDraw.getRegionWidth() / HunterOfPoke.PPM,
                        regionToDraw.getRegionHeight() / HunterOfPoke.PPM,
                        regionToDraw.getRegionHeight() / (float) regionToDraw.getRegionWidth(),
                        2 - (regionToDraw.getRegionHeight() / (float) regionToDraw.getRegionWidth()),
                        rotation, false);
            }
        }
    }
}
