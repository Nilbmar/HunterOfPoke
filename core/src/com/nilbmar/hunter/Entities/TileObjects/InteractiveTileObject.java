package com.nilbmar.hunter.Entities.TileObjects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nilbmar.hunter.HunterOfPoke;

/**
 * Created by sysgeek on 4/9/17.
 */

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        defineBody();
    }

    protected void defineBody() {
        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2)  / HunterOfPoke.PPM,
                (bounds.getY() + bounds.getHeight() / 2) / HunterOfPoke.PPM);

        body = world.createBody(bDef);

        shape.setAsBox(bounds.getWidth() / 2 / HunterOfPoke.PPM, bounds.getHeight() / 2 / HunterOfPoke.PPM);
        fDef.shape = shape;

        // Collision is only for detection
        // doesn't stop moveComponent of character
        //fDef.isSensor = true;

        body.createFixture(fDef);
    }
}
