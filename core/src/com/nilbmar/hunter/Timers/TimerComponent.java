package com.nilbmar.hunter.Timers;

import com.nilbmar.hunter.Components.Component;
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
    private boolean timerHasEnded;
    private TimerType timerType;

    public enum TimerType { ITEM, ATTACK, REMOVE_COLLISION, RESET_COLLISION }

    public TimerComponent(Entity entity, float setTime, float deltaTime) {
        this.entity = entity;
        this.setTime = setTime;
        timerHasEnded = false;
        stateTime = stateTime + deltaTime;
    }

    public boolean timerHasEnded() {
        return timerHasEnded;
    }
    public Entity getEntity() { return entity; }

    protected void setTimerType(TimerType type) { timerType = type; }
    public TimerType getTimerType() { return timerType; }

    public void update(float deltaTime) {
        if (stateTime >= setTime) {
            timerHasEnded = true;
        } else {
            stateTime = stateTime + deltaTime;
        }
    }
}