package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.AI.SteeringAI;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Components.LifeComponent;
import com.nilbmar.hunter.Entities.NewEntity;
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

public class Enemy extends NewEntity {
    private SteeringAI ai;
    private LifeComponent lifeComp;

    private boolean destroyed;
    private boolean aiAssigned;


    public Enemy(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Generic Enemy");
        entityType = EntityType.ENEMY;

        // TODO: THESE WILL BE NEEDED FOR NEW AnimationComp
        //setImageWidth(20);
        //setImageHeight(24);
        atlas = screen.getAssetsHandler().getEnemyAtlas();
        destroyed = false;

        lifeComp = new LifeComponent();
        directionComp = new DirectionComponent();

        currentAction = Action.STILL;
        previousAction = Action.STILL;
        stateTimer = 0;

        imageComponent.setPosition(startInWorldX, startInWorldY);

        // AI
        // TODO: REMOVE RADIUS FROM STEERINGAI CONSTRUCTOR
    }

    public DirectionComponent getDirectionComponent() { return directionComp; }
    public LifeComponent getLifeComponent() { return lifeComp; }

    public void setSteeringAI() {
        if (b2Body != null) {
            ai = new SteeringAI(this, screen.getPlayer(), 30);
        }
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
    public void onHit(NewEntity entity) {
        switch (entity.getEntityType()) {
            case BULLET:
                Gdx.app.log("Enemy Hit", "You got me!");
                break;
        }


        //Gdx.app.log(getName(), "Ouch! You hit me, you scum!");
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
                | HunterOfPoke.ENEMY_BIT | HunterOfPoke.ITEM_BIT | HunterOfPoke.BULLET_BIT));
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
        imageComponent.setBounds(boundsBeginX / HunterOfPoke.PPM, boundsBeginY / HunterOfPoke.PPM,
                boundsWidth / HunterOfPoke.PPM, boundsHeight / HunterOfPoke.PPM);
        imageComponent.setRegion(charStill);
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
