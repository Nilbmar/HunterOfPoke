package com.nilbmar.hunter.Tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by sysgeek on 6/30/17.
 *
 * Tool: AssetHandler
 * Purpose: Control the LibGDX AssetManager
 * and load assets into it
 */

public class AssetHandler {
    public final AssetManager manager = new AssetManager();

    // Location of texture packs for TextureAtlases
    private final String spritesLocation = "sprites/";
    private String playerPack = spritesLocation + "charFem.pack";
    private final String enemyPack = spritesLocation + "characters.pack";
    private final String bulletPack = spritesLocation + "ammo_and_effects.pack";
    private final String itemPack = spritesLocation + "pickups.pack";

    // Sprite Variables
    private TextureAtlas playerAtlas;
    private TextureAtlas enemyAtlas;
    private TextureAtlas bulletAtlas;
    private TextureAtlas itemAtlas;



    public void loadImages() {
        manager.load(playerPack, TextureAtlas.class);
        manager.load(enemyPack, TextureAtlas.class);
        manager.load(bulletPack, TextureAtlas.class);
        manager.load(itemPack, TextureAtlas.class);


    }

    public void setTextureAtlases() {
        // Sprite Variables
        playerAtlas = manager.get(playerPack);
        enemyAtlas = manager.get(enemyPack);
        bulletAtlas = manager.get(bulletPack);
        itemAtlas = manager.get(itemPack);
    }

    public TextureAtlas getPlayerAtlas() { return playerAtlas; }
    public TextureAtlas getEnemyAtlas() { return enemyAtlas; }
    public TextureAtlas getBulletAtlas() { return bulletAtlas; }
    public TextureAtlas getItemAtlas() { return itemAtlas; }

    public String getPlayerPack() { return playerPack; }
    public void setPlayerPack(String fileName) { playerPack = fileName; }

    public void dispose() {
        manager.dispose();
    }
}
