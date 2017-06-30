package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/25/17.
 *
 * TODO: REMOVE AFTER TESTING
 * TEMPORARY EXTENSION OF ENEMY CLASS
 * TO TEST DECORATOR PATTERN
 */

public class Monster extends Enemy {
    public Monster(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        atlas = screen.getAssetsHandler().getEnemyAtlas();
    }

    public void setSprite() {
        TextureRegion charStill = new TextureRegion(
                atlas.findRegion(regionName),
                regionBeginX, regionBeginY, regionWidth, regionHeight);
        setBounds(boundsBeginX / HunterOfPoke.PPM, boundsBeginY / HunterOfPoke.PPM,
                boundsWidth / HunterOfPoke.PPM, boundsHeight / HunterOfPoke.PPM);
        setRegion(charStill);

        // Movement
        //setCurrentAcceleration(acceleration);
    }

    public void prepareToDraw() {
        defineBody();
        setSprite();
        setCurrentAcceleration(acceleration);
    }
}
