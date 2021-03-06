package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Displays status of character
 * ie. current item usage
 */

public class UserInfoHUD extends LabelHUD {
    private String info;
    private Color color;

    public UserInfoHUD(String text) {
        super(text);
        label.setColor(Color.CHARTREUSE);
        color = Color.CHARTREUSE;
    }

    public void setInfo(String info) { this.info = info; }
    public void setColor(Color color) { this.color = color; }

    @Override
    public void update() {
        if (info != null) {
            label.setColor(color);
            label.setText(info);
        }
    }
}
