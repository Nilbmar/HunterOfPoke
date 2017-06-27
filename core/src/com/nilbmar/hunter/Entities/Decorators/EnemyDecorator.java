package com.nilbmar.hunter.Entities.Decorators;

import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/27/17.
 */

public abstract class EnemyDecorator extends Enemy {

    public EnemyDecorator(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);
    }
}
