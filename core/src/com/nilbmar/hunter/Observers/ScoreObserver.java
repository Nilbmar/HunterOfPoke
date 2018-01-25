package com.nilbmar.hunter.Observers;

import com.badlogic.gdx.Gdx;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Scenes.Hud;
import com.nilbmar.hunter.Scenes.HudPieces.ScoreHUD;

/**
 * Created by sysgeek on 1/25/18.
 *
 * Purpose: Watch for updates to score and adjust Hud piece
 */

public class ScoreObserver implements Observer {
    private Subject subject;
    private ScoreHUD scoreHUD;

    public ScoreObserver(ScoreHUD scoreHUD, Player player) {
        this.scoreHUD = scoreHUD;
        subject = player;
        subject.addObserver(this);
    }

    @Override
    public void update() {
        if (subject != null) {
            try {
                int score = ((Player) subject).getScore();
                scoreHUD.setScore(score);
            } catch (Exception e) {
                Gdx.app.log("ScoreObserver Exception", e.getMessage());
            }
        }
    }

    @Override
    public String getType() { return Hud.HudObservers.SCORE.name(); }
}
