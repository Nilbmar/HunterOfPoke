package com.nilbmar.hunter.Timers;

import com.nilbmar.hunter.Components.TimerComponent;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Enums.ItemType;

/**
 * Created by sysgeek on 12/28/17.
 */

public class ResetCollisionTimer extends TimerComponent {
    public ResetCollisionTimer(Entity entity, float setTime, float deltaTime) {
        super(entity, setTime, deltaTime);
    }
}
