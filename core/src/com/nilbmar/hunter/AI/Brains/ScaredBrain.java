package com.nilbmar.hunter.AI.Brains;

import com.badlogic.gdx.Gdx;
import com.nilbmar.hunter.AI.States.Goal;
import com.nilbmar.hunter.AI.Utils.Vision;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 11/10/17.
 */

public class ScaredBrain extends Brain {
    public ScaredBrain(Enemy enemy) {
        super(enemy);
    }

    @Override
    public void noGoal() {
        Gdx.app.log("Scared Brain", "No Goal");
        addGoal(Goal.PATROL);
    }

    @Override
    public void patrol() {
        Gdx.app.log("Scared Brain", "Patrol");
        if (hasLOStoPlayer) {
            addGoal(Goal.FIND_HELP);
        } else {
            // Set next patrol target
        }

    }

    @Override
    public void attack() {
        Gdx.app.log("Scared Brain", "Attack");
        if (hasLOStoPlayer) {
            if (helpIsNear) {
                getEnemy().attack();
            } else {
                addGoal(Goal.FIND_HELP);
            }
        } else {
            addGoal(Goal.PATROL);
        }
    }

    @Override
    public void findHelp() {
        Gdx.app.log("Scared Brain", "Find Help");
        if (helpIsNear) {
            addGoal(Goal.ATTACK);
        } else {
            getEnemy().findHelp();
        }
    }

    @Override
    public void hide() {
        Gdx.app.log("Scared Brain", "Hide");
    }

    @Override
    public void run() {
        Gdx.app.log("Scared Brain", "Run");
    }
}
