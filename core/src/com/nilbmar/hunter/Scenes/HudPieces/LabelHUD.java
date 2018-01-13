package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by sysgeek on 1/13/18.
 */

public class LabelHUD implements HudPiece {
    private String text;
    private Vector2 position = new Vector2(-50, -50);

    protected Label label;

    public LabelHUD(String text) {
        this.text = text;
        label = new Label(this.text,
                    new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }

    // THIS MIGHT NOT CHANGE THE NAME DISPLAYED SINCE LABEL IS SET IN CONSTRUCTOR
    public void setLabel(String info) { this.text = info; }
    public Label getLabel() { return label; }

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
        label.setText(text);
    }
}
