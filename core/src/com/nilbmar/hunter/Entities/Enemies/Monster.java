package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/25/17.
 */

public class Monster extends Enemy {
    protected int regionBeginX;
    protected int regionBeginY;
    protected int regionWidth;
    protected int regionHeight;
    protected float boundsBeginX;
    protected float boundsBeginY;
    protected float boundsWidth;
    protected float boundsHeight;
    protected int acceleration;
    protected float offsetSpriteX;

    public Monster(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Batty");   // TODO: SET FROM CONFIG FILE
        hitPoints = 2;      // TODO: SET FROM CONFIG FILE
        maxHitPoints = 4;

        defineBody();
        regionName = "bats"; // "swarm"
        offsetSpriteY = 0 / HunterOfPoke.PPM;

        TextureRegion charStill = new TextureRegion(screen.getEnemyAtlas().findRegion(regionName), regionBeginX, regionBeginY, regionWidth, regionHeight);
        setBounds(boundsBeginX, boundsBeginY, boundsWidth / HunterOfPoke.PPM, boundsHeight / HunterOfPoke.PPM);
        setRegion(charStill);

        // Movement
        setCurrentAcceleration(acceleration);
    }

    public Monster() {

    }
}
