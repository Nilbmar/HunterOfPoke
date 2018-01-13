package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Piece of the HUD to display player's HP
 */

public class LifeHUD implements HudPiece {
    private int hp = 0;
    private int hpTotal = 0;
    private Vector2 position = new Vector2(-50, -50);

    private Label lifeLabel = new Label(hp + " / " + hpTotal,
            new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    public LifeHUD(int hp, int hpTotal) {
        this.hp = hp;
        this.hpTotal = hpTotal;
    }

    public Label getLabel() { return lifeLabel; }

    public void setHP(int hp) { this.hp = hp; }
    public void setHpTotal(int hpTotal) { this.hpTotal = hpTotal; }

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
