package com.nilbmar.hunter.Entities.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nilbmar.hunter.Commands.AccelerationCommand;
import com.nilbmar.hunter.Commands.UpdateHudCommand;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.Enums.EntityType;
import com.nilbmar.hunter.Tools.Enums.HudLabels;
import com.nilbmar.hunter.Tools.Enums.InventorySlotType;
import com.nilbmar.hunter.Tools.Enums.ItemType;

/**
 * Created by sysgeek on 6/12/17.
 *
 * Item: Speed Boost
 * Purpose: Gives a temporary speed boost to an entity
 */

public class SpeedBoostItem extends Item {
    private Entity entityThatUsed;
    private AccelerationCommand accelerationCommand;
    private UpdateHudCommand hudUpdate;

    public SpeedBoostItem(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        inventoryType = InventorySlotType.ACCELERATION;
        inventoryLimit = 5;
        addToCountOnPickup = 1;
        amountOfEffect = 2;  // amountOfEffect of HP to recover

        setItemEffectTime(10f);
        setItemType(ItemType.ACCELERATION);


        setName("Speed Boost!");


        regionName = "swarm"; // TODO: CREATE ITEM.PACK - CURRENTLY USING CHARACTER PACK
        // TODO: CHANGE FROM ENEMY ATLAS TO ITEM ATLAS
        TextureRegion charStill = new TextureRegion(screen.getItemAtlas().findRegion(regionName), 0, 0, 16, 16);
        setBounds(0, 0, 16 / HunterOfPoke.PPM, 16 / HunterOfPoke.PPM);
        setRegion(charStill);
    }

    @Override
    public void use(Entity entity) {

        entityThatUsed = entity;

        if (entity.getEntityType() == EntityType.PLAYER) {
            int hpToRecover = (int) amountOfEffect;
            ((Player) entity).recoverHitPoints((int) amountOfEffect);
            setTimerComponent(getItemEffectTime(), getItemType());
            accelerationCommand = new AccelerationCommand(entityThatUsed, 1);
            accelerationCommand.execute(entityThatUsed);
            updateHud();
        }
        if (entity.getEntityType() == EntityType.ENEMY) {
            int hpToRecover = (int) amountOfEffect;
            ((Enemy) entity).recoverHitPoints(hpToRecover);
        }


    }

    @Override
    protected void updateHud() {
        // TODO: THIS NEEDS TO GO ELSEWHERE - BUT WHERE?
        hudUpdate = new UpdateHudCommand(screen.getHUD(), HudLabels.USER_INFO, getName());
        hudUpdate.execute(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Undo the effect after timer
        if (timerComponent != null) {
            if (timerComponent.endTimer()) {
                if (accelerationCommand != null) {
                    accelerationCommand.undo(entityThatUsed);
                    accelerationCommand = null;
                    timerComponent = null;

                    // TODO: CHANGE HOW HUD UPDATES
                    // CURRENTLY, THIS WILL BLANK THE LABEL NO MATTER WHAT
                    // EVEN IF ANOTHER ITEM IS MORE RECENT
                    // POSSIBLY STORE AN ARRAY OF ITEMS WITH TIMERS
                    // THEN REMOVE THEM FROM ARRAY WHEN THEY TIME OUT
                    hudUpdate = new UpdateHudCommand(screen.getHUD(), HudLabels.USER_INFO, "");
                    hudUpdate.execute(this);
                }
            } else {
                timerComponent.update(deltaTime);
            }
        }

    }
}
