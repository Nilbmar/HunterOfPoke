package com.nilbmar.hunter.Commands;

import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.NewEntity;
import com.nilbmar.hunter.Entities.Player;

/**
 * Created by sysgeek on 6/22/17.
 *
 * Command: Change Collision
 * Purpose: Give the player invincibility
 * and undo to return to normal collision
 */

public class ChangeCollisionCommand implements Command {


    @Override
    public void execute(NewEntity entity) {
        // boolean collideWithEnemies
        ((Player) entity).resetCollision(false);
    }

    public void undo(NewEntity entity) {
        // boolean collideWithEnemies
        ((Player) entity).resetCollision(true);
    }
}
