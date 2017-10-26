package com.nilbmar.hunter.Entities.Enemies;

import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 10/26/17.
 */

public class Brain {
    private Entity entity;
    private enum Goal { IDLE, PATROL, FIND }

    private Goal currentGoal;

    public Brain(Entity entity) {
        this.entity = entity;
        currentGoal = Goal.IDLE;

    }

    public void setGoal(Goal goal) { currentGoal = goal; }
    public Goal getGoal() { return currentGoal; }
}
