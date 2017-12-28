package com.nilbmar.hunter.Timers;

import com.nilbmar.hunter.Entities.Entity;

import com.nilbmar.hunter.Components.TimerComponent;

/**
 * Created by sysgeek on 12/28/17.
 */

public class AttackTimer extends TimerComponent {

    public AttackTimer(Entity entity, float setTime, float deltaTime) {
        super(entity, setTime, deltaTime);
        setTimerType(TimerType.ATTACK);
    }
}
