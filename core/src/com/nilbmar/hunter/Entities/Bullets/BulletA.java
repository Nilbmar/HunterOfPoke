package com.nilbmar.hunter.Entities.Bullets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 4/22/17.
 *
 * Bullet: BulletA
 * Purpose: Ball shaped bullet
 * TODO: RENAME
 * USED THE NAME BulletA BECAUSE OF
 * IMAGE FILE NAMED BulletA
 */

public class BulletA extends Bullet {


    public BulletA(PlayScreen screen, float x, float y, Vector2 v, float rotation, String firedBy) {
        super(screen, x, y, v, rotation, firedBy);

        regionName = "bulleta";
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(atlas.findRegion(regionName), 0, 0, 6, 6));
        animation = new Animation<TextureRegion>(0f, frames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        imageComponent.setRegion((TextureRegion) animation.getKeyFrame(0));
        //setRegion(screen.getBulletAtlas().findRegion("bulleta"), 0, 0, 6, 6);
        imageComponent.setBounds(imageComponent.getX(), imageComponent.getY(), 6 / HunterOfPoke.PPM, 6 / HunterOfPoke.PPM);

        defineBody();
        lifespan = 5f;
        velocity = v;
        acceleration = 2;
        coolOffTime = 0.15f;

        movement = new MoveComponent(b2Body);
        b2Body.setActive(true);
    }

    @Override
    protected void defineShape() {
        bodyComponent.setFixtureDef(Shape.Type.Circle, 3); // CircleShape - radius of 5
        //bodyComponent.setFixtureDef(Shape.Type.Polygon, 4, 4); // PolygonShape - Set as Box
    }
}
