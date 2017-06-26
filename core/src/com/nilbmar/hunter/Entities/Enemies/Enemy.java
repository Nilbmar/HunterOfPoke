package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.Commands.FireCommand;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.Action;
import com.nilbmar.hunter.Enums.Direction;
import com.nilbmar.hunter.Enums.EntityType;

/**
 * Created by sysgeek on 5/26/17.
 *
 * Abstract Class: Enemy
 * Purpose: Base class for all Enemies
 */

public abstract class Enemy extends Entity {
    protected float startInWorldX;
    protected float startInWorldY;


    protected float offsetSpriteY;

    protected boolean destroyed;

    protected float stateTimer; // Used to getFrame() of animation
    protected int hitPoints;
    protected int maxHitPoints;

    // TODO: REMOVE FIRECOMMAND
    int fireCount = 1;
    FireCommand fire;

    public Enemy(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Generic Enemy");
        entityType = EntityType.ENEMY;

        // do not remove these
        this.startInWorldX = startInWorldX;
        this.startInWorldY = startInWorldY;

        destroyed = false;

        currentDirection = Direction.DOWN;
        previousDirection = Direction.DOWN;
        currentAction = Action.STILL;
        previousAction = Action.STILL;
        stateTimer = 0;

        fire = new FireCommand(screen.getBulletPatterns(), BulletType.BALL, ShotType.SINGLE);
    }

    @Override
    public float getSpawnOtherX() {
        return getX() + getWidth() / 2;
    }

    @Override
    public float getSpawnOtherY() {
        return getY() + getHeight() / 2;
    }

    public int getHitPoints() { return hitPoints; }
    public void recoverHitPoints(int hitPointsToAdd) {
        int tempHP = hitPoints + hitPointsToAdd;
        if (tempHP >= maxHitPoints) {
            hitPoints = maxHitPoints;
        } else {
            hitPoints = tempHP;
        }
    }

    @Override
    public void onHit(Entity entity) {
        Gdx.app.log(getName(), "Ouch! You hit me, you scum!");
    }

    @Override
    protected void defineShape() {
        //bodyComponent.setFixtureDef(Shape.Type.Circle, 5); // CircleShape - radius of 5
        bodyComponent.setFixtureDef(Shape.Type.Polygon, 4, 4); // PolygonShape - Set as Box
    }

    @Override
    protected void defineBody() {
        createBody(startInWorldX, startInWorldY);
        defineShape();
        defineBits((short) (HunterOfPoke.PLAYER_BIT | HunterOfPoke.GROUND_BIT
                | HunterOfPoke.ENEMY_BIT | HunterOfPoke.ITEM_BIT));
        finalizeBody();
    }

    @Override
    protected void defineBits(short maskBits) {
        bodyComponent.setCategoryBit(HunterOfPoke.ENEMY_BIT);
        bodyComponent.setMaskBits(maskBits);
    }

    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // Used to set the b2Body's shape lower on the sprite
        // so only lower-body collides with objects
        setPosition(b2Body.getPosition().x - getWidth() / 2,
                b2Body.getPosition().y - getHeight() / 2 + offsetSpriteY);

        // TODO: REMOVE - ONLY USING THIS FOR TESTING FIRECOMMAND
        // ONLY ALLOWS SINGLE SHOT
        if (fireCount >= 1) {
            fire.setType(BulletType.BALL);
            fire.execute(this);
            fireCount--;
        }
    }
}
