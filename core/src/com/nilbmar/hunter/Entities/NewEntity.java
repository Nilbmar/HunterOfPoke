package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.nilbmar.hunter.Components.AnimationComp;
import com.nilbmar.hunter.Components.BodyComponent;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Components.FramesComponent;
import com.nilbmar.hunter.Components.ImageComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Components.TimerComponent;
import com.nilbmar.hunter.Enums.Action;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Enums.ItemType;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.AssetHandler;

/**
 * Created by sysgeek on 8/22/17.
 *
 * Entity that uses an extension of Image
 * as a component instead of extending Image directly
 */

public abstract class NewEntity {
    protected PlayScreen screen;
    protected World world;
    protected Body b2Body;


    protected AssetHandler assets;
    protected TextureAtlas atlas;
    protected boolean updateTextureAtlas = false;

    // Components
    protected FramesComponent framesComp;
    protected AnimationComp animComp;
    protected Animation charAnim;
    protected ImageComponent imageComponent;
    protected BodyComponent bodyComponent;
    protected MoveComponent moveComponent;
    protected TimerComponent timerComponent;
    protected DirectionComponent directionComp;

    protected float startInWorldX;
    protected float startInWorldY;

    // Variables for graphics/bounds
    protected String regionName;
    protected int regionBeginX;
    protected int regionBeginY;
    protected int regionWidth;
    protected int regionHeight;
    protected float boundsBeginX;
    protected float boundsBeginY;
    protected float boundsWidth;
    protected float boundsHeight;
    protected float offsetSpriteX;
    protected float offsetSpriteY;
    private int imageWidth;
    private int imageHeight;

    protected EntityType entityType;

    protected float deltaTime; // Used for TimerComponent - set in update()

    protected int acceleration;
    protected int baseAcceleration;
    protected int currentAcceleration;

    protected float stateTimer;
    protected Action currentAction;
    protected Action previousAction;

    protected String name;

    public NewEntity(PlayScreen screen, float startInWorldX, float startInWorldY) {
        this.screen = screen;
        world = this.screen.getWorld();

        this.startInWorldX = startInWorldX;
        this.startInWorldY = startInWorldY;

        offsetSpriteX = 0;
        offsetSpriteY = 0;

        assets = screen.getAssetsHandler();

        imageComponent = new ImageComponent(startInWorldX, startInWorldY);
        imageComponent.setPosition(startInWorldX, startInWorldY);

        baseAcceleration = 1;

        name = "Entity That Shan't Be Named";

        // Will need to change make sure pack files
        // have a "default" region, or set in constructor
        // before setFramesComponent()
        regionName = "default";

        bodyComponent = new BodyComponent();
        //defineBody();
    }

    public abstract String getRegionName(DirectionComponent.Direction currentDirection);

    // Change TextureAtlas in the AnimationComp
    public void setUpdateTextureAtlas(boolean updateTextureAtlas) {
        switch (entityType) {
            case PLAYER:
                animComp.setAtlas(assets.getPlayerAtlas());
                break;
            case ENEMY:
                animComp.setAtlas(assets.getEnemyAtlas());
                break;
            case BULLET:
                animComp.setAtlas(assets.getBulletAtlas());
                break;
            case ITEM:
                animComp.setAtlas(assets.getItemAtlas());
                break;
        }
        this.updateTextureAtlas = updateTextureAtlas; // Set so it will change on update() in getFrame()
    }

    public TextureRegion getFrame(float deltaTime) {
        TextureRegion region;
        //currentDirection = directionComp.getDirection();

        // Only set animation when something changes
        if (currentAction != previousAction || directionComp.getDirection() != directionComp.getPreviouDirection() || updateTextureAtlas) {
            setUpdateTextureAtlas(false);
            animComp.setRegionName(getRegionName(directionComp.getDirection()));
            charAnim = animComp.makeTexturesIntoAnimation(0.1f, directionComp.getDirection(), currentAction);
        }

        // Get Key Frame
        // If Walking, loop the animation, otherwise, pause it on last frame
        switch (currentAction) {
            case WALKING:
                region = (TextureRegion) charAnim.getKeyFrame(stateTimer, true);
                break;
            case USE:
                // TODO: SET A DIFFERENT REGION AFTER MAKING NEW SPRITESHEETS
                region = (TextureRegion) charAnim.getKeyFrame(stateTimer, false);
                break;
            case STILL:
            default:
                region = (TextureRegion) charAnim.getKeyFrame(stateTimer, false);
                break;
        }

        // Flip region based on LEFT/RIGHT directions
        // (includes UP/DOWN variants)
        switch (directionComp.getDirection()) {
            case LEFT:
            case UP_LEFT:
            case DOWN_LEFT:
                if (!region.isFlipX()) {
                    region.flip(true, false);
                }
                break;

            case RIGHT:
            case UP_RIGHT:
            case DOWN_RIGHT:
                if (region.isFlipX()) {
                    region.flip(true, false);
                }
                break;
        }
        stateTimer = (directionComp.getDirection() == directionComp.getPreviouDirection() && currentAction == previousAction)
                ? stateTimer + deltaTime : 0;

        if (!getName().contains("Dlumps")) {
            Gdx.app.log("StateTimer", getName() + " " + directionComp.getDirection() + "/" + directionComp.getPreviouDirection()
                    + " " + currentAction + "/" + previousAction);
        }

        directionComp.setDirection(directionComp.getDirection());
        previousAction = getAction();
        return region;
    }
    public DirectionComponent getDirectionComponent() { return directionComp; }

    public Action getAction() { return currentAction; }
    protected void setAction(Action currentAction) {
        //previousAction = currentAction;
        this.currentAction = currentAction;
    }


    public abstract float getSpawnOtherX();
    public abstract float getSpawnOtherY();
    public abstract void prepareToDraw();

    protected void setName(String name) {
        this.name = name;
    }

    public int getImageWidth() { return imageWidth; }
    protected void setImageWidth(int imageWidth) { this.imageWidth = imageWidth; }
    public int getImageHeight() { return imageHeight; }
    protected void setImageHeight(int imageHeight) { this.imageHeight = imageHeight; }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setRegionBeginX(int regionBeginX) {
        this.regionBeginX = regionBeginX;
    }

    public void setRegionBeginY(int regionBeginY) {
        this.regionBeginY = regionBeginY;
    }

    public void setRegionWidth(int regionWidth) {
        this.regionWidth = regionWidth;
    }

    public void setRegionHeight(int regionHeight) {
        this.regionHeight = regionHeight;
    }

    public void setBoundsBeginX(float boundsBeginX) {
        this.boundsBeginX = boundsBeginX;
    }

    public void setBoundsBeginY(float boundsBeginY) {
        this.boundsBeginY = boundsBeginY;
    }

    public void setBoundsWidth(float boundsWidth) {
        this.boundsWidth = boundsWidth;
    }

    public void setBoundsHeight(float boundsHeight) {
        this.boundsHeight = boundsHeight;
    }

    public void setOffsetSpriteX(float offsetSpriteX) {
        this.offsetSpriteX = offsetSpriteX;
    }

    public void setOffsetSpriteY(float offsetSpriteY) {
        this.offsetSpriteY = offsetSpriteY;
    }

    public EntityType getEntityType() { return entityType; }
    public String getName() { return name; }


    public MoveComponent getMoveComponent() { return moveComponent; }
    protected void setTimerComponent(float setTimer, ItemType itemType) {
        // TODO: CHANGE TIMER COMPONENT WHEN TESTING
        timerComponent = new TimerComponent(this, setTimer, itemType, deltaTime);
    }

    // Boosts from items or reset
    public int getBaseAcceleration() { return baseAcceleration; }
    public int getCurrentAcceleration() { return currentAcceleration; }
    public void setCurrentAcceleration(int currentAcceleration) {
        this.currentAcceleration = currentAcceleration;
    }

    // TODO: MAKE THIS ABSTRACT AFTER SETTING UP MORE
    public void onHit(NewEntity entity) {}

    public Body getB2Body() { return b2Body; }

    protected void createBody(float x, float y) {
        b2Body = world.createBody(bodyComponent.getBodyDef(BodyDef.BodyType.DynamicBody,
                x, y));
    }

    protected void defineShape() {
        //bodyComponent.setFixtureDef(Shape.Type.Circle, 5); // CircleShape - radius of 5
        bodyComponent.setFixtureDef(Shape.Type.Polygon, 4, 4); // PolygonShape - Set as Box
    }

    protected abstract void defineBits(short maskBits);
    protected abstract void defineBody();

    protected void finalizeBody() {
        // false = is the fixture a sensor
        bodyComponent.finalizeFixture(false);
        b2Body.createFixture(bodyComponent.getFixtureDef()).setUserData(this);

        bodyComponent.dispose();
    }

    public void update(float deltaTime) {
        this.deltaTime = deltaTime;

        // Used to set the b2Body's shape lower on the sprite
        // so only lower-body collides with objects
        imageComponent.setPosition(
                b2Body.getPosition().x - imageComponent.getWidth() / 2 + offsetSpriteX,
                b2Body.getPosition().y - imageComponent.getHeight() / 2 + offsetSpriteY
        );

        /*
        setPosition(b2Body.getPosition().x - getWidth() / 2 + offsetSpriteX,
                b2Body.getPosition().y - getHeight() / 2 + offsetSpriteY);
        */
    }

    public void draw(Batch batch) {
        //super.draw(batch);
        imageComponent.draw(batch);
    }
}
