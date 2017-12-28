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
    private TimerType timerType;

    public enum TimerType { ITEM, ATTACK, REMOVE_COLLISION, RESET_COLLISION }

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

    protected void setTimerType(TimerType type) { timerType = type; }
    public TimerType getTimerType() { return timerType; }

    public void update(float deltaTime) {
        if (stateTime >= setTime) {
            endTimer = true;
        } else {
            stateTime = stateTime + deltaTime;
        }
    }
}