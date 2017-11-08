package com.nilbmar.hunter.AI.States;

import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 10/26/17.
 */

public class Brain implements States {
    private Entity entity;

    private Goal currentGoal;
    private Action currentAction;

    public Brain(Entity entity) {
        this.entity = entity;
        currentGoal = Goal.IDLE;
        currentAction = Action.STILL;
    }

    @Override
    public Goal getGoal() { return currentGoal; }

    @Override
    public void setGoal(Goal goal) { currentGoal = goal; }

    @Override
    public Action getAction() { return currentAction; }

    @Override
    public void setAction(Action action) {
        currentAction = action;
    }
}
