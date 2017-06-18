package com.nilbmar.hunter.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Tools.Enums.InventorySlotType;

/**
 * Created by sysgeek on 6/18/17.
 */

public class InventoryComponent {
    private Entity entity;
    private int slotsTotal;
    private int slotsMax;
    private InventorySlotType slotType;

    private Array<InventorySlotType> currentSlotsType;

    public InventoryComponent(Entity entity, int slotsMax) {
        this.entity = entity;
        this.slotsMax = slotsMax;

        currentSlotsType = new Array<InventorySlotType>();
        slotsTotal = 0;

    }

    // TODO: ITEMS WILL NEED TO HAVE AN INVENTORY TYPE
    public void placeInInventory(InventorySlotType slotType) {
        if (slotsTotal < slotsMax) {
            currentSlotsType.add(slotType);
            slotsTotal++;
            Gdx.app.log("Inventory", "Added " + slotType + " to inventory.");
        }
    }

    public void addMaxSlots(int extraSlots) { slotsMax = slotsMax + extraSlots; }
    public void setSlotType(InventorySlotType slotType) { this.slotType = slotType; }
    public InventorySlotType getSlotType() { return slotType; }
}
