package com.nilbmar.hunter.AI.Brains;

import com.badlogic.gdx.math.Vector2;
import com.nilbmar.hunter.AI.AITarget;
import com.nilbmar.hunter.AI.States.Action;
import com.nilbmar.hunter.AI.States.Goal;
import com.nilbmar.hunter.AI.States.Temperament;
import com.nilbmar.hunter.AI.Utils.Vision;
import com.nilbmar.hunter.Entities.Enemies.Enemy;

import java.util.Stack;

/**
 * Created by sysgeek on 10/26/17.
 *
 * Base class to control enemy AI
 */

public abstract class Brain {
    private Enemy enemy;
    private AITarget target;

    private Stack<Goal> stack;
    private Action currentAction;
    private Temperament currentTemperament;

    protected boolean hasLOStoPlayer;
    protected boolean helpIsNear;
    protected boolean alarmed;

    public Brain(Enemy enemy) {
        this.enemy = enemy;

        stack = new Stack<Goal>();
        // Set target to current position so it doesn't move yet
        target = new AITarget(enemy.getPosition());

        hasLOStoPlayer = false;
        alarmed = false;
        stack.push(Goal.NONE);
        currentAction = Action.STILL;
        currentTemperament = Temperament.DOCILE;
    }

    public Enemy getEnemy() { return enemy; }

    public Goal getGoal() { return stack.peek(); }
    public void addGoal(Goal goal) {
        stack.push(goal);
    }
    public void removeGoal() {
        if (stack.peek() != Goal.NONE) {
            stack.pop();
        }
    }

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

    public AITarget getTarget() { return target; }
    public void setTarget(Vector2 position) {
        target.setPosition(position);
    }

    private void goalIs() {
        switch (stack.peek()) {
            case NONE:
                noGoal();
                break;
            case PATROL:
                patrol();
                break;
            case ATTACK:
                attack();
                break;
            case FIND_HELP:
                findHelp();
                break;
            case HIDE:
                hide();
                break;
            case RUN:
                run();
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
