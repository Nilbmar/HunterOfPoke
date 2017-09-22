package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.Commands.ChangeCollisionCommand;
import com.nilbmar.hunter.Commands.UseCommand;
import com.nilbmar.hunter.Components.AnimationComp;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Components.FramesComponent;
import com.nilbmar.hunter.Components.InventoryComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Enums.ItemType;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.Action;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Enums.InventorySlotType;
import com.nilbmar.hunter.Tools.AssetHandler;

/**
 * Created by sysgeek on 4/7/17.
 *
 * Entity: Player
 * Purpose: The character the player controls
 */

public class Player extends NewEntity {
    private AssetHandler assets;
    private float stateTimer; // Used to getFrame() of animation
    private int hitPoints;
    private int maxHitPoints;
    private Item holdItem;

    // Textures and Animations
    private FramesComponent framesComp;
    private AnimationComp animComp;
    private Animation charAnim;

    private int walkSteps; // How many images in a full walk cycle
    private boolean updateTextureAtlas = false;

    // Components
    private InventoryComponent inventoryComponent;
    private DirectionComponent directionComp;
    private DirectionComponent.Direction currentDirection;
    private DirectionComponent.Direction previousDirection;

    public Player(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        assets = screen.getAssetsHandler();

        setName("Dlumps");  // TODO: GET THIS FROM USER
        hitPoints = 10;     // TODO: GET FROM GAMEMANAGER
        maxHitPoints = 20;
        entityType = EntityType.PLAYER;
        setImageWidth(20);
        setImageHeight(24);

        directionComp = new DirectionComponent();
        currentDirection = directionComp.getDirection();
        previousDirection = currentDirection;

        currentAction = Action.STILL;
        previousAction = Action.STILL;
        stateTimer = 0;

        // int is inventory slots available
        inventoryComponent = new InventoryComponent(this, 5);
        holdItem = null;

        //regionName = "default"; // Will need to change in charMale and charAlien pack files

        // Set up frame information for animations to use
        framesComp = new FramesComponent(4, getImageWidth(), getImageHeight());

        // Set up animations component
        animComp = new AnimationComp(screen, this, framesComp, regionName);

        // Create Body
        defineBody();

        setCurrentAcceleration(1);
        moveComponent = new MoveComponent(b2Body);

        charAnim = animComp.makeTexturesIntoAnimation(0.1f, currentDirection, currentAction);

        // Used to set bounds at the feet and lower body
        offsetSpriteY = 8 / HunterOfPoke.PPM;

        // TODO: PUT IMAGEWIDTH AND IMAGEHEIGHT INTO JSON
        imageComponent.setBounds(0, 0, getImageWidth() / HunterOfPoke.PPM, getImageHeight() / HunterOfPoke.PPM);
    }

    @Override
    public void prepareToDraw() {

    }

    public DirectionComponent getDirectionComponent() { return directionComp; }

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
        float spawnBulletOffsetX = imageComponent.getX() + imageComponent.getWidth() / 2;
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
        float spawnBulletOffsetY = imageComponent.getY() + imageComponent.getHeight() / 2;
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
    private void setAction() {
        previousAction = currentAction;
        if (moveComponent.isMoving()) {
            currentAction = Action.WALKING;
        } else {
            if (currentAction == Action.USE) {
                if (charAnim.getKeyFrameIndex(stateTimer) == 3) {
                    currentAction = Action.STILL;
                }
            } else {
                currentAction = Action.STILL;
            }
        }

        // Set action in movement component if it has changed
        if (currentAction != previousAction) {
            moveComponent.setAction(currentAction);
        }
    }

    // Change player's TextureAtlas in the AnimationComp
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
              // There's a separate region for still DOWN
              // than for walking DOWN
              switch (currentAction) {
                  case WALKING:
                      regionName = "south2";
                      break;
                  case USE:
                      regionName = "south";
                      break;
                  case STILL:
                  default:
                      regionName = "default";
                      break;
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
        currentDirection = directionComp.getDirection();
        //currentAction = getAction();

        // Only set animation when something changes
        if (currentAction != previousAction || currentDirection != previousDirection || updateTextureAtlas) {
            setUpdateTextureAtlas(false);
            animComp.setRegionName(getRegionName());
            charAnim = animComp.makeTexturesIntoAnimation(0.1f, currentDirection, currentAction);
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
        previousDirection = directionComp.getDirection();
        previousAction = getAction();
        return region;
    }

    @Override
    public void onHit(NewEntity entity) {
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

            currentAction = Action.USE;
        }

    }

    public Item getHoldItem() { return holdItem; }
    public void setHoldItem(Item item) { holdItem = item; }

    // Change maskBits to not collide with enemies
    // or go back to colliding with enemies
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

        directionComp.setDirection(moveComponent.getCurrentDirection());

        imageComponent.setRegion(getFrame(deltaTime));
        setAction(); // Must follow call to getFrame(deltaTime) or USE frames won't work
    }
}
