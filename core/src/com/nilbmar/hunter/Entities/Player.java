package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.Commands.ChangeCollisionCommand;
import com.nilbmar.hunter.Commands.Command;
import com.nilbmar.hunter.Commands.UseCommand;
import com.nilbmar.hunter.Components.AnimationComponent;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Components.FramesComponent;
import com.nilbmar.hunter.Components.InventoryComponent;
import com.nilbmar.hunter.Components.LifeComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Observers.Observer;
import com.nilbmar.hunter.Observers.Subject;
import com.nilbmar.hunter.Scenes.Hud;
import com.nilbmar.hunter.Scenes.HudPieces.UserInfoHUD;
import com.nilbmar.hunter.Timers.AttackTimer;
import com.nilbmar.hunter.Timers.TimerComponent;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Enums.ItemType;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.AI.States.Action;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Enums.InventorySlotType;
import com.nilbmar.hunter.Timers.ItemTimer;
import com.nilbmar.hunter.Timers.ResetCollisionTimer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sysgeek on 4/7/17.
 *
 * Entity: Player
 * Purpose: The character the player controls
 */

public class Player extends Entity implements Subject {
    //private Array<Observer> observers;
    private HashMap<String, Observer> observers;

    private AttackTimer attackTimer;
    private float howOftenCanAttack;

    // Components
    private InventoryComponent inventoryComponent;
    private LifeComponent lifeComp;

    private Item holdItem;

    public Player(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Dlumps");  // TODO: GET THIS FROM USER

        lifeComp = new LifeComponent();
        lifeComp.setHitPoints(10);     // TODO: GET FROM GAMEMANAGER
        lifeComp.setMaxHitPoints(20);

        entityType = EntityType.PLAYER;
        setImageWidth(20);
        setImageHeight(24);

        directionComp = new DirectionComponent();

        currentAction = Action.STILL;
        previousAction = Action.STILL;
        stateTimer = 0;


        timerMap = new HashMap<TimerComponent.TimerType, TimerComponent>();
        howOftenCanAttack = 0.5f;
        attackTimer = null;

        // int is inventory slots available
        inventoryComponent = new InventoryComponent(this, 5);
        holdItem = null;

        //regionName = "default";

        // Set up frame information for animations to use
        framesComp = new FramesComponent(getImageWidth(), getImageHeight());
        framesComp.setAnimFile("json/animations/playerAnim.json");
        framesComp.setFrames();

        // Set up animations component
        animComp = new AnimationComponent(screen, this, framesComp, regionName);

        // Create Body
        defineBody();

        setCurrentAcceleration(1);
        moveComponent = new MoveComponent(b2Body);

        // Create initial default animation
        charAnim = animComp.makeTexturesIntoAnimation(0.1f, directionComp.getDirection(), currentAction);

        // Used to set bounds at the feet and lower body
        offsetSpriteY = 8 / HunterOfPoke.PPM;


        // TODO: PUT IMAGEWIDTH AND IMAGEHEIGHT INTO JSON
        imageComponent.setBounds(0, 0, getImageWidth() / HunterOfPoke.PPM, getImageHeight() / HunterOfPoke.PPM);
    }

    @Override
    public void prepareToDraw() {

    }

    @Override
    public String getRegionName(DirectionComponent.Direction currentDirection) {
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

    @Override
    public float getSpawnOtherX() {
        float spawnBulletOffsetX = imageComponent.getX() + imageComponent.getWidth() / 2;
        float offset = 10 / HunterOfPoke.PPM;
        switch (directionComp.getDirection()) {
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

    @Override
    public float getSpawnOtherY() {
        float spawnBulletOffsetY = imageComponent.getY() + imageComponent.getHeight() / 2;
        float offset = 10 / HunterOfPoke.PPM;
        switch (directionComp.getDirection()) {
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


    private void setAction() {
        previousAction = currentAction;
        if (moveComponent.isMoving()) {
            currentAction = Action.WALKING;
        } else {
            if (currentAction == Action.USE) { // TODO: SET THIS NUMBER
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



    public LifeComponent getLifeComp() { return lifeComp; }
    @Override
    public void onHit(Entity entity) {
        switch (entity.getEntityType()) {
            case ENEMY:
            case BULLET:
                // Call timer, after timer, then resetCollision
                // Otherwise game crashes trying to reset collision while still colliding
                addTimer(0.1f, TimerComponent.TimerType.REMOVE_COLLISION);

                // TODO: GET AMOUNT OF HP TO LOSE
                lifeComp.loseHitPoints(1);

                if (lifeComp.isDead()) {
                    Gdx.app.log("Player Death", "What a world!");
                }
                break;
        }
        Gdx.app.log("Player HP = " + lifeComp.getHitPoints(), "Ouch! " + entity.getName() + " hit me!");
        notifyObserver();
    }

    public void onPickup(Item item) {
        switch (item.getItemType()){
            case DEATH:
                // TODO: KILL ME!
                timerMap.put(TimerComponent.TimerType.ITEM, null);
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

            /* TODO: THIS WON'T SET THE HUD PROPERLY IF GET HIT BEFORE REACHING ITEM
             * AND DOESN'T UNSET WHEN TIMER ENDS
             */
            if (observers.containsKey(Hud.HudObservers.USER_INFO.toString())) {
                Gdx.app.log("user info", holdItem.getName());
                UserInfoHUD uiHud = (UserInfoHUD) observers.get(Hud.HudObservers.USER_INFO.toString());
                uiHud.setInfo(holdItem.getName());
                observers.get(Hud.HudObservers.USER_INFO.toString()).update();
            }

            notifyObserver();
        }
    }

    public Item getHoldItem() { return holdItem; }
    public void setHoldItem(Item item) { holdItem = item; }

    public AttackTimer getAttackTimer() { return attackTimer; }
    public void setAttackTimer(float offsetTimer) {
        float setTime = howOftenCanAttack + offsetTimer;
        attackTimer = new AttackTimer(this, setTime, deltaTime);
    }

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
                    | HunterOfPoke.ITEM_BIT
                    | HunterOfPoke.BULLET_BIT;
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
        defineBits((short) (HunterOfPoke.GROUND_BIT | HunterOfPoke.ENEMY_BIT
                | HunterOfPoke.ITEM_BIT | HunterOfPoke.BULLET_BIT));
        finalizeBody();
    }

    private void addItemTimer(float setTimer, ItemType itemType) {
        timerMap.put(TimerComponent.TimerType.ITEM, new ItemTimer(this, setTimer, itemType, deltaTime));
    }
    private void addTimer(float setTimer, TimerComponent.TimerType timerType) {
        switch (timerType) {
            case REMOVE_COLLISION:
                timerMap.put(TimerComponent.TimerType.REMOVE_COLLISION,
                        new ResetCollisionTimer(this, setTimer, deltaTime, TimerComponent.TimerType.REMOVE_COLLISION));
                break;
            case RESET_COLLISION:
                timerMap.put(TimerComponent.TimerType.RESET_COLLISION,
                        new ResetCollisionTimer(this, setTimer, deltaTime, TimerComponent.TimerType.RESET_COLLISION));
                break;
        }
    }

    private void checkTimer(float deltaTime) {
        if (timerMap != null) {
            for (Map.Entry<TimerComponent.TimerType, TimerComponent> entry : timerMap.entrySet()) {
                if (entry.getValue() != null) {

                    // Where the real code begins
                    if (entry.getValue().timerHasEnded()) {

                        Command command = null;
                        switch (entry.getValue().getTimerType()) {
                            case REMOVE_COLLISION:
                                Gdx.app.log("Update", "Removing Collision");
                                command = new ChangeCollisionCommand();
                                command.execute(this);
                                timerMap.put(TimerComponent.TimerType.REMOVE_COLLISION, null);

                                // Add a timer to reset the collision to enabled
                                //addItemTimer(2f, ItemType.RESET_COLLISION);
                                addTimer(2f, TimerComponent.TimerType.RESET_COLLISION);
                                break;

                            case RESET_COLLISION:
                                Gdx.app.log("Update", "Resetting Collision");
                                command = new ChangeCollisionCommand();
                                ((ChangeCollisionCommand) command).undo(this);
                                timerMap.put(TimerComponent.TimerType.RESET_COLLISION, null);
                                break;
                        }
                    } else {
                        entry.getValue().update(deltaTime);
                    }
                }
            }
        }

    }

    public void setupHudObservers(Hud hud) {
        observers = new HashMap<String, Observer>();
        addObserver(hud.getObserver(Hud.HudObservers.LIFE));
        addObserver(hud.getObserver(Hud.HudObservers.USER_INFO));
        addObserver(hud.getObserver(Hud.HudObservers.SCORE));
    }

    @Override
    public void addObserver(Observer observer) {
        observers.put(observer.getType(), observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        //int obsToDelete = observers.indexOf(observer, false);
        //observers.removeIndex(obsToDelete);

        observers.remove(observer);
        //observers.removeValue(observer, false);
    }

    @Override
    public void notifyObserver() {
        HashMap.Entry<String, Observer> entry;
        Iterator<HashMap.Entry<String, Observer>> it = observers.entrySet().iterator();
        while (it.hasNext()) {
            entry = it.next();
            entry.getValue().update();
            it.remove();
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Check if its ok to resetCollision()
        checkTimer(deltaTime);
        if (attackTimer != null) {
            attackTimer.update(deltaTime);
        }

        directionComp.setDirection(moveComponent.getCurrentDirection());

        imageComponent.setRegion(getFrame(deltaTime));
        setAction(); // Must follow call to getFrame(deltaTime) or USE frames won't work
    }
}
