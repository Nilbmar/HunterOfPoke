package com.nilbmar.hunter.Entities.Decorators;

import com.nilbmar.hunter.Components.WeaponComponent;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/27/17.
 *
 * EnemyDecorator: SomeOtherDecorator
 * Purpose: Testing switch statements for decorators
 * Currently adds a WeaponComponent with
 * BulletType.FIRE AND ShotType.TWIN default
 */

public class SomeOtherDecorator extends EnemyDecorator {
    // TODO: REMOVE WEAPONCOMPONENT
    private int fireCount = 5;
    private WeaponComponent weapon;

    public SomeOtherDecorator(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);

        weapon = new WeaponComponent(screen.getBulletPatterns(), BulletType.FIRE, ShotType.TWIN);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // TODO: REMOVE - ONLY USING THIS FOR TESTING WEAPONCOMPONENT
        // ONLY ALLOWS SINGLE SHOT
        if (fireCount >= 1) {
            weapon.setType(BulletType.FIRE);
            weapon.fire(this);
            fireCount--;
        }
    }
}
