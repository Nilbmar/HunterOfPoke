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

public abstract class Brain {
    private Entity entity;

    private Goal currentGoal;
    private Action currentAction;
    private Temperament currentTemperament;

    private boolean hasLOStoPlayer;

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

    public boolean getHasLOStoPlayer() { return hasLOStoPlayer; }
    public void setHasLOStoPlayer(boolean hasLOStoPlayer) { this.hasLOStoPlayer = hasLOStoPlayer; }

    private void goalIs() {
        switch (currentGoal) {
            case NONE:
                noGoal();
                break;
            case PATROL:
                patrol();
                // Look for player
                // if player found, set target to player
                // else set target to next patrol point
                //setTarget(nextPatrolPoint);
                break;
            case ATTACK:
                attack();
                //setTarget(player);
                break;
            case FIND_HELP:
                findHelp();
                //setTarget(closestFamiliar);
                break;
            case HIDE:
                hide();
                //setTarget(closestFamiliar);
                break;
            case RUN:
                run();
                //setTarget(closestBarrier);
                break;
        }
    }

    public abstract void noGoal();
    public abstract void patrol();
    public abstract void attack();
    public abstract void findHelp();
    public abstract void hide();
    public abstract void run();

    public void update(boolean hasLOStoPlayer) {
        this.hasLOStoPlayer = hasLOStoPlayer;
        goalIs();
    }
}
