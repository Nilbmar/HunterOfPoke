package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 4/9/17.
 */

public class Pickup extends com.nilbmar.hunter.Entities.TileObjects.InteractiveTileObject {
    //private PlayScreen screen;
    private float posX;
    private float posY;

    public Pickup(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }

    public float getX() { return posX + 16 / 2 / HunterOfPoke.PPM; }
    public float getY() { return posY + 16 / 2 / HunterOfPoke.PPM; }
}
