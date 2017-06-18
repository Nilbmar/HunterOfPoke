package com.nilbmar.hunter.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Tools.Enums.InventorySlotType;

/**
 * Created by sysgeek on 6/18/17.
 */

public class InventoryComponent {
    private Entity entity;
    private int slotsTotal;
    private int slotsMax;
    private InventorySlotType slotType;

    private Array<InventorySlotType> arrCurrentSlots;
    private Array<Integer> arrCountInSlots;

    public InventoryComponent(Entity entity, int slotsMax) {
        this.entity = entity;
        this.slotsMax = slotsMax;

        arrCurrentSlots = new Array<InventorySlotType>();
        arrCountInSlots = new Array<Integer>();
        slotsTotal = 0;

    }

    // TODO: ITEMS WILL NEED TO HAVE AN INVENTORY TYPE
    public void placeInInventory(Item item, int count) {
        InventorySlotType slotType = item.getInventoryType();

        if (!inInventory(slotType)) {
            if (slotsTotal < slotsMax) {
                arrCurrentSlots.add(slotType);
                arrCountInSlots.add(count); // count = how many of an item
                item.setInventoryIndex(arrCurrentSlots.indexOf(slotType, true)); // true - uses == to compare
                slotsTotal++;
                Gdx.app.log("Inventory", "Added " + slotType + " to inventory.");
            }
        } else {
            int currentSlot = item.getInventoryIndex();
            int currentItemCount = arrCountInSlots.get(currentSlot);
            if (currentItemCount < item.getInventoryLimit()) {
                arrCountInSlots.set(currentSlot, currentItemCount + count);
                Gdx.app.log("Inventory", slotType + " - Previous: " + currentItemCount
                        + " Updated: " + arrCountInSlots.get(currentSlot));
            } else {
                Gdx.app.log("Inventory", slotType + " limit of " + currentItemCount + "/"
                        + item.getInventoryLimit() + " has been reached.");
            }
        }
    }

    public InventorySlotType getSlotTypeInArray(int index) {
        return  arrCurrentSlots.get(index);
    }

    private boolean inInventory(InventorySlotType slotType) {
        boolean isInInventory = false;

        for (int x = 0; x < slotsTotal; x++) {
            if (slotType == getSlotTypeInArray(x)) {
                isInInventory = true;
            }
        }

        return isInInventory;
    }

    public void addMaxSlots(int extraSlots) { slotsMax = slotsMax + extraSlots; }
    public void setSlotType(InventorySlotType slotType) { this.slotType = slotType; }
    public InventorySlotType getSlotType() { return slotType; }

}
