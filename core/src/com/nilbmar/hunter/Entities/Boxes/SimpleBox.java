package com.nilbmar.hunter.Entities.Boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Components.MoveComponent;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 8/22/17.
 */

public class SimpleBox extends Box {
    private DirectionComponent.Direction dirThrown;



    public SimpleBox(PlayScreen screen, float x, float y, Vector2 v, float rotation) {
        super(screen, x, y, v, rotation);

        regionName = "mine1";
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(atlas.findRegion(regionName), 0, 0, 16, 16));
        animation = new Animation<TextureRegion>(0f, frames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        imageComponent.setRegion((TextureRegion) animation.getKeyFrame(0));
        imageComponent.setBounds(imageComponent.getX(), imageComponent.getY(),
                16 / HunterOfPoke.PPM, 16 / HunterOfPoke.PPM);

        defineBody();
        lifespan = 5f;
        velocity = v;
        acceleration = 2;
        coolOffTime = 0.15f;

        dirThrown = screen.getPlayer().getDirectionComponent().getDirection();

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

    public void update(float deltaTime) {
        super.update(deltaTime);
        if (!landed) { arcShot(); }
    }
}

