package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by sysgeek on 1/13/18.
 */

public class UserInfoHUD implements HudPiece {
    private String info;
    private Vector2 position = new Vector2(-50, -50);

    private Label infoLabel;

    public UserInfoHUD(String info) {
        this.info = info;
        infoLabel = new Label(this.info,
                new Label.LabelStyle(new BitmapFont(), Color.CHARTREUSE));
    }

    // THIS MIGHT NOT CHANGE THE NAME DISPLAYED SINCE LABEL IS SET IN CONSTRUCTOR
    public void setInfo(String info) { this.info = info; }
    public Label getLabel() { return infoLabel; }

    @Override
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void update() {
        infoLabel.setText(info);
    }
}
