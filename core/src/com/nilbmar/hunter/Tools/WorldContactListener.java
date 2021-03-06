package com.nilbmar.hunter.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nilbmar.hunter.Entities.Boxes.Box;
import com.nilbmar.hunter.Entities.Bullets.Bullet;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Enums.EntityType;
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
                // TODO: SEPARATE BULLETS AND BOXES
                if (fixtA.getFilterData().categoryBits == HunterOfPoke.BULLET_BIT) {
                    try {
                        ((Bullet) fixtA.getUserData()).onHit();
                    } catch (ClassCastException castEx) {
                        //Gdx.app.log("ClassCastException", "not a bullet");
                    }
                    try {
                        ((Box) fixtA.getUserData()).onHit();
                    } catch (ClassCastException castEx) {
                        //Gdx.app.log("ClassCastException", "not a box");
                    }
                } else {
                    try {
                        ((Bullet) fixtB.getUserData()).onHit();
                    } catch (ClassCastException castEx) {
                        //Gdx.app.log("ClassCastException", "not a bullet");
                    }
                    try {
                        ((Box) fixtB.getUserData()).onHit();
                    } catch (ClassCastException castEx) {
                        //Gdx.app.log("ClassCastException", "not a box");
                    }
                }
                break;
            case HunterOfPoke.PLAYER_BIT | HunterOfPoke.ENEMY_BIT:
                if (fixtA.getFilterData().categoryBits == HunterOfPoke.ENEMY_BIT) {
                    //((Enemy) fixtA.getUserData()).onHit((Player) fixtB.getUserData());
                    ((Player) fixtB.getUserData()).onHit((Enemy) fixtA.getUserData());
                } else {
                    //((Enemy) fixtB.getUserData()).onHit((Player) fixtA.getUserData());
                    ((Player) fixtA.getUserData()).onHit((Enemy) fixtB.getUserData());
                }
                break;
            case HunterOfPoke.ENEMY_BIT | HunterOfPoke.GROUND_BIT:
                //Gdx.app.log("Enemy Collision", "Enemy collided with ground");
                break;
            case HunterOfPoke.ENEMY_BIT | HunterOfPoke.BULLET_BIT:
                // TODO: BREAKING WHEN BOX HITS ENEMY
                // BECAUSE IT'S CASTING TO BULLET - MAKE BOTH PROJECTILES

                // If Bullet that hits an Enemy was fired by an Enemy - ignore it
                if (fixtA.getFilterData().categoryBits == HunterOfPoke.ENEMY_BIT) {
                    EntityType firedBy = ((Bullet) (fixtB.getUserData())).getFiredBy().getEntityType();
                    // If bullet hits enemy, but is not fired by an enemy
                    if (firedBy != EntityType.ENEMY) {
                        ((Enemy) (fixtA.getUserData())).onHit((Entity) fixtB.getUserData());
                        ((Bullet) (fixtB.getUserData())).onHit();
                    }
                } else if (fixtB.getFilterData().categoryBits == HunterOfPoke.ENEMY_BIT) {
                    EntityType firedBy = ((Bullet) (fixtA.getUserData())).getFiredBy().getEntityType();
                    // If not fired by an enemy
                    if (firedBy != EntityType.ENEMY) {
                        ((Enemy) (fixtB.getUserData())).onHit((Entity) fixtA.getUserData());
                        ((Bullet) (fixtA.getUserData())).onHit();
                    }
                }
                break;
            case HunterOfPoke.PLAYER_BIT | HunterOfPoke.BULLET_BIT:
                // If Bullet that hits the Player was fired by the Player - ignore it
                if (fixtA.getFilterData().categoryBits == HunterOfPoke.PLAYER_BIT) {
                    EntityType firedBy = ((Bullet) (fixtB.getUserData())).getFiredBy().getEntityType();
                    // If not fired by an enemy
                    if (firedBy != EntityType.PLAYER) {
                        ((Player) (fixtA.getUserData())).onHit((Entity) fixtB.getUserData());
                        ((Bullet) (fixtB.getUserData())).onHit();
                    }
                } else if (fixtB.getFilterData().categoryBits == HunterOfPoke.PLAYER_BIT) {
                    EntityType firedBy = ((Bullet) (fixtA.getUserData())).getFiredBy().getEntityType();
                    // If not fired by an enemy
                    if (firedBy != EntityType.PLAYER) {
                        ((Player) (fixtB.getUserData())).onHit((Entity) fixtA.getUserData());
                        ((Bullet) (fixtA.getUserData())).onHit();
                    }
                }
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
