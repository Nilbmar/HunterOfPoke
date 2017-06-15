package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.Enums.Action;
import com.nilbmar.hunter.Tools.Enums.Direction;

/**
 * Created by sysgeek on 5/26/17.
 */

public abstract class Enemy extends Entity {
    protected float startInWorldX;
    protected float startInWorldY;

    protected float offsetSpriteY;

    protected boolean destroyed;

    protected float stateTimer; // Used to getFrame() of animation

    public Enemy(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Generic Enemy");

        // do not remove these
        this.startInWorldX = startInWorldX;
        this.startInWorldY = startInWorldY;

        destroyed = false;

        currentDirection = Direction.DOWN;
        previousDirection = Direction.DOWN;
        currentAction = Action.STILL;
        previousAction = Action.STILL;
        stateTimer = 0;
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
        defineBits((short) (HunterOfPoke.PLAYER_BIT | HunterOfPoke.GROUND_BIT | HunterOfPoke.ENEMY_BIT));
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
    }
}
