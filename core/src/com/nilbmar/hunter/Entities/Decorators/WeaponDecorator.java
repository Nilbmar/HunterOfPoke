package com.nilbmar.hunter.Entities.Decorators;

import com.nilbmar.hunter.Commands.FireCommand;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/27/17.
 *
 * EnemyDecorator: WeaponDecorator
 * Purpose: Add a FireCommand to Enemy
 * from Tiled custom property
 */

public class WeaponDecorator extends EnemyDecorator {
    // TODO: REMOVE FIRECOMMAND
    int fireCount = 1;
    FireCommand fire;
    BulletType bulletType;
    ShotType shotType;

    public WeaponDecorator(PlayScreen screen, float startInWorldX, float startInWorldY,
                           BulletType bulletType, ShotType shotType) {
        super(screen, startInWorldX, startInWorldY);

        this.bulletType = bulletType;
        this.shotType = shotType;
        fire = new FireCommand(screen.getBulletPatterns(), bulletType, shotType);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // TODO: REMOVE - ONLY USING THIS FOR TESTING FIRECOMMAND
        // ONLY ALLOWS SINGLE SHOT
        if (fireCount >= 1) {
            fire.setType(bulletType);
            fire.execute(this);
            fireCount--;
        }
    }
}
