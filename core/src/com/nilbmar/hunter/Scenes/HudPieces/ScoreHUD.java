package com.nilbmar.hunter.Scenes.HudPieces;

import java.util.Locale;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Hud Piece to store and display player's score
 */

public class ScoreHUD extends LabelHUD {
    private int score;

    public ScoreHUD(String text) {
        super(text);
        score = Integer.parseInt(text);
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void update() {
        // "%06d" is how many digits long = 6 digits
        label.setText(String.format(Locale.US, "%06d", score));
    }
}
