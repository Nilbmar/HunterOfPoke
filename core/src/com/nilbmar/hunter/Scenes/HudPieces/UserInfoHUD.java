package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Displays status of character
 * ie. current item usage
 */

public class UserInfoHUD extends LabelHUD {

    public UserInfoHUD(String text) {
        super(text);
        label.setColor(Color.CHARTREUSE);
    }
}
