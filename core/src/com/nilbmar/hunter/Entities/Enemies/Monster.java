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
    public Monster(PlayScreen screen, float startInWorldX, float startInWorldY) {
        super(screen, startInWorldX, startInWorldY);
    }

    public void loadJson(String file) {
        FileHandle handle = Gdx.files.internal(file);
        String fileContent = handle.readString();
        Json json = new Json();
        EntityData data = json.fromJson(EntityData.class, fileContent);

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

        offsetSpriteX = data.offsetSpriteX;
        offsetSpriteY = data.offsetSpriteY;

        /*
        for(Object e :data.enemies){
            Position p = (Position)e;
            Gdx.app.log(GameManager.LOG, "type = " + p.type + "x = " + p.x + "y =" + p.y);
        }*/

    }

    public void setSprite() {
        TextureRegion charStill = new TextureRegion(
                screen.getEnemyAtlas().findRegion(regionName),
                regionBeginX, regionBeginY, regionWidth, regionHeight);
        setBounds(boundsBeginX / HunterOfPoke.PPM, boundsBeginY / HunterOfPoke.PPM,
                boundsWidth / HunterOfPoke.PPM, boundsHeight / HunterOfPoke.PPM);
        setRegion(charStill);

        // Movement
        //setCurrentAcceleration(acceleration);
    }

    public void finalize() {
        defineBody();
        setSprite();
        setCurrentAcceleration(acceleration);
    }
}
