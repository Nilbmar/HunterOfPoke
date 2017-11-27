package com.nilbmar.hunter.AI.Brains;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Queue;
import com.nilbmar.hunter.AI.States.Goal;
import com.nilbmar.hunter.AI.SteeringAI;
import com.nilbmar.hunter.AI.Utils.Vision;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 11/10/17.
 */

public class ScaredBrain extends Brain {
    public ScaredBrain(Enemy enemy, SteeringAI ai) {
        super(enemy, ai);

        setAmtHelpRequired(1);
        //setAmtHelpRequired(0);
    }

    @Override
    public void noGoal() {
        //Gdx.app.log("Scared Brain", "No Goal");
        addGoal(Goal.PATROL);
    }

    @Override
    public void patrol() {
        //Gdx.app.log("Scared Brain", "Patrol");
        if (hasLOStoPlayer) {
            addGoal(Goal.ATTACK);
        } else {
            // Set next patrol target
            // TODO: TEMPORARILY SETTING TARGET TO CURRENT POSITION SO IT STAYS PUT
            getEnemy().setTarget(getEnemy().getPosition());
            //goTo();
        }

    }

    @Override
    public void attack() {
        //Gdx.app.log("Scared Brain", "Attack");
        if (hasLOStoPlayer) {
            // Keep this check in for Scared
            // so they can go back to finding help
            // if they lose a friend
            if (nearbyHelpQ.size >= getAmtHelpRequired()) {
                getEnemy().attack();
            } else {
                run();
                addGoal(Goal.FIND_HELP);
            }
        } else {
            //addGoal(Goal.PATROL);
            // Go back to previous Goal (PATROL)
            removeGoal();
        }
    }

    @Override
    public void findHelp() {
        //Gdx.app.log("Scared Brain", "Find Help");
        if (nearbyHelpQ.size < getAmtHelpRequired()) {
            nearbyHelpQ.addFirst(getEnemy().findHelp());
            Gdx.app.log("Help", getEnemy().findHelp() + "");
            if (nearbyHelpQ.size >= 1) {
                //getEnemy().setTarget(nearbyHelpQ.first().getPosition());
                Gdx.app.log("ScaredBrain - Find Help", nearbyHelpQ.toString());
            }
        } else {
            if (nearbyHelpQ.first() != null) {
                addGoal(Goal.ATTACK);
            }
        }
    }

    @Override
    public void hide() {
        //Gdx.app.log("Scared Brain", "Hide");
    }

    @Override
    public void run() {
        //Gdx.app.log("Scared Brain", "Run");
    }
}
