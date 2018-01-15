package com.nilbmar.hunter.Scenes.HudPieces;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Hud piece for the player's name
 */

public class NameHUD extends LabelHUD {
    private String name;

    public NameHUD(String name) {
        super(name);
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
