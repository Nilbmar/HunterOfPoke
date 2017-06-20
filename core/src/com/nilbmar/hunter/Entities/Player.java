package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.Commands.AccelerationCommand;
import com.nilbmar.hunter.Commands.UpdateHudCommand;
import com.nilbmar.hunter.Commands.UseCommand;
import com.nilbmar.hunter.Components.AnimationComponent;
import com.nilbmar.hunter.Components.FramesComponent;
import com.nilbmar.hunter.Components.InventoryComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.Enums.Action;
import com.nilbmar.hunter.Tools.Enums.Direction;
import com.nilbmar.hunter.Tools.Enums.EntityType;
import com.nilbmar.hunter.Tools.Enums.HudLabels;
import com.nilbmar.hunter.Tools.Enums.ItemType;

/**
 * Created by sysgeek on 4/7/17.
 */

public class Player extends Entity {
    private float stateTimer; // Used to getFrame() of animation
    private int hitPoints;
    private int maxHitPoints;
    private Item holdItem;

    // Textures and Animations
    private FramesComponent framesComp;
    private AnimationComponent animComp;
    private Animation charWalk; // TODO: CHANGE TO charAnim
    private int walkSteps; // How many images in a full walk cycle

    // Componenets
    private InventoryComponent inventoryComponent;

    private UpdateHudCommand hudUpdate;
    private AccelerationCommand accelerationCommand;

    public Player(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Dlumps");  // TODO: GET THIS FROM USER
        hitPoints = 10;     // TODO: GET FROM GAMEMANGER
        maxHitPoints = 20;
        entityType = EntityType.PLAYER;

        currentDirection = Direction.DOWN;
        previousDirection = Direction.DOWN;
        currentAction = Action.STILL;
        previousAction = Action.STILL;
        stateTimer = 0;

        // int is inventory slots available
        inventoryComponent = new InventoryComponent(this, 5);
        holdItem = null;

        // Setup Animations
        //regionName = "default"; // Will need to change in charMale and charAlien pack files
        setFramesComponent();
        animComp = new AnimationComponent(screen, framesComp, regionName);

        // Create Body
        defineBody();

        setCurrentAcceleration(1);
        movement = new MoveComponent(b2Body);

        charWalk = new Animation(0.1f, animComp.getAnimation(currentDirection, currentAction));

        // TODO: PROBABLY CAN REMOVE CHARSTILL AND SETREGION
        // Set up default facing sprite
        TextureRegion charStill = new TextureRegion(screen.getPlayerAtlas().findRegion(regionName), 0, 0, 20, 24);
        setBounds(0, 0, 20 / HunterOfPoke.PPM, 24 / HunterOfPoke.PPM);
        setRegion(charStill);
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

    public float getSpawnOtherX() {
        float spawnBulletOffsetX = getX() + getWidth() / 2;
        float offset = 10 / HunterOfPoke.PPM;
        switch (currentDirection) {
            case UP:
                // TODO: DON'T WANT TO CHANGE ANYTHING YET, MIGHT LATER
                //spawnBulletOffsetX = spawnBulletOffsetX;
                break;
            case UP_LEFT:
                spawnBulletOffsetX = spawnBulletOffsetX - offset;
                break;
            case UP_RIGHT:
                spawnBulletOffsetX = spawnBulletOffsetX + offset;
                break;
            case DOWN:
                // TODO: DON'T WANT TO CHANGE ANYTHING YET, MIGHT LATER
                //spawnBulletOffsetX = spawnBulletOffsetX;
                break;
            case DOWN_LEFT:
                spawnBulletOffsetX = spawnBulletOffsetX - offset;
                break;
            case DOWN_RIGHT:
                spawnBulletOffsetX = spawnBulletOffsetX + offset;
                break;
            case LEFT:
                spawnBulletOffsetX = spawnBulletOffsetX - offset;
                break;
            case RIGHT:
                spawnBulletOffsetX = spawnBulletOffsetX + offset;
                break;
        }
        return spawnBulletOffsetX;
    }
    public float getSpawnOtherY() {
        float spawnBulletOffsetY = getY() + getHeight() / 2;
        float offset = 10 / HunterOfPoke.PPM;
        switch (currentDirection) {
            case UP:
                spawnBulletOffsetY = spawnBulletOffsetY + offset;
                break;
            case UP_LEFT:
                spawnBulletOffsetY = spawnBulletOffsetY + offset;
                break;
            case UP_RIGHT:
                spawnBulletOffsetY = spawnBulletOffsetY + offset;
                break;
            case DOWN:
                spawnBulletOffsetY = spawnBulletOffsetY - offset;
                break;
            case DOWN_LEFT:
                spawnBulletOffsetY = spawnBulletOffsetY - offset;
                break;
            case DOWN_RIGHT:
                spawnBulletOffsetY = spawnBulletOffsetY - offset;
                break;
            case LEFT:
                //spawnBulletOffsetY = spawnBulletOffsetY - offset;
                break;
            case RIGHT:
                //spawnBulletOffsetY = spawnBulletOffsetY - offset;
                break;

        }
        return spawnBulletOffsetY;
    }

    private Action getAction() { return currentAction; }
    private void setAction(Action act) {
        previousAction = currentAction;
        currentAction = act;
    }

    private String getRegionName() {
      switch (currentDirection) {
          case UP:
              regionName = "1_north";
              break;
          case UP_LEFT:
          case UP_RIGHT:
              regionName = "1_diagup";
              break;
          case DOWN:
              if (currentAction == Action.WALKING) {
                  regionName = "1_south";
              } else {
                  regionName = "default";
              }
              break;
          case DOWN_LEFT:
          case DOWN_RIGHT:
              regionName = "1_diagdown";
              break;
          case LEFT:
          case RIGHT:
              regionName = "1_side";
              break;
      }

        return regionName;
    }

    private TextureRegion getFrame(float deltaTime) {
        TextureRegion region;
        currentDirection = getDirection();
        currentAction = getAction();

        // Only set animation when something changes
        if (currentAction != previousAction || currentDirection != previousDirection) {
            animComp.setRegionName(getRegionName());
            charWalk = new Animation(0.1f, animComp.getAnimation(currentDirection, currentAction));
        }

        // true - looping
        if (currentAction == Action.WALKING) {
            region = (TextureRegion) charWalk.getKeyFrame(stateTimer, true);
        } else {
            region = (TextureRegion) charWalk.getKeyFrame(stateTimer, false);
        }

        // Flip region based on LEFT/RIGHT directions
        // (includes UP/DOWN variants)
        switch (currentDirection) {
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

        stateTimer = (currentDirection == previousDirection && currentAction == previousAction)
                ? stateTimer + deltaTime : 0;
        previousDirection = getDirection();
        previousAction = getAction();
        return region;
    }

    private void setFramesComponent() {
        walkSteps = 4;
        framesComp = new FramesComponent(walkSteps);

        // TODO: I'M NOT SURE HOW MANY NEED TO SET FOR WALK FRAMES
        // SHOULD I JUST SET Y AND TAKE CARE OF X IN LOOP?
        // OR MAKE THE FC TAKE THIS INT AND LOOP IN setWalkFrames()?
        //int numOfWalkFramesPerDir = 3;
        int scaleSizeX = 20;
        int scaleSizeY = 24;

        /* YOU MUST GO IN ORDER OF UP, UP_LEFT, DOWN, DOWN_LEFT, LEFT*/
        // Multiple steps in walk cycles
        for (int x = 0; x < walkSteps; x++) {
            framesComp.setWalkFrames(Direction.UP, x * scaleSizeX, 0);
        }
        for (int x = 0; x < walkSteps; x++) {
            framesComp.setWalkFrames(Direction.UP_LEFT, x * scaleSizeX, 0);
        }

        for (int x = 0; x < walkSteps; x++) {
            framesComp.setWalkFrames(Direction.DOWN, x * scaleSizeX, 0);
        }

        for (int x = 0; x < walkSteps; x++) {
            framesComp.setWalkFrames(Direction.DOWN_LEFT, x * scaleSizeX, 0);
        }

        for (int x = 0; x < walkSteps; x++) {
            framesComp.setWalkFrames(Direction.LEFT, x * scaleSizeX, 0);
        }

        framesComp.setUseFrames(Direction.UP, scaleSizeX, 0);
        framesComp.setUseFrames(Direction.UP_LEFT, scaleSizeX, 0); // TODO: THESE MAY HAVE CHANGED IT
        framesComp.setUseFrames(Direction.DOWN, 0, 0);
        framesComp.setUseFrames(Direction.DOWN_LEFT, 0, 0); // TODO: THESE MAY HAVE CHANGED IT
        framesComp.setUseFrames(Direction.LEFT, scaleSizeX, 0);

        framesComp.setStillFrames(Direction.UP, scaleSizeX, 0);
        framesComp.setStillFrames(Direction.UP_LEFT, scaleSizeX, 0);
        framesComp.setStillFrames(Direction.DOWN, 0, 0);
        framesComp.setStillFrames(Direction.DOWN_LEFT, scaleSizeX, 0);
        framesComp.setStillFrames(Direction.LEFT, scaleSizeX, 0);
    }

    public void onPickup(Item item) {
        switch (item.getItemType()){
            case DEATH:
                // TODO: KILL ME!
                timerComponent = null;
                break;
            default:
                // TODO: CHANGE WHEN ADD IN NEW INVENTORY TYPES
                inventoryComponent.placeInInventory(item, item.getAddToCountOnPickup());
                break;
        }

        // TODO: SHOULD DO BY SWITCHING BETWEEN INVENTORY ITEMS
        setHoldItem(item);
    }

    public void useItem() {
        String newText = "";
        switch (holdItem.getItemType()) {
            case ACCELERATION:
                timerComponent = null;
                accelerationCommand = new AccelerationCommand(this, 1);
                accelerationCommand.execute(this);
                newText = "Speed Boost!";
                break;
        }
        UseCommand use = new UseCommand(holdItem);
        use.execute(this);

        if (holdItem.getItemEffectTime() > 0) {
            setTimerComponent(holdItem.getItemEffectTime(), holdItem.getItemType());
        }

        hudUpdate = new UpdateHudCommand(screen.getHUD(), HudLabels.USER_INFO, newText);
        hudUpdate.execute(this);
    }

    public Item getHoldItem() { return holdItem; }
    public void setHoldItem(Item item) { holdItem = item; }

    /* TODO: CURRENTLY ONLY RESETS COLLISION TO COLLISION WITH ENEMY_BIT
     * This was done to test the reset by disallowing ground collision
     * Change this so it won't collide with enemies ore their bullets
     * for invincibility frames, but should still collide with ground */
    public void resetCollision() {
        float newX = b2Body.getPosition().x;
        float newY = b2Body.getPosition().y;
        world.destroyBody(b2Body);

        short maskBits = HunterOfPoke.ENEMY_BIT;

        createBody(newX, newY);
        defineShape();
        defineBits(maskBits);
        finalizeBody();
    }


    @Override
    protected void defineShape() {
        bodyComponent.setFixtureDef(Shape.Type.Circle, 5); // CircleShape - radius of 5
        //bodyComponent.setFixtureDef(Shape.Type.Polygon, 4, 3); // PolygonShape - Set as Box
    }

    @Override
    protected void defineBits(short maskBits) {
        bodyComponent.setCategoryBit(HunterOfPoke.PLAYER_BIT);
        bodyComponent.setMaskBits(maskBits);
    }

    @Override
    protected void defineBody() {
        createBody(startInWorldX, startInWorldY);
        defineShape();
        defineBits((short) (HunterOfPoke.GROUND_BIT | HunterOfPoke.ENEMY_BIT | HunterOfPoke.ITEM_BIT));
        finalizeBody();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Used to set the b2Body's shape lower on the sprite
        // so only lower-body collides with objects
        float offsetSpriteY = 8 / HunterOfPoke.PPM;

        setPosition(b2Body.getPosition().x - getWidth() / 2,
                b2Body.getPosition().y - getHeight() / 2 + offsetSpriteY);
        setDirection(movement.getCurrentDirection());
        setAction(movement.getCurrentAction());
        setRegion(getFrame(deltaTime));

        // TODO: THIS NEEDS TO BE SIMPLIFIED
        if (timerComponent != null) {
            if (timerComponent.endTimer()) {
                ItemType type = ItemType.ACCELERATION;
                switch (type){
                    case ACCELERATION:
                        accelerationCommand.undo(this);
                        accelerationCommand = null;
                        timerComponent = null;
                        hudUpdate.setLabelToUpdate(HudLabels.USER_INFO);
                        hudUpdate.setNewText("");
                        hudUpdate.execute(this);
                        break;
                }

            } else {
                timerComponent.update(deltaTime);
            }
        }
    }
}
