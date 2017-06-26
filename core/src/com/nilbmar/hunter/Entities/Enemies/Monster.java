package com.nilbmar.hunter.Entities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/25/17.
 */

public class Monster extends Enemy {
    protected int regionBeginX;
    protected int regionBeginY;
    protected int regionWidth;
    protected int regionHeight;
    protected float boundsBeginX;
    protected float boundsBeginY;
    protected float boundsWidth;
    protected float boundsHeight;
    protected int acceleration;
    protected float offsetSpriteX;

    public Monster(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);
    }

    public void loadJson(String file) {
        FileHandle handle = Gdx.files.internal(file);
        String fileContent = handle.readString();
        Json json = new Json();
        MonsterData data = json.fromJson(MonsterData.class, fileContent);

        name = data.name;
        hitPoints = data.hitPoints;
        maxHitPoints = data.maxHitPoints;


        regionName = data.regionName;
        offsetSpriteY = data.offsetSpriteY / HunterOfPoke.PPM;

        regionBeginX = data.regionBeginX;
        regionBeginY = data.regionBeginY;
        regionWidth = data.regionWidth;
        regionHeight = data.regionHeight;

        boundsBeginX = data.boundsBeginX;
        boundsBeginY = data.boundsBeginY;
        boundsWidth = data.boundsWidth;
        boundsHeight = data.boundsHeight;

        acceleration = data.acceleration;


    }

    public void finalize() {
        defineBody();
        TextureRegion charStill = new TextureRegion(
                screen.getEnemyAtlas().findRegion(regionName),
                regionBeginX, regionBeginY, regionWidth, regionHeight);
        setBounds(boundsBeginX / HunterOfPoke.PPM, boundsBeginY / HunterOfPoke.PPM,
                boundsWidth / HunterOfPoke.PPM, boundsHeight / HunterOfPoke.PPM);
        setRegion(charStill);

        // Movement
        setCurrentAcceleration(acceleration);
    }
}
