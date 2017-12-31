package com.nilbmar.hunter.Entities.Items;

import com.badlogic.gdx.physics.box2d.Shape;
import com.nilbmar.hunter.Commands.UpdateHudCommand;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Timers.TimerComponent;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.AI.States.Action;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Enums.InventorySlotType;
import com.nilbmar.hunter.Enums.ItemType;
import com.nilbmar.hunter.Timers.ItemTimer;

import java.util.HashMap;

/**
 * Created by sysgeek on 6/12/17.
 *
 * Abstract Class: Item
 * Purpose: Base class for all pickup Items
 */

public abstract class Item extends Entity {

    protected Entity entityThatUsed;
    protected UpdateHudCommand hudUpdate;
    protected boolean destroyed;
    protected float stateTimer;
    protected ItemType itemType;
    protected float itemEffectTime;
    protected InventorySlotType inventoryType;
    protected int inventoryIndex;
    protected int addToCountOnPickup;
    protected int inventoryLimit;
    protected float amountOfEffect; // Different for different items - cast when needed
    protected TimerComponent.TimerType timerType = TimerComponent.TimerType.ITEM;

    public Item(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Generic Item");
        entityType = EntityType.ITEM;
        atlas = screen.getAssetsHandler().getItemAtlas();

        destroyed = false;
        itemEffectTime = 1f;
        amountOfEffect = 0;

        timerMap = new HashMap<TimerComponent.TimerType, TimerComponent>();

        defineBody();
        currentAction = Action.STILL;
        previousAction = Action.STILL;
        stateTimer = 0;
        imageComponent.setPosition(startInWorldX, startInWorldY);
    }

    @Override
    public void prepareToDraw() {

    }

    public String getRegionName(DirectionComponent.Direction currentDirection) {
        switch (currentDirection) {

        }

        return regionName;
    }

    public abstract void use(Entity entity);
    protected abstract void updateHud();

    protected void addItemTimer(float setTimer, ItemType itemType) {
        timerMap.put(TimerComponent.TimerType.ITEM, new ItemTimer(this, setTimer, itemType, deltaTime));
    }


    public float getAmountOfEffect() { return amountOfEffect; }
    public ItemType getItemType() { return itemType; }
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
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
    }
}
