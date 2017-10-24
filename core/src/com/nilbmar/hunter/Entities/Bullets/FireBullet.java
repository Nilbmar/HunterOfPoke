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
 * Created by sysgeek on 6/14/17.
 *
 * Bullet: FireBullet
 * Purpose: Flame shaped bullet like out of a flamethrower
 */

public class FireBullet extends Bullet {


    public FireBullet(PlayScreen screen, float startInWorldX, float startInWorldY, Vector2 v, float rotation, String firedBy) {
        super(screen, startInWorldX, startInWorldY, v, rotation, firedBy);

        regionName = "flamethrower_bullet";
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(atlas.findRegion(regionName), 0, 0, 13, 10));
        frames.add(new TextureRegion(atlas.findRegion(regionName), 13, 0, 13, 10));
        animation = new Animation<TextureRegion>(0.2f, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        imageComponent.setRegion((TextureRegion) animation.getKeyFrame(0));
        imageComponent.setBounds(imageComponent.getX(), imageComponent.getY(),
                13 / HunterOfPoke.PPM, 10 / HunterOfPoke.PPM);

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
    public void update(float deltaTime) {
        super.update(deltaTime);
        imageComponent.setRegion(getFrame(deltaTime, stateTime));
    }
}
