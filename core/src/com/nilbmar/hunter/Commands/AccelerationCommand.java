package com.nilbmar.hunter.Commands;

import com.nilbmar.hunter.Entities.NewEntity;

/**
 * Created by sysgeek on 6/11/17.
 */

public class AccelerationCommand implements Command {
    NewEntity entity;
    private int acceleration;
    private int prevAcceleration;
    private float stateTimer;

    public AccelerationCommand(NewEntity entity, int acceleration) {
        this.entity = entity;
        this.acceleration = acceleration;
    }

    @Override
    public void execute(NewEntity entity) {
        prevAcceleration = entity.getBaseAcceleration();
        acceleration = prevAcceleration + acceleration;
        entity.setCurrentAcceleration(acceleration);
    }

    public void setAcceleration(int acceleration) {
        this.acceleration = acceleration;
    }

    // TODO: MAKE PRIVATE AGAIN AFTER TESTING AND IMPLEMENTING TIMER
    public void undo(NewEntity entity) {
        entity.setCurrentAcceleration(entity.getBaseAcceleration());
    }

    private void setTimer(float deltaTime) {
        stateTimer = stateTimer + deltaTime;
    }
}
