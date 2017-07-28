package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.AI.SteeringAI;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.Action;
import com.nilbmar.hunter.Enums.EntityType;

/**
 * Created by sysgeek on 5/26/17.
 *
 * Abstract Class: Enemy
 * Purpose: Base class for all Enemies
 */

public class Enemy extends Entity {
    private DirectionComponent directionComp;
    private DirectionComponent.Direction currentDirection;
    private DirectionComponent.Direction previousDirection;
    private SteeringAI ai;

    private boolean destroyed;
    private boolean aiAssigned;

    private float stateTimer; // Used to getFrame() of animation
    private int hitPoints;
    private int maxHitPoints;

    public Enemy(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Generic Enemy");
        entityType = EntityType.ENEMY;

        // TODO: THESE WILL BE NEEDED FOR NEW AnimationComp
        //setImageWidth(20);
        //setImageHeight(24);
        atlas = screen.getAssetsHandler().getEnemyAtlas();
        destroyed = false;

        directionComp = new DirectionComponent();
        currentDirection = directionComp.getDirection();
        previousDirection = currentDirection;

        currentAction = Action.STILL;
        previousAction = Action.STILL;
        stateTimer = 0;

        // AI
        // TODO: REMOVE RADIUS FROM STEERINGAI CONSTRUCTOR



    }

    public DirectionComponent getDirectionComponent() { return directionComp; }

    public void setSteeringAI() {
        if (b2Body != null) {
            ai = new SteeringAI(this, screen.getPlayer(), 30);
        }
    }

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
    public void setMaxHitPoints(int maxHitPoints) { this.maxHitPoints = maxHitPoints; }

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

    private void setSprite() {
        TextureRegion charStill = new TextureRegion(
                atlas.findRegion(regionName),
                regionBeginX, regionBeginY, regionWidth, regionHeight);
        setBounds(boundsBeginX / HunterOfPoke.PPM, boundsBeginY / HunterOfPoke.PPM,
                boundsWidth / HunterOfPoke.PPM, boundsHeight / HunterOfPoke.PPM);
        setRegion(charStill);
    }

    public void prepareToDraw() {
        defineBody();
        setSprite();
    }

    public void draw(Batch batch) {
        if (!destroyed) {
            super.draw(batch);

        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (ai != null) {
                ai.update(deltaTime);
        }

    }
}
