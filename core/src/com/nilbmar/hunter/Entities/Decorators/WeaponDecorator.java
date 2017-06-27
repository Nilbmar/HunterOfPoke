package com.nilbmar.hunter.Entities.Decorators;

import com.nilbmar.hunter.Commands.FireCommand;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/27/17.
 */

public class WeaponDecorator extends EnemyDecorator {
    // TODO: REMOVE FIRECOMMAND
    int fireCount = 1;
    FireCommand fire;

    public WeaponDecorator(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        fire = new FireCommand(screen.getBulletPatterns(), BulletType.BALL, ShotType.SINGLE);
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
