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
    // TODO: REMOVE FIRECOMMAND
    int fireCount = 1;
    FireCommand fire;

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

        fire = new FireCommand(screen.getBulletPatterns(), BulletType.BALL, ShotType.SINGLE);
    }

    @Override
    public float getSpawnOtherX() {
        return getX() + getWidth() / 2;
    }

    @Override
    public float getSpawnOtherY() {
        return getY() + getHeight() / 2;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // TODO: REMOVE - ONLY USING THIS FOR TESTING FIRECOMMAND
        // ONLY ALLOWS SINGLE SHOT
        if (fireCount >= 1) {
            fire.setType(BulletType.BALL);
            fire.execute(this);
            fireCount--;
        }
    }
}
