package com.nilbmar.hunter.Entities.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.Enums.EntityType;
import com.nilbmar.hunter.Tools.Enums.InventorySlotType;
import com.nilbmar.hunter.Tools.Enums.TimerType;

/**
 * Created by sysgeek on 6/12/17.
 */

public class SpeedBoostItem extends Item {

    public SpeedBoostItem(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        inventoryType = InventorySlotType.ACCELERATION;
        inventoryLimit = 5;
        addToCountOnPickup = 1;
        amountOfEffect = 2;  // amountOfEffect of HP to recover

        setItemEffectTime(10f);
        setTimerType(TimerType.ACCELERATION);

        setName(timerType.getName() + ": " + getItemEffectTime());

        regionName = "swarm"; // TODO: CREATE ITEM.PACK - CURRENTLY USING CHARACTER PACK
        // TODO: CHANGE FROM ENEMY ATLAS TO ITEM ATLAS
        TextureRegion charStill = new TextureRegion(screen.getItemAtlas().findRegion(regionName), 0, 0, 16, 16);
        setBounds(0, 0, 16 / HunterOfPoke.PPM, 16 / HunterOfPoke.PPM);
        setRegion(charStill);
    }

    @Override
    public void use(Entity entity) {
        if (entity.getEntityType() == EntityType.PLAYER) {
            int hpToRecover = (int) amountOfEffect;
            ((Player) entity).recoverHitPoints((int) amountOfEffect);
            Gdx.app.log("Item", "used by " + entity.getEntityType());
        }
        if (entity.getEntityType() == EntityType.ENEMY) {
            int hpToRecover = (int) amountOfEffect;
            ((Enemy) entity).recoverHitPoints(hpToRecover);
            Gdx.app.log("Item", "used by " + entity.getEntityType());
        }


    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }


}
