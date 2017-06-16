package com.nilbmar.hunter.Entities.Bullets.Subtypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Components.BodyComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.Entities.Bullets.Bullet;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.BulletCreator;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by sysgeek on 4/22/17.
 */

public class BulletA extends Bullet {


    public BulletA(PlayScreen screen, float x, float y, Vector2 v, float rotation) {
        super(screen, x, y, v, rotation);


        regionName = "bulleta";
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getBulletAtlas().findRegion(regionName), 0, 0, 6, 6));
        animation = new Animation(0f, frames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        setRegion((TextureRegion) animation.getKeyFrame(0));
        //setRegion(screen.getBulletAtlas().findRegion("bulleta"), 0, 0, 6, 6);
        setBounds(getX(), getY(), 6 / HunterOfPoke.PPM, 6 / HunterOfPoke.PPM);

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

    @Override
    public void onHit() {
        if (!destroyed) {
            setToDestroy = true;

            // Play sounds
        }
    }
}
