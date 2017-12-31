package com.nilbmar.hunter.Timers;

import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Enums.ItemType;

/**
 * Created by sysgeek on 6/13/17.
 *
 * Purpose: Remove item's effects after set time
 */

public class ItemTimer extends TimerComponent {
    private ItemType itemType;

    public ItemTimer(Entity entity, float setTime, ItemType itemType, float deltaTime) {
        super(entity, setTime, deltaTime);
        this.itemType = itemType;
        setTimerType(TimerType.ITEM);
    }

    public ItemType getItemType() { return itemType; }
}
