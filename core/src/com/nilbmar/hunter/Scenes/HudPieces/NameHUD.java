package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Hud piece for the player's name
 */

public class NameHUD implements HudPiece {
    private String name;
    private Vector2 position = new Vector2(-50, -50);

    private Label nameLabel;

    public NameHUD(String name) {
        this.name = name;
        nameLabel = new Label(this.name,
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }

    // THIS MIGHT NOT CHANGE THE NAME DISPLAYED SINCE LABEL IS SET IN CONSTRUCTOR
    public void setName(String name) { this.name = name; }
    public Label getNameLabel() { return nameLabel; }

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

    }
}
