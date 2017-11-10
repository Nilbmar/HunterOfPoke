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
    }

    @Override
    public void patrol() {
        if (hasLOStoPlayer) {
            setGoal(Goal.FIND_HELP);
        }
        Gdx.app.log("Scared Brain", "Patrol");
    }

    @Override
    public void attack() {
        Gdx.app.log("Scared Brain", "Attack");
        if (hasLOStoPlayer) {
            if (helpIsNear) {
                getEnemy().attack();
            } else {
                setGoal(Goal.FIND_HELP);
            }
        } else {
            setGoal(Goal.PATROL);
        }
    }

    @Override
    public void findHelp() {
        Gdx.app.log("Scared Brain", "Find Help");
        if (helpIsNear) {
            setGoal(Goal.ATTACK);
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
