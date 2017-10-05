package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.AI.SteeringAI;
import com.nilbmar.hunter.Components.AnimationComp;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Components.FramesComponent;
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
    private EnemyType enemyType;

    private boolean destroyed;
    private boolean aiAssigned;


    public Enemy(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Generic Enemy");
        entityType = EntityType.ENEMY;
        enemyType = null;

        // TODO: THESE WILL BE NEEDED FOR NEW AnimationComp
        setImageWidth(16);
        setImageHeight(16);
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

    public void setupAnimationComponents() {
        if (enemyType != null) {
            // Set up frame information for animations to use
            framesComp = new FramesComponent(getImageWidth(), getImageHeight());
            framesComp.setFrames("json/animations/" + enemyType.getName() + "Anim.json");

            // Set up animations component
            animComp = new AnimationComp(screen, this, framesComp, regionName);

            // Create initial default animation
            charAnim = animComp.makeTexturesIntoAnimation(0.1f, directionComp.getDirection(), currentAction);

            // Used to set bounds at the feet and lower body
            //offsetSpriteY = 8 / HunterOfPoke.PPM;

            // TODO: PUT IMAGEWIDTH AND IMAGEHEIGHT INTO JSON
            imageComponent.setBounds(0, 0, getImageWidth() / HunterOfPoke.PPM, getImageHeight() / HunterOfPoke.PPM);
            //imageComponent.setBounds(boundsBeginX / HunterOfPoke.PPM, boundsBeginY / HunterOfPoke.PPM,
              //      boundsWidth / HunterOfPoke.PPM, boundsHeight / HunterOfPoke.PPM);
        } else {
            Gdx.app.log("setupAnimationComponents", "enemyType is null");
        }
    }

    public LifeComponent getLifeComponent() { return lifeComp; }

    // Based on AI movement
    public void setDirection() {
        // Switch Enemy direction based on position
        // relative to player position
        float dirX = getPosition().x - screen.getPlayer().getPosition().x;
        float dirY = getPosition().y - screen.getPlayer().getPosition().y;
        Gdx.app.log("enemy position", dirX + " " + dirY);
        if (!ai.getLinearVelocity().isZero()) {
            setAction(Action.WALKING);

            // If player is above enemy, face UP
            if (dirY < 0) {
                directionComp.setDirection(DirectionComponent.Direction.UP);
            } else if (dirY > 1) {
                // If player is below enemy, face DOWN, but allow for some SIDE facng
                directionComp.setDirection(DirectionComponent.Direction.DOWN);
            } else {
                // If Enemy is within 1 UP or DOWN of player,
                // face to the RIGHT or LEFT toward player
                if (dirX < 0) {
                    directionComp.setDirection(DirectionComponent.Direction.RIGHT);
                } else if (dirX > 0) {
                    directionComp.setDirection(DirectionComponent.Direction.LEFT);
                }
            }
        } else {
            // If Enemy is not moving, set to STILL animation
            setAction(Action.STILL);
        }
    }

    public void setSteeringAI() {
        if (b2Body != null) {
            ai = new SteeringAI(this, screen.getPlayer(), 30);
        }
    }

    public void setEnemyType(EnemyType enemyType) { this.enemyType = enemyType; }
    public String getRegionName(DirectionComponent.Direction currentDirection) {
        // TODO: THIS DOESN'T TAKE INTO ACCOUNT ENEMIES LIKE "bat"
        // THAT DOESN'T HAVE UP/DOWN/SIDE REGIONS
        switch (currentDirection) {
            case UP:
            case UP_LEFT:
            case UP_RIGHT:
                regionName = enemyType.getName() + "_up";
                break;
            case DOWN:
            case DOWN_LEFT:
            case DOWN_RIGHT:
                regionName = enemyType.getName() + "_down";
                break;
            case LEFT:
            case RIGHT:
                regionName = enemyType.getName() + "_side";
                break;
        }

        //Gdx.app.log("Enemy Region Name", regionName);
        return regionName;
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
                lifeComp.loseHitPoints(1);
                if (!lifeComp.isDead()) {
                    Gdx.app.log("Enemy Death", "AAaaaaaaggghhh! I'm dying!");
                }
                break;
        }
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
            setDirection();
        }

        imageComponent.setRegion(getFrame(deltaTime));
    }


}
