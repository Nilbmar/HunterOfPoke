package com.nilbmar.hunter.Components;

import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.NewEntity;
import com.nilbmar.hunter.Enums.ItemType;

/**
 * Created by sysgeek on 6/13/17.
 */

public class TimerComponent {
    Entity entity;
    private float stateTime;
    private float setTime;
    private boolean endTimer;
    private ItemType itemType;

    public TimerComponent(Entity entity, float setTime, ItemType itemType, float deltaTime) {
        this.entity = entity;
        this.setTime = setTime;
        this.itemType = itemType;
        endTimer = false;
        stateTime = stateTime + deltaTime;
    }

    public TimerComponent(NewEntity entity, float setTime, ItemType itemType, float deltaTime) {
        // TODO: RE-ENABLE SETTING OF THIS.ENTITY
        //this.entity = entity;
        this.setTime = setTime;
        this.itemType = itemType;
        endTimer = false;
        stateTime = stateTime + deltaTime;
    }

    public boolean endTimer() {
        return endTimer;
    }
    public ItemType getItemType() { return itemType; }

    public void update(float deltaTime) {
        if (stateTime >= setTime) {
            endTimer = true;
        } else {
            stateTime = stateTime + deltaTime;
        }
    }
}
