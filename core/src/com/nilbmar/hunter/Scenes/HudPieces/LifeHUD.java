package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.Gdx;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Observers.Observer;
import com.nilbmar.hunter.Observers.Subject;
import com.nilbmar.hunter.Scenes.Hud;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Piece of the HUD to display player's HP
 */

public class LifeHUD extends LabelHUD {
    private Subject subject;
    private int hp = 0;
    private int hpTotal = 0;

    public LifeHUD(int hp, int hpTotal, Subject subject) {
        super(hp + " / " + hpTotal);
        this.subject = subject;
        setHP();
    }

    public void setHP() {
        try {
            hp = ((Player) subject).getLifeComp().getHitPoints();
            hpTotal = ((Player) subject).getLifeComp().getMaxHitPoints();
        } catch (Exception ex) {
            Gdx.app.log("Exception", "Subject can not be converted to Player");
        }
    }

    @Override
    public void update() {
        label.setText(hp + " / " + hpTotal);
    }
}
