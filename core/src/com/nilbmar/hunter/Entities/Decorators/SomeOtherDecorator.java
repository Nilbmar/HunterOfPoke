package com.nilbmar.hunter.Entities.Decorators;

import com.nilbmar.hunter.Commands.FireCommand;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/27/17.
 *
 * EnemyDecorator: SomeOtherDecorator
 * Purpose: Testing switch statements for decorators
 * Currently adds a FireCommand with
 * BulletType.FIRE AND ShotType.TWIN default
 */

public class SomeOtherDecorator extends EnemyDecorator {
    // TODO: REMOVE FIRECOMMAND
    int fireCount = 5;
    FireCommand fire;

    public SomeOtherDecorator(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        fire = new FireCommand(screen.getBulletPatterns(), BulletType.FIRE, ShotType.TWIN);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // TODO: REMOVE - ONLY USING THIS FOR TESTING FIRECOMMAND
        // ONLY ALLOWS SINGLE SHOT
        if (fireCount >= 1) {
            fire.setType(BulletType.FIRE);
            fire.execute(this);
            fireCount--;
        }
    }
}
