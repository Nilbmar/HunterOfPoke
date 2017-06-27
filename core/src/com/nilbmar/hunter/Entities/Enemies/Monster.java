package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/25/17.
 */

public class Monster extends Enemy {
    public Monster(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);
    }

    public void setSprite() {
        TextureRegion charStill = new TextureRegion(
                screen.getEnemyAtlas().findRegion(regionName),
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
