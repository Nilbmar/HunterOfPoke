package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.nilbmar.hunter.Commands.UpdateHudCommand;
import com.nilbmar.hunter.Enums.HudLabels;
import com.nilbmar.hunter.Observers.Observer;
import com.nilbmar.hunter.Scenes.Hud;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Displays status of character
 * ie. current item usage
 */

public class UserInfoHUD extends LabelHUD implements Observer {
    protected UpdateHudCommand hudUpdate;
    private String info;
    private Color color;

    public UserInfoHUD(String text) {
        super(text);
        label.setColor(Color.CHARTREUSE);
        color = Color.CHARTREUSE;
    }

    public void setInfo(String info) { this.info = info;
        Gdx.app.log("UserInfoHUD", "Setting info to " + info);

    }
    public void setColor(Color color) { this.color = color; }

    @Override
    public String getType() { return Hud.HudObservers.USER_INFO.toString(); }

    @Override
    public void update() {
        if (info != null) {
            Gdx.app.log("UserInfoHUD update", info.toString());
            label.setColor(color);
            label.setText(info);
        }
    }
}
