package com.nilbmar.hunter.Components;

import com.badlogic.gdx.utils.Disposable;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Tools.Enums.TimerType;

import java.sql.Time;

/**
 * Created by sysgeek on 6/13/17.
 */

public class TimerComponent {
    Entity entity;
    private float stateTime;
    private float setTime;
    private boolean endTimer;
    private TimerType timerType;

    public TimerComponent(Entity entity, float setTime, TimerType timerType, float deltaTime) {
        this.entity = entity;
        this.setTime = setTime;
        this.timerType = timerType;
        endTimer = false;
        stateTime = stateTime + deltaTime;
    }

    public boolean endTimer() {
        return endTimer;
    }
    public TimerType getTimerType() { return timerType; }

    public void update(float deltaTime) {
        if (stateTime >= setTime) {
            endTimer = true;
        } else {
            stateTime = stateTime + deltaTime;
        }
    }
}
