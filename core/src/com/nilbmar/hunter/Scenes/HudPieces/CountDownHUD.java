package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Locale;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Hud piece for displaying time left in level
 */

public class CountDownHUD extends LabelHUD {
    private int time;
    private int timeAlloted;

    public CountDownHUD(String text) {
        super(text);
        timeAlloted = Integer.parseInt(text);
        time = Integer.parseInt(text);
    }

    @Override
    public void update() {
        // "%03d" is how many digits long = 3 digits
        label.setText(String.format(Locale.US, "%03d", time) + " / " + timeAlloted);

        // TODO: BETTER TIME REDUCTION
        if (time > 0) {
            time--;
        }
    }
}
