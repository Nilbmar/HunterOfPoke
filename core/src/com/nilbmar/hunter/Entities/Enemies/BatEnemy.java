package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nilbmar.hunter.Commands.FireCommand;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/24/17.
 */

public class BatEnemy extends Enemy {
    public BatEnemy(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        setName("Batty");   // TODO: SET FROM CONFIG FILE
        hitPoints = 2;      // TODO: SET FROM CONFIG FILE
        maxHitPoints = 4;

        defineBody();
        regionName = "bats"; // "swarm"
        offsetSpriteY = 0 / HunterOfPoke.PPM;

        TextureRegion charStill = new TextureRegion(screen.getEnemyAtlas().findRegion(regionName), 0, 0, 16, 16);
        setBounds(0, 0, 16 / HunterOfPoke.PPM, 16 / HunterOfPoke.PPM);
        setRegion(charStill);

        // Movement
        setCurrentAcceleration(1);
    }
}
