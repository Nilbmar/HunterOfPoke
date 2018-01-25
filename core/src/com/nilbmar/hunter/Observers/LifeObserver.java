package com.nilbmar.hunter.Observers;

import com.badlogic.gdx.Gdx;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Scenes.Hud;
import com.nilbmar.hunter.Scenes.HudPieces.LifeHUD;

/**
 * Created by sysgeek on 1/25/18.
 *
 * Purpose: Run updates when player gains or loses life
 */

public class LifeObserver implements Observer{
    private Subject subject;
    private LifeHUD lifeHUD;

    public LifeObserver(LifeHUD lifeHUD, Player player) {
        this.lifeHUD = lifeHUD;
        subject = player;
        subject.addObserver(this);
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void update() {
        try {
            lifeHUD.setHP();
        } catch (Exception e) {
            Gdx.app.log("LifeObserver Exception", e.toString());
        }
    }

    @Override
    public String getType() {
        return Hud.HudObservers.LIFE.name();
    }
}
