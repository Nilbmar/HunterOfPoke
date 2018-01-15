package com.nilbmar.hunter.Scenes.HudPieces;

/**
 * Created by sysgeek on 1/13/18.
 *
 * Purpose: Hud Piece to display the level name
 */

public class LevelHUD extends LabelHUD {
    private String levelName;

    public LevelHUD(String text) {
        super(text);
        levelName = text;
    }

    public String getLevelName() { return levelName; }
    public void setLevelName(String levelName) { this.levelName = levelName; }
}
