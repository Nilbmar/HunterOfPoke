package com.nilbmar.hunter.Observers;

import com.badlogic.gdx.Gdx;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Scenes.Hud;
import com.nilbmar.hunter.Scenes.HudPieces.UserInfoHUD;

/**
 * Created by sysgeek on 1/25/18.
 *
 * Purpose: Watch for changes in player status and update Hud piece
 */

public class StatusObserver implements Observer {
    private Subject subject;
    private UserInfoHUD infoHUD;

    public StatusObserver(UserInfoHUD infoHUD, Player player) {
        this.infoHUD = infoHUD;
        this.subject = player;
        subject.addObserver(this);
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void update() {
        if (subject != null) {
            try {
                infoHUD.setInfo(((Player) subject).getStatusFX().toString());
            } catch (ClassCastException e) {
                Gdx.app.log("StatusObserver Exception", e.toString());
            }
        }
    }

    @Override
    public String getType() {
        return Hud.HudObservers.USER_INFO.name();
    }
}
