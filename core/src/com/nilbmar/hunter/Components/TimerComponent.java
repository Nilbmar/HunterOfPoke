package com.nilbmar.hunter.Components;

import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 12/28/17.
 *
 * Purpose: Base class for setting timers
 */

public abstract class TimerComponent implements Component {
    private Entity entity;
    private float stateTime;
    private float setTime;
    private boolean endTimer;

    public TimerComponent(Entity entity, float setTime, float deltaTime) {
        this.entity = entity;
        this.setTime = setTime;
        endTimer = false;
        stateTime = stateTime + deltaTime;
    }

    public boolean endTimer() {
        return endTimer;
    }
    public Entity getEntity() { return entity; }

    public void update(float deltaTime) {
        if (stateTime >= setTime) {
            endTimer = true;
        } else {
            stateTime = stateTime + deltaTime;
        }
    }
}