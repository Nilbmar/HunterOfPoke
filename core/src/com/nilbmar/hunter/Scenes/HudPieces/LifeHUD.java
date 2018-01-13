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

public class LifeHUD extends LabelHUD {
    private int hp = 0;
    private int hpTotal = 0;

    public LifeHUD(int hp, int hpTotal) {
        super(hp + " / " + hpTotal);
        this.hp = hp;
        this.hpTotal = hpTotal;
    }

    public void setHP(int hp) { this.hp = hp; }
    public void setHpTotal(int hpTotal) { this.hpTotal = hpTotal; }

    @Override
    public void update() {
        label.setText(hp + " / " + hpTotal);
    }
}
