package com.nilbmar.hunter.Entities.Decorators;

import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Components.WeaponComponent;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/27/17.
 *
 * EnemyDecorator: WeaponDecorator
 * Purpose: Add a WeaponComponent to Enemy
 * from Tiled custom property
 */

public class WeaponDecorator extends EnemyDecorator {
    private int fireCount = 1;
    private WeaponComponent weapon;
    private BulletType bulletType;
    private ShotType shotType;



    public WeaponDecorator(PlayScreen screen, float startInWorldX, float startInWorldY,
                           BulletType bulletType, ShotType shotType) {
        super(screen, startInWorldX, startInWorldY);

        this.bulletType = bulletType;
        this.shotType = shotType;
        weapon = new WeaponComponent(screen.getBulletPatterns(), bulletType, shotType);
    }

    public WeaponDecorator(PlayScreen screen, float startInWorldX, float startInWorldY,
                           Array<String> shotProperties) {
        super(screen, startInWorldX, startInWorldY);

        // Set default properties in case never set
        bulletType = BulletType.BALL;
        shotType = ShotType.SINGLE;

        parseProperties(shotProperties);

        weapon = new WeaponComponent(screen.getBulletPatterns(), bulletType, shotType);
    }

    // Might use these setters to change bullet properties later on
    public void setBulletType(BulletType bulletType) { this.bulletType = bulletType; }

    public void setShotType(ShotType shotType) { this.shotType = shotType; }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // TODO: REMOVE - ONLY USING THIS FOR TESTING WEAPONCOMPONENT
        // ONLY ALLOWS SINGLE SHOT
        if (fireCount >= 1) {
            weapon.setType(bulletType);
            weapon.fire(this);
            fireCount--;
        }
    }

    private void parseProperties(Array<String> fullProperties) {
        Array<String> weaponProperties = null;

        // If the Tiled custom property "ShotType" contains data
        // split it apart with a blank space as the separator
        if (!fullProperties.get(1).equals("null")) {
            weaponProperties = new Array<String>(fullProperties.get(1).split(" "));
        }

        // Switch the BulletType and ShotType based on
        // custom property "ShotType" in Tiled
        // index[0] is the ShotType enum
        // index[1] is the BulletType enum
        if (weaponProperties != null) {
            shotType = ShotType.contains(weaponProperties.get(0));
            if (shotType == null) {
                shotType = ShotType.SINGLE;
            }

            bulletType = BulletType.contains(weaponProperties.get(1));
            if (bulletType == null) {
                bulletType = BulletType.BALL;
            }
        }
    }
}


