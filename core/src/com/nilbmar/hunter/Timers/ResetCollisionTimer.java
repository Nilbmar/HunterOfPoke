package com.nilbmar.hunter.Timers;

import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 12/28/17.
 *
 * Purpose: Remove collision or Reset collision for player
 * - most likely used when hit by enemy for invincibility frames
 */

public class ResetCollisionTimer extends TimerComponent {
    private TimerType timerType;

    public ResetCollisionTimer(Entity entity, float setTime, float deltaTime, TimerType timerType) {
        super(entity, setTime, deltaTime);

        // Default to RESET_COLLISION in case correct type isn't passed
        // Player will be placed in a "normal" state
        this.timerType = TimerType.RESET_COLLISION;

        switch (timerType) {
            case REMOVE_COLLISION:
                this.timerType = TimerType.REMOVE_COLLISION;
                break;
            case RESET_COLLISION:
                this.timerType = TimerType.RESET_COLLISION;
                break;
        }


        setTimerType(this.timerType);
    }

    public TimerType getCurrentTimerType() { return timerType; }
}
