package com.nilbmar.hunter.Commands;

import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Scenes.Hud;
import com.nilbmar.hunter.Tools.Enums.HudLabels;

/**
 * Created by sysgeek on 6/13/17.
 *
 * Command: Update HUD
 * Purpose: Adjust the overlay HUD on the fly
 */

public class UpdateHudCommand implements Command {
    private Hud hud;
    private String newText;
    private HudLabels label;

    public UpdateHudCommand(Hud hud, HudLabels label, String newText) {
        this.hud = hud;
        this.label = label;
        this.newText = newText;
    }
    @Override
    public void execute(Entity entity) {
        switch(label) {
            case PLAYER:
                hud.setPlayerName(newText);
                break;
            case USER_INFO:
                hud.setUserInfo(newText);
                break;
        }
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public void setLabelToUpdate(HudLabels label) {
        this.label = label;
    }
}
