package com.nilbmar.hunter.Timers;

import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 12/28/17.
 *
 * Purpose: Only allow attacking again
 * - after a set time from the previous attack
 */

public class AttackTimer extends TimerComponent {

    public AttackTimer(Entity entity, float setTime, float deltaTime) {
        super(entity, setTime, deltaTime);
        setTimerType(TimerType.ATTACK);
    }
}
