package com.nilbmar.hunter.AI.Brains;

import com.nilbmar.hunter.AI.States.Action;
import com.nilbmar.hunter.AI.States.Goal;
import com.nilbmar.hunter.AI.States.Temperament;
import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 10/26/17.
 *
 * Base class to control enemy AI
 */

public class Brain {
    private Entity entity;

    private Goal currentGoal;
    private Action currentAction;
    private Temperament currentTemperament;

    public Brain(Entity entity) {
        this.entity = entity;
        currentGoal = Goal.PATROL;
        currentAction = Action.STILL;
        currentTemperament = Temperament.DOCILE;
    }

    public Entity getEntity() { return  entity; }

    public Goal getGoal() { return currentGoal; }
    public void setGoal(Goal goal) { currentGoal = goal; }

    public Action getAction() { return currentAction; }
    public void setAction(Action action) {
        currentAction = action;
    }

    public Temperament getTemperament() { return currentTemperament; }
    public void setTemperament(Temperament temperament) {
        currentTemperament = temperament;
    }

    private void goalIs() {
        switch (currentGoal) {
            case NONE:
                // 
                // setGoal(Goal.PATROL);
                break;
            case PATROL:
                // Look for player
                // if player found, set target to player
                // else set target to next patrol point
                //setTarget(nextPatrolPoint);
                break;
            case ATTACK:
                //setTarget(player);
                break;
            case FIND_HELP:
                //setTarget(closestFamiliar);
                break;
            case RUN:
                //setTarget(closestBarrier);
                break;
        }
    }
}
