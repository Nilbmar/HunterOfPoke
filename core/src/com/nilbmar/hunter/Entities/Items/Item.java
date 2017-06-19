package com.nilbmar.hunter.Entities.Items;

import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.Enums.Action;
import com.nilbmar.hunter.Tools.Enums.Direction;
import com.nilbmar.hunter.Tools.Enums.EntityType;
import com.nilbmar.hunter.Tools.Enums.InventorySlotType;
import com.nilbmar.hunter.Tools.Enums.TimerType;

/**
 * Created by sysgeek on 6/12/17.
 */

public abstract class Item extends Entity {
    protected boolean destroyed;
    protected float stateTimer;
    protected TimerType timerType;
    protected float itemEffectTime;
    protected InventorySlotType inventoryType;
    protected int inventoryIndex;
    protected int addToCountOnPickup;
    protected int inventoryLimit;
    protected float amountOfEffect; // Different for different items - cast when needed

    public Item(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Generic Item");
        entityType = EntityType.ITEM;

        destroyed = false;
        itemEffectTime = 1f;

        defineBody();
        currentDirection = Direction.DOWN;
        previousDirection = Direction.DOWN;
        currentAction = Action.STILL;
        previousAction = Action.STILL;
        stateTimer = 0;
    }

    public abstract void use(Entity entity);

    public float getAmountOfEffect() { return amountOfEffect; }
    public TimerType getTimerType() { return timerType; }
    public void setTimerType(TimerType timerType) {
        this.timerType = timerType;
    }
    public float getItemEffectTime() { return itemEffectTime; }
    public void setItemEffectTime(float itemEffectTime) {
        this.itemEffectTime = itemEffectTime;
    }
    public int getInventoryIndex() { return inventoryIndex; }
    public void setInventoryIndex(int inventoryIndex) { this.inventoryIndex = inventoryIndex; }
    public int getAddToCountOnPickup() { return addToCountOnPickup; }
    public InventorySlotType getInventoryType() { return inventoryType; }
    public int getInventoryLimit() { return inventoryLimit; }


    @Override
    public float getSpawnOtherX() {return getX() + getWidth() / 2; }

    @Override
    public float getSpawnOtherY() { return getY() + getHeight() / 2; }

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
                | HunterOfPoke.ENEMY_BIT));
        finalizeBody();
    }

    @Override
    protected void defineBits(short maskBits) {
        bodyComponent.setCategoryBit(HunterOfPoke.ITEM_BIT);
        bodyComponent.setMaskBits(maskBits);
    }

    @Override
    protected void finalizeBody() {
        // true - is the fixture a sensor
        bodyComponent.finalizeFixture(true);
        b2Body.createFixture(bodyComponent.getFixtureDef()).setUserData(this);

        bodyComponent.dispose();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setPosition(b2Body.getPosition().x - getWidth() / 2,
                b2Body.getPosition().y - getHeight() / 2);
    }
}
