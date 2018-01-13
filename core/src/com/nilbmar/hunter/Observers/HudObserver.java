package com.nilbmar.hunter.Observers;

import com.nilbmar.hunter.Scenes.HudPieces.LifeHUD;

/**
 * Created by sysgeek on 1/13/18.
 */

public class HudObserver implements Observer {
    private Subject subject;
    private LifeHUD lifeHUD;

    public HudObserver(Subject subject) {
        this.subject = subject;

        lifeHUD = new LifeHUD(0, 0);

    }

    @Override
    public void update() {

    }
}
