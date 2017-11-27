package com.nilbmar.hunter.AI.Brains;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.nilbmar.hunter.AI.AITarget;
import com.nilbmar.hunter.AI.States.Action;
import com.nilbmar.hunter.AI.States.Goal;
import com.nilbmar.hunter.AI.States.Temperament;
import com.nilbmar.hunter.AI.SteeringAI;
import com.nilbmar.hunter.AI.Utils.Behaviors;
import com.nilbmar.hunter.Entities.Enemies.Enemy;

import java.util.Stack;

/**
 * Created by sysgeek on 10/26/17.
 *
 * Base class to control enemy AI
 */

public abstract class Brain {
    private Enemy enemy;
    private SteeringAI ai;
    private AITarget target;

    private Stack<Goal> goalsStack;
    private Action currentAction;
    private Temperament currentTemperament;

    private int amtHelpRequired;
    protected Queue<Enemy> nearbyHelpQ;
    protected boolean helpIsNear;

    protected boolean hasLOStoPlayer;
    protected boolean alarmed;

    public Brain(Enemy enemy, SteeringAI ai) {
        this.enemy = enemy;
        this.ai = ai;

        goalsStack = new Stack<Goal>();
        // Set target to current position so it doesn't move yet
        target = new AITarget(enemy.getPosition());

        nearbyHelpQ = new Queue<Enemy>();
        hasLOStoPlayer = false;
        alarmed = false;
        goalsStack.push(Goal.NONE);
        currentAction = Action.STILL;
        currentTemperament = Temperament.DOCILE;
    }

    public Stack<Goal> getGoalsStack() { return goalsStack; }
    public int getAmtHelpRequired() { return amtHelpRequired; }
    public void setAmtHelpRequired(int amt) { amtHelpRequired = amt; }

    public Enemy getEnemy() { return enemy; }

    public Goal getGoal() { return goalsStack.peek(); }
    public void addGoal(Goal goal) {
        goalsStack.push(goal);
    }
    public void removeGoal() {
        if (goalsStack.peek() != Goal.NONE) {
            goalsStack.pop();
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
        switch (goalsStack.peek()) {
            case NONE:
                noGoal();
                break;
            case GO_TO:
                goTo();
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

    public void goTo() {
        // Set SteeringBehavior's target
        getEnemy().setTarget(target.getPosition());
    }

    public void run() {
        ai.setCurrentBehavior(Behaviors.Behavior.SEEK);
        getEnemy().setTarget(new Vector2(
                getEnemy().getPosition().x - 10,
                getEnemy().getPosition().y - 10));
        Gdx.app.log("Brain", "Running to " + getEnemy().getPosition() + " - behavior is " + ai.getCurrentBehavior());
    }

    public abstract void noGoal();
    public abstract void patrol();
    public abstract void attack();
    public abstract void findHelp();
    public abstract void hide();
    //public abstract void run();

    public void update(boolean hasLOStoPlayer) {
        this.hasLOStoPlayer = hasLOStoPlayer;
        goalIs();
    }
}
