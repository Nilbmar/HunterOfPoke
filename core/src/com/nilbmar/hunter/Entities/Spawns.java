package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Enemies.SwarmEnemy;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Entities.Items.SpeedBoostItem;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Entities.TileObjects.InteractiveTileObject;
import com.nilbmar.hunter.Tools.Enums.SpawnType;

/**
 * Created by sysgeek on 4/9/17.
 */

public class Spawns implements Disposable {
    private PlayScreen screen;
    private float posX;
    private float posY;
    private SpawnType type;

    public Spawns(PlayScreen screen, TiledMap map, float posX, float posY, SpawnType type) {
        this.screen = screen;
        this.posX = posX / HunterOfPoke.PPM;
        this.posY = posY / HunterOfPoke.PPM;
        this.type = type;
    }

    public Enemy spawnEnemy() {
        Enemy enemy = new SwarmEnemy(screen, getX(), getY());
        return enemy;
    }

    public Item spawnItem() {
        Item item = new SpeedBoostItem(screen, getX(), getY());
        return item;
    }

    public float getX() { return posX + 16 / 2 / HunterOfPoke.PPM; }
    public float getY() { return posY + 16 / 2 / HunterOfPoke.PPM; }
    public SpawnType getType() { return type; }

    @Override
    public void dispose() {

    }
}
