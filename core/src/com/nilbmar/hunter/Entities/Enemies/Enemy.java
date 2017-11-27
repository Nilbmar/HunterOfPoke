package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.AI.AITarget;
import com.nilbmar.hunter.AI.Brains.Brain;
import com.nilbmar.hunter.AI.Brains.ScaredBrain;
import com.nilbmar.hunter.AI.States.Temperament;
import com.nilbmar.hunter.AI.SteeringAI;
import com.nilbmar.hunter.AI.Utils.Vision;
import com.nilbmar.hunter.Components.AnimationComp;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Components.FramesComponent;
import com.nilbmar.hunter.Components.LifeComponent;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.AI.States.Action;
import com.nilbmar.hunter.Enums.EntityType;

/**
 * Created by sysgeek on 5/26/17.
 *
 * Abstract Class: Enemy
 * Purpose: Base class for all Enemies
 */

public class Enemy extends Entity {
    private Brain brain;
    private SteeringAI ai;
    private LifeComponent lifeComp;
    private EnemyType enemyType;

    private boolean destroyed;
    private boolean hasLOStoPlayer;
    private double distanceForLOS;
    private Vision vision;
    private AITarget target;

    public Enemy(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Generic Enemy");
        entityType = EntityType.ENEMY;
        enemyType = null;

        vision = new Vision(screen);

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

        hasLOStoPlayer = false;
        distanceForLOS = 0;

        imageComponent.setPosition(startInWorldX, startInWorldY);

        // AI
    }

    public void setupAnimationComponents() {
        if (enemyType != null) {
            // Set up frame information for animations to use
            framesComp = new FramesComponent(getImageWidth(), getImageHeight());
            //framesComp.setFrames("json/animations/" + enemyType.getName() + "Anim.json");
            framesComp.setAnimFile("json/animations/" + enemyType.getName() + "Anim.json");
            framesComp.setFrames();

            // Set up animations component
            animComp = new AnimationComp(screen, this, framesComp, regionName);

            // Create initial default animation
            charAnim = animComp.makeTexturesIntoAnimation(0.1f, directionComp.getDirection(), currentAction);

            // Used to set bounds at the feet and lower body
            //offsetSpriteY = 8 / HunterOfPoke.PPM;

            imageComponent.setBounds(0, 0, getImageWidth() / HunterOfPoke.PPM, getImageHeight() / HunterOfPoke.PPM);
            // old setup
            //imageComponent.setBounds(boundsBeginX / HunterOfPoke.PPM, boundsBeginY / HunterOfPoke.PPM,
            //      boundsWidth / HunterOfPoke.PPM, boundsHeight / HunterOfPoke.PPM);
        } else {
            Gdx.app.log("setupAnimationComponents", "enemyType is null");
        }
    }

    public void setupBrain(Temperament temperament) {
        switch (temperament) {
            case SCARED:
                brain = new ScaredBrain(this, ai);
                break;
            case CAUTIOUS:

                break;
            case DOCILE:

                break;
            case AGGRESSIVE:

                break;
            case VICIOUS:

                break;
        }
    }

    public LifeComponent getLifeComponent() { return lifeComp; }

    // Based on AI movement
    private void setDirection() {
        // Switch Enemy direction based on position
        // relative to AITarget position
        float dirX = getPosition().x - target.getPosition().x;
        float dirY = getPosition().y - target.getPosition().y;

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

    public double getDistanceForLOS() { return distanceForLOS; }
    public void setDistanceForLOS(double distanceForLOS) {
        this.distanceForLOS = distanceForLOS;
    }
    public void setHasLoStoPlayer(boolean los) { hasLOStoPlayer = los; }

    private void getNewTarget() {
        if (hasLOStoPlayer) {
            setTarget(screen.getPlayer().getPosition());
        } else {
            setTarget(getPosition());
        }
    }

    public void setTarget(Vector2 position) {
        target.setPosition(position);
        ai.setTarget(target);
    }

    public void setSteeringAI() {
        float boundingRadius = 30f;
        if (b2Body != null) {

            if (target == null) {
                target = new AITarget(getPosition());
            }
            ai = new SteeringAI(this, target, boundingRadius);

            /*
            moveComponent = new MoveComponent(b2Body);
            moveCommand = new MoveCommand();
            moveVector = new Vector2(0, 0);
            */
        }
    }

    public void setEnemyType(EnemyType enemyType) { this.enemyType = enemyType; }

    public String getRegionName(DirectionComponent.Direction currentDirection) {
        Array<String> regionNames = framesComp.getAnimData().getWalkFramesArr();

        // THAT DOESN'T HAVE UP/DOWN/SIDE REGIONS
        switch (currentDirection) {
            case UP:
            case UP_LEFT:
            case UP_RIGHT:
                regionName = regionNames.get(0);
                break;
            case DOWN:
            case DOWN_LEFT:
            case DOWN_RIGHT:
                if (regionNames.size >= 2) {
                    regionName = regionNames.get(1);
                }
                break;
            case LEFT:
            case RIGHT:
                if (regionNames.size >= 3) {
                    regionName = regionNames.get(2);
                }
                break;
        }

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

    public void attack() {
        //Gdx.app.log(getName(), " is attacking.");
        /* If no weapon, setTarget to player
            if does have a weapon:
                PICK ANOTHER STEERING BEHAVIOR TO KEEP A CERTAIN DISTANCE
         */

        setTarget(screen.getPlayer().getPosition());
    }

    public Enemy findHelp() {
        Enemy currentClosestEnemy = null;
        Double distToLastEnemy = 999.0;
        Double distToCurrentEnemy = 999.0;

        // Look for the closest enemy,that isn't itself, to help attack
        for (Enemy enemy : screen.getEnemies()) {
            vision.setupRaycast(enemy);
            if (this != enemy && vision.hasLoS(this, enemy, distanceForLOS)) {
                distToCurrentEnemy = vision.getDistance(this.getPosition(), enemy.getPosition());

                // Get shortest distance to help
                if (distToCurrentEnemy < distToLastEnemy) {
                    currentClosestEnemy = enemy;
                    distToLastEnemy = distToCurrentEnemy;
                }
            }
        }

        return currentClosestEnemy;
    }

    @Override
    public void onHit(Entity entity) {
        switch (entity.getEntityType()) {
            case BULLET:
                Gdx.app.log("Enemy Hit", "You got me!");
                lifeComp.loseHitPoints(1);
                Gdx.app.log("On Hit - NearbyHelpQ", brain.getGoalsStack().toString());
                if (!lifeComp.isDead()) {
                    Gdx.app.log("Enemy Death", "AAaaaaaaggghhh! I'm dying!");
                }
                break;
        }
    }

    public void onArrived() {
        // Stops enemy movement until a new target is set
        b2Body.setAwake(false);
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

        if (brain != null) {
            brain.update(hasLOStoPlayer);
        } else {
            setupBrain(Temperament.SCARED);
        }

        // Set the target for and update AI movement
        if (ai != null) {

            //getNewTarget();
            ai.update(deltaTime);

            setDirection();

            /* TODO: FIGURE OUT HOW TO MAKE THEM WALK WITHOUT ANGLES
            if (ai.getLinearVelocity().x >= ai.getLinearVelocity().y) {
                moveVector.set(ai.getLinearVelocity().x, 0);
            } else {
                moveVector.set(0, ai.getLinearVelocity().y);
            }

            moveCommand.setMovement(moveVector);
            moveCommand.execute(this);
            */
        }

        imageComponent.setRegion(getFrame(deltaTime));
    }


}
