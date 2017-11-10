package com.nilbmar.hunter.AI.Brains;

import com.badlogic.gdx.Gdx;
import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 11/10/17.
 */

public class ScaredBrain extends Brain {
    public ScaredBrain(Entity entity) {
        super(entity);
    }

    @Override
    public void noGoal() {
        Gdx.app.log("Scared Brain", "No Goal");
    }

    @Override
    public void patrol() {
        Gdx.app.log("Scared Brain", "Patrol");
    }

    @Override
    public void attack() {
        Gdx.app.log("Scared Brain", "Attack");
    }

    @Override
    public void findHelp() {
        Gdx.app.log("Scared Brain", "Find Help");
    }

    @Override
    public void hide() {
        Gdx.app.log("Scared Brain", "Run");
    }

    @Override
    public void run() {
        Gdx.app.log("Scared Brain", "Run");
    }
}
