package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.Commands.AccelerationCommand;
import com.nilbmar.hunter.Commands.ChangeCollisionCommand;
import com.nilbmar.hunter.Commands.UpdateHudCommand;
import com.nilbmar.hunter.Commands.UseCommand;
import com.nilbmar.hunter.Components.AnimationComponent;
import com.nilbmar.hunter.Components.FramesComponent;
import com.nilbmar.hunter.Components.InventoryComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Enums.ItemType;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.Action;
import com.nilbmar.hunter.Enums.Direction;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Enums.InventorySlotType;
import com.nilbmar.hunter.Tools.AssetHandler;

/**
 * Created by sysgeek on 4/7/17.
 *
 * Entity: Player
 * Purpose: The character the player controls
 */

public class Player extends Entity {
    private AssetHandler assets;
    private float stateTimer; // Used to getFrame() of animation
    private int hitPoints;
    private int maxHitPoints;
    private Item holdItem;

    // Textures and Animations
    private FramesComponent framesComp;
    private AnimationComponent animComp;
    private Animation charWalk; // TODO: CHANGE TO charAnim
    private int walkSteps; // How many images in a full walk cycle
    private boolean updateTextureAtlas = false;

    // Componenets
    private InventoryComponent inventoryComponent;

    private UpdateHudCommand hudUpdate;
    private AccelerationCommand accelerationCommand;

    public Player(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        assets = screen.getAssetsHandler();

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
        animComp = new AnimationComponent(screen, EntityType.PLAYER, framesComp, regionName);

        // Create Body
        defineBody();

        setCurrentAcceleration(1);
        moveComponent = new MoveComponent(b2Body);

        charWalk = new Animation(0.1f, animComp.getAnimation(currentDirection, currentAction));

        // TODO: PROBABLY CAN REMOVE CHARSTILL AND SETREGION
        // Set up default facing sprite
        offsetSpriteY = 8 / HunterOfPoke.PPM;
        TextureRegion charStill = new TextureRegion(assets.getPlayerAtlas().findRegion(regionName), 0, 0, 20, 24);
        setBounds(0, 0, 20 / HunterOfPoke.PPM, 24 / HunterOfPoke.PPM);
        setRegion(charStill);
    }

    @Override
    public void prepareToDraw() {

    }

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
    public void setMaxHitPoints(int maxHitPoints) { this.maxHitPoints = maxHitPoints; }

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

    // Change player's TextureAtlas in the AnimationComponent
    public void setUpdateTextureAtlas(boolean updateTextureAtlas) {
        animComp.setAtlas(assets.getPlayerAtlas());
        this.updateTextureAtlas = updateTextureAtlas; // Set so it will change on update() in getFrame()
    }
    private String getRegionName() {
      switch (currentDirection) {
          case UP:
              regionName = "north";
              break;
          case UP_LEFT:
          case UP_RIGHT:
              regionName = "diagup";
              break;
          case DOWN:
              if (currentAction == Action.WALKING) {
                  regionName = "south";
              } else {
                  regionName = "default";
              }
              break;
          case DOWN_LEFT:
          case DOWN_RIGHT:
              regionName = "diagdown";
              break;
          case LEFT:
          case RIGHT:
              regionName = "side";
              break;
      }

        return regionName;
    }

    private TextureRegion getFrame(float deltaTime) {
        TextureRegion region;
        currentDirection = getDirection();
        currentAction = getAction();

        // Only set animation when something changes
        if (updateTextureAtlas || currentAction != previousAction || currentDirection != previousDirection) {
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

    @Override
    public void onHit(Entity entity) {
        Gdx.app.log(getName(), "Oof! That smarts. Go away " + entity.getName() + ".");
        // Call timer, after timer, then resetCollision
        // Otherwise game crashes trying to reset collision while still colliding
        setTimerComponent(0.5f, ItemType.REMOVE_COLLISION);
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
        InventorySlotType slotType = holdItem.getInventoryType();


        if (inventoryComponent.countInInventory(slotType) > 0) {
            Gdx.app.log("Inventory", getEntityType() + " used " + slotType);
            newText = holdItem.getName();

            UseCommand use = new UseCommand(holdItem);
            use.execute(this);
            inventoryComponent.reduceInventory(holdItem.getInventoryType());
        }

    }

    public Item getHoldItem() { return holdItem; }
    public void setHoldItem(Item item) { holdItem = item; }

    /* TODO: CURRENTLY ONLY RESETS COLLISION TO COLLISION WITH ENEMY_BIT
     * This was done to test the reset by disallowing ground collision
     * Change this so it won't collide with enemies ore their bullets
     * for invincibility frames, but should still collide with ground */
    public void resetCollision(boolean collideWithEnemies) {
        float newX = b2Body.getPosition().x;
        float newY = b2Body.getPosition().y;
        world.destroyBody(b2Body);
        short maskBits = HunterOfPoke.NOTHING_BIT;

        if (collideWithEnemies) {
            maskBits = (short) HunterOfPoke.GROUND_BIT
                    | HunterOfPoke.ENEMY_BIT
                    | HunterOfPoke.ITEM_BIT;
        } else {
            maskBits = (short) HunterOfPoke.GROUND_BIT | HunterOfPoke.ITEM_BIT;
        }

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

        // Check if its ok to resetCollision()
        if (timerComponent != null) {
            if (timerComponent.endTimer()) {
                if (timerComponent.getItemType() == ItemType.REMOVE_COLLISION) {
                    Gdx.app.log("Update", "Removing Collision");
                    ChangeCollisionCommand reset = new ChangeCollisionCommand();
                    reset.execute(this);
                    timerComponent = null;
                    setTimerComponent(2f, ItemType.RESET_COLLISION);
                } else if (timerComponent.getItemType() == ItemType.RESET_COLLISION) {
                    Gdx.app.log("Update", "Resetting Collision");
                    ChangeCollisionCommand reset = new ChangeCollisionCommand();
                    reset.undo(this);
                    timerComponent = null;
                }
            } else {
                timerComponent.update(deltaTime);
            }
        }

        setDirection(moveComponent.getCurrentDirection());
        setAction(moveComponent.getCurrentAction());
        setRegion(getFrame(deltaTime));
    }
}
