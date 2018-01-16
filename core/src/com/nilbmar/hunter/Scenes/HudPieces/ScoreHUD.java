package com.nilbmar.hunter.Scenes.HudPieces;

import com.nilbmar.hunter.Observers.Observer;
import com.nilbmar.hunter.Scenes.Hud;

import java.util.Locale;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Hud Piece to store and display player's score
 */

public class ScoreHUD extends LabelHUD implements Observer {
    private int score;

    public ScoreHUD(String text) {
        super(text);
        score = Integer.parseInt(text);
    }

    public void addToScore(int add) {
        score = score + add;
    }

    @Override
    public String getType() { return Hud.HudObservers.SCORE.toString(); }

    @Override
    public void update() {
        // "%06d" is how many digits long = 6 digits
        label.setText(String.format(Locale.US, "%06d", score));
    }
}
