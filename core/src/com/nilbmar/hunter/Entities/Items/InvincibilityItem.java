package com.nilbmar.hunter.Entities.Items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nilbmar.hunter.Commands.ChangeCollisionCommand;
import com.nilbmar.hunter.Commands.UpdateHudCommand;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Enums.HudLabels;
import com.nilbmar.hunter.Enums.InventorySlotType;
import com.nilbmar.hunter.Enums.ItemType;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Timers.ItemTimer;

/**
 * Created by sysgeek on 6/24/17.
 *
 * Item: Invincibility
 * Purpose: Temporarily remove collision with enemies
 * and after a Timer, resets collision to normal
 */

public class InvincibilityItem extends Item {
    private ChangeCollisionCommand collisionCommand;

    public InvincibilityItem(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        inventoryType = InventorySlotType.INVINCIBILITY;
        inventoryLimit = 1;
        addToCountOnPickup = 1;

        setItemEffectTime(5f);
        setItemType(ItemType.INVINCIBILITY);


        setName("Invincibility");

        regionName = "item_wine";
        TextureRegion charStill = new TextureRegion(atlas.findRegion(regionName), 0, 0, 32, 66);
        imageComponent.setBounds(0, 0, 8 / HunterOfPoke.PPM, 16 / HunterOfPoke.PPM);
        imageComponent.setRegion(charStill);
    }

    @Override
    public void use(Entity entity) {
        entityThatUsed = entity;

        if (entity.getEntityType() == EntityType.PLAYER) {
            addItemTimer(getItemEffectTime(), getItemType());
            collisionCommand = new ChangeCollisionCommand();
            collisionCommand.execute(entityThatUsed);
            updateHud();
        }
    }

    @Override
    protected void updateHud() {
    // TODO: THIS NEEDS TO GO ELSEWHERE - BUT WHERE?
        //hudUpdate = new UpdateHudCommand(screen.getHUD(), HudLabels.USER_INFO, getName());
        //hudUpdate.execute(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Undo the effect after timer
        if (timerMap != null) {
            if (timerMap.containsKey(timerType) && timerMap.get(timerType) != null) {
                ItemTimer timer = (ItemTimer) timerMap.get(timerType);
                if (timer.timerHasEnded()) {
                    if (collisionCommand != null) {
                        collisionCommand.undo(entityThatUsed);
                        collisionCommand = null;
                        timerMap.put(timerType, null);

                        // TODO: CHANGE HOW HUD UPDATES
                        // CURRENTLY, THIS WILL BLANK THE LABEL NO MATTER WHAT
                        // EVEN IF ANOTHER ITEM IS MORE RECENT
                        // POSSIBLY STORE AN ARRAY OF ITEMS WITH TIMERS
                        // THEN REMOVE THEM FROM ARRAY WHEN THEY TIME OUT
                        hudUpdate = new UpdateHudCommand(screen.getHUD(), HudLabels.USER_INFO, "");
                        hudUpdate.execute(this);
                    }
                } else {
                    timer.update(deltaTime);
                }
            }
        }

    }
}
