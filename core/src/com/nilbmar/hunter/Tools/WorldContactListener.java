package com.nilbmar.hunter.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nilbmar.hunter.Entities.Bullets.Bullet;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.HunterOfPoke;

/**
 * Created by sysgeek on 4/23/17.
 */

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        // There are two fixtures in a contact
        Fixture fixtA = contact.getFixtureA();
        Fixture fixtB = contact.getFixtureB();

        int collisionDef = fixtA.getFilterData().categoryBits | fixtB.getFilterData().categoryBits;

        // Handle collisions and who dies
        switch(collisionDef) {
            case HunterOfPoke.BULLET_BIT | HunterOfPoke.GROUND_BIT:
                if (fixtA.getFilterData().categoryBits == HunterOfPoke.BULLET_BIT) {
                    ((Bullet) fixtA.getUserData()).onHit();
                } else {
                    ((Bullet) fixtB.getUserData()).onHit();
                }
                break;
            case HunterOfPoke.PLAYER_BIT | HunterOfPoke.ENEMY_BIT:
                if (fixtA.getFilterData().categoryBits == HunterOfPoke.ENEMY_BIT) {
                    // TODO: NO ONHIT BUILT YET
                    Gdx.app.log(((Enemy) fixtA.getUserData()).getName(), "Haha! I hit you, stupid player!");
                } else {
                    Gdx.app.log(((Enemy) fixtB.getUserData()).getName(), "Haha! I hit you, stupid player!");
                    // TODO: NO ONHIT BUILT YET
                    //((Player) fixtB.getUserData()).onHit();
                }
                break;
            case HunterOfPoke.ENEMY_BIT | HunterOfPoke.GROUND_BIT:
                Gdx.app.log("Enemy Collision", "Enemy collided with ground");
                break;
            case HunterOfPoke.ITEM_BIT | HunterOfPoke.PLAYER_BIT:
                if (fixtA.getFilterData().categoryBits == HunterOfPoke.PLAYER_BIT) {
                    ((Player) fixtA.getUserData()).onPickup((Item) fixtB.getUserData());

                    // TODO: CREATE DESTROY METHOD
                    //((Item) fixtB.getUserData()).destroy();
                } else {
                    ((Player) fixtB.getUserData()).onPickup((Item) fixtA.getUserData());

                    Gdx.app.log("Item Pickup", ((Item) fixtA.getUserData()).getName());
                    // TODO: CREATE DESTROY METHOD
                    //((Item) fixtA.getUserData()).destroy();
                }
                break;

            // Enemy tramples item pickup
            case HunterOfPoke.ITEM_BIT | HunterOfPoke.ENEMY_BIT:
                if (fixtA.getFilterData().categoryBits == HunterOfPoke.ENEMY_BIT) {
                    Gdx.app.log("Item", ((Enemy) fixtA.getUserData()).getEntityType()
                            + " " + ((Enemy) fixtA.getUserData()).getName()
                            + " trampled item.");
                    // TODO: CREATE DESTROY METHOD
                    //((Item) fixtB.getUserData()).destroy();
                } else {
                    Gdx.app.log("Item", ((Enemy) fixtB.getUserData()).getEntityType()
                            + " " + ((Enemy) fixtB.getUserData()).getName()
                            + " trampled item.");
                    // TODO: CREATE DESTROY METHOD
                    //((Item) fixtA.getUserData()).destroy();
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
