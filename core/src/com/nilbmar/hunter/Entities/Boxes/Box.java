package com.nilbmar.hunter.Entities.Boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Entities.NewEntity;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.BoxCreator;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by sysgeek on 8/22/17.
 */

public abstract class Box extends NewEntity implements Poolable {
    protected World world;
    protected PlayScreen screen;
    protected BoxCreator boxCreator;

    protected Vector2 velocity;

    protected MoveComponent movement;
    protected float acceleration = 1;
    protected boolean landed;
    protected boolean pastRisingPoint;

    // Amount to scaleRiseFall image so it looks like it's rising or falling
    protected float scaleRiseFall = 0.07f;
    protected float sideScaleRiseFall = 0.05f; // Different for LEFT and RIGHT directions
    protected float arcShotRise = 0.015f;
    protected float arcShotFall = 0.008f;
    protected float zAxis = 0.0f;
    protected float defaultScale;

    protected Animation animation;
    protected Array<TextureRegion> frames;
    protected TextureRegion regionToDraw;

    protected float lifespan;
    protected boolean setToDestroy;
    protected boolean destroyed;

    protected float stateTime;
    protected float coolOffTime;
    protected float rotation;

    public Box(PlayScreen screen, float startInWorldX, float startInWorldY, Vector2 v, float rotation) {
        super(screen, startInWorldX, startInWorldY);
        this.world = screen.getWorld();
        this.screen = screen;
        this.boxCreator = screen.getBoxCreator();
        this.rotation = rotation;

        landed = false;

        entityType = EntityType.BULLET;
        atlas = screen.getAssetsHandler().getBulletAtlas();

        imageComponent.setPosition(startInWorldX, startInWorldY);
        defaultScale = imageComponent.getScaleX();
    }

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
    public float getSpawnOtherX() {
        return imageComponent.getX() + imageComponent.getWidth() / 2;
    }

    @Override
    public float getSpawnOtherY() {
        return imageComponent.getY() + imageComponent.getHeight() / 2;
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

    public void arcShot() {

        // Should box be rising or falling
        if (stateTime >= 0.2) {
            pastRisingPoint = true;
        }

        // Scale box up if it's not past its halfway point
        if (!pastRisingPoint) {
            switch (movement.getCurrentDirection()) {
                case UP:
                case UP_LEFT:
                case UP_RIGHT:
                case DOWN:
                case DOWN_LEFT:
                case DOWN_RIGHT:
                    imageComponent.scale(imageComponent.getScaleX() / HunterOfPoke.PPM + scaleRiseFall);
                    break;
                case LEFT:
                case RIGHT:
                    imageComponent.scale(imageComponent.getScaleX() / HunterOfPoke.PPM + sideScaleRiseFall);
                    offsetSpriteY += arcShotRise;
                    break;
            }

        } else {
            // Scale box image down after it's past it's halfway point
            switch (movement.getCurrentDirection()) {
                case UP:
                case UP_LEFT:
                case UP_RIGHT:
                case DOWN:
                case DOWN_LEFT:
                case DOWN_RIGHT:
                    if (imageComponent.getScaleX() > defaultScale) {
                        imageComponent.scale(imageComponent.getScaleX() / HunterOfPoke.PPM - scaleRiseFall);
                    } else {
                        setLanded(true);
                    }
                    break;
                case LEFT:
                case RIGHT:
                    // This causes the box to slide after landing
                    // but if I try to setLanded() based on offsetSpriteY
                    // box will land while still scaled too large
                    if (imageComponent.getScaleX() > defaultScale || offsetSpriteY > 0) {
                        if (imageComponent.getScaleX() > defaultScale) {
                            imageComponent.scale(imageComponent.getScaleX() / HunterOfPoke.PPM - sideScaleRiseFall);
                        }
                        if (offsetSpriteY > 0) {
                            arcShotFall = 0.008f;
                            offsetSpriteY -= arcShotFall;
                        }
                    } else {
                        setLanded(true);
                    }


                    break;
            }
        }
    }

    protected void setLanded(boolean landed) {
        this.landed = landed;
    }

    public void stop() {
        movement.move(new Vector2(0, 0), 0);
    }

    private void move() {
        if (movement != null) {
            if (velocity.x < 0) {
                velocity.x -= 1;
            } else if (velocity.x > 0) {
                velocity.x += 1;
            }

            if (velocity.y < 0) {
                velocity.y -= 1;
            } else if (velocity.y > 0) {
                velocity.y += 1;
            }

            movement.move(velocity, 2);
        }
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
    }

    //@Override
    private void destroy() {
        destroyed = true;

        stateTime = 0;
        boxCreator.reduceBulletCount();
        screen.getBoxCreator().getAllBoxesPool().free(this);
        screen.getBoxCreator().getAllBoxesArray().removeValue(this, true);
        world.destroyBody(b2Body);
    }

    // Resets so can be pooled without values carrying over
    @Override
    public void reset() {
        // TODO: NO IDEA ABOUT POSITION, SETTING IT TO OFFSCREEN?
        imageComponent.setPosition(-2, -2);
        this.stateTime = 0;
        this.setToDestroy = false;
        this.destroyed = false;
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

        if (!destroyed) {
            if (setToDestroy) {
                destroy();
            } else {
                if (landed != true) {
                    move();
                } else {
                    stop();
                }
                stateTime += deltaTime;
                //timeout();
            }
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

                imageComponent.draw(batch);

                /* THIS DOESN'T WORK WITH SCALING
                // FIGURE OUT HOW TO ROTATE USING THE sprite.draw(batch) ABOVE
                // -----------------------------------------------------------
                // Rotate bullet and send to batch
                // rotation is handled by BulletPatternHandler's
                // private getBulletRotation(DirectionComponent.Direction dir)
                batch.draw(regionToDraw, imageComponent.getX() + offsetSpriteX, imageComponent.getY() + offsetSpriteY,
                        regionToDraw.getRegionWidth() / 2 / HunterOfPoke.PPM,
                        regionToDraw.getRegionHeight() / 2 / HunterOfPoke.PPM,
                        regionToDraw.getRegionWidth() / HunterOfPoke.PPM,
                        regionToDraw.getRegionHeight() / HunterOfPoke.PPM,
                        regionToDraw.getRegionHeight() / (float) regionToDraw.getRegionWidth(),
                        2 - (regionToDraw.getRegionHeight() / (float) regionToDraw.getRegionWidth()),
                        rotation, false);
                */
            }
        }
    }
}
