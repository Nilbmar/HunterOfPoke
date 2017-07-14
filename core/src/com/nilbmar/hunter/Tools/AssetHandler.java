package com.nilbmar.hunter.Tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.nilbmar.hunter.Enums.PlayerType;

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
    private final String enemyPack = spritesLocation + "characters.pack";
    private final String bulletPack = spritesLocation + "ammo_and_effects.pack";
    private final String itemPack = spritesLocation + "pickups.pack";

    // Player Character Images
    private final String male = "charMale.pack";
    private final String female = "charFem.pack";
    private final String alien = "charAlien.pack";
    private String playerPack = spritesLocation + alien;
    private PlayerType currentPlayerType = PlayerType.ALIEN;


    // Sprite Variables
    private TextureAtlas playerAtlas;
    private TextureAtlas enemyAtlas;
    private TextureAtlas bulletAtlas;
    private TextureAtlas itemAtlas;



    public void loadImages() {
        loadPlayerImages();
        manager.load(enemyPack, TextureAtlas.class);
        manager.load(bulletPack, TextureAtlas.class);
        manager.load(itemPack, TextureAtlas.class);


    }

    // Allow changing of player images
    private void loadPlayerImages() { manager.load(playerPack, TextureAtlas.class); }
    private void setPlayerTextureAtlas() { playerAtlas = manager.get(playerPack); }

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

    public PlayerType getCurrentPlayerType() { return currentPlayerType;}

    // Change player's sprites
    public void setPlayerPack(PlayerType playerType) {
        switch(playerType) {
            case FEMALE:
                playerPack = spritesLocation + female;
                currentPlayerType = PlayerType.FEMALE;
                break;
            case MALE:
                playerPack = spritesLocation + male;
                currentPlayerType = PlayerType.MALE;
                break;
            case ALIEN:
                playerPack = spritesLocation + alien;
                currentPlayerType = PlayerType.ALIEN;
                break;
        }

        loadPlayerImages();
        manager.finishLoading(); // Make sure new images are loaded before switching
        setPlayerTextureAtlas();
    }

    public void dispose() {
        manager.dispose();
    }
}
