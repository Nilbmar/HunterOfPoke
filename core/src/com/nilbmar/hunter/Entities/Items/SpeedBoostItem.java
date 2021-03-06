package com.nilbmar.hunter.Entities.Items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nilbmar.hunter.Commands.AccelerationCommand;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Enums.InventorySlotType;
import com.nilbmar.hunter.Enums.ItemType;
import com.nilbmar.hunter.Timers.ItemTimer;
import com.nilbmar.hunter.Timers.TimerComponent;

/**
 * Created by sysgeek on 6/12/17.
 *
 * Item: Speed Boost
 * Purpose: Gives a temporary speed boost to an entity
 */

public class SpeedBoostItem extends Item {
    private AccelerationCommand accelerationCommand;

    public SpeedBoostItem(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        inventoryType = InventorySlotType.ACCELERATION;
        inventoryLimit = 5;
        addToCountOnPickup = 1;
        amountOfEffect = 2;  // amountOfEffect of HP to recover

        setItemEffectTime(10f);
        setItemType(ItemType.ACCELERATION);


        setName("Speed Boost!");


        regionName = "item_chocolate"; // TODO: CREATE ITEM.PACK - CURRENTLY USING CHARACTER PACK
        // TODO: CHANGE FROM ENEMY ATLAS TO ITEM ATLAS
        TextureRegion charStill = new TextureRegion(atlas.findRegion(regionName), 0, 0, 64, 32);
        imageComponent.setBounds(0, 0, 16 / HunterOfPoke.PPM, 8 / HunterOfPoke.PPM);
        imageComponent.setRegion(charStill);
    }

    @Override
    public void use(Entity entity) {

        entityThatUsed = entity;

        if (entity.getEntityType() == EntityType.PLAYER) {
            addItemTimer(getItemEffectTime(), getItemType());
            accelerationCommand = new AccelerationCommand(entityThatUsed, 1);
            accelerationCommand.execute(entityThatUsed);
        }
        if (entity.getEntityType() == EntityType.ENEMY) {
            // TODO: WILL ENEMIES TRAMPLE ITEMS?
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Undo the effect after timer
        if (timerMap != null) {
            if (timerMap.containsKey(timerType) && timerMap.get(timerType) != null) {
                ItemTimer timer = (ItemTimer) timerMap.get(timerType);
                if (timer.timerHasEnded()) {
                    if (accelerationCommand != null) {
                        accelerationCommand.undo(entityThatUsed);
                        accelerationCommand = null;
                        timerMap.put(timerType, null);
                    }
                } else {
                    timer.update(deltaTime);
                }
            }
        }

    }
}
