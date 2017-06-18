package com.nilbmar.hunter.Entities.Items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.Enums.InventorySlotType;
import com.nilbmar.hunter.Tools.Enums.TimerType;

/**
 * Created by sysgeek on 6/12/17.
 */

public class SpeedBoostItem extends Item {

    public SpeedBoostItem(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        inventoryType = InventorySlotType.ITEM;
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
    public void update(float deltaTime) {
        super.update(deltaTime);
    }


}
