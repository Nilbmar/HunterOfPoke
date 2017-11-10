package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.nilbmar.hunter.AI.States.Temperament;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Items.InvincibilityItem;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Entities.Items.SpeedBoostItem;
import com.nilbmar.hunter.Entities.Enemies.EnemyType;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Enums.ItemType;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.SpawnType;

/**
 * Created by sysgeek on 4/9/17.
 *
 * Spawns: Spawn points created by B2WorldCreator/Tiled map
 * Purpose: Spawn enemies or items, then remove themselves
 */

public class Spawns implements Disposable {
    private PlayScreen screen;
    private float posX;
    private float posY;
    private SpawnType type;
    private ItemType itemType;
    private EnemyType enemyType;
    private String decorators;

    public Spawns(PlayScreen screen, TiledMap map, float posX, float posY, SpawnType type) {
        this.screen = screen;
        this.posX = posX / HunterOfPoke.PPM;
        this.posY = posY / HunterOfPoke.PPM;
        this.type = type;
        decorators = "";
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public Enemy spawnEnemy() {
        String file = "json/entityData/" + enemyType.getName() + ".json";

        EntityLoader loader = new EntityLoader();
        loader.setEntityType(EntityType.ENEMY);
        loader.setFile(file);
        Enemy enemy = (Enemy) loader.decorate(screen, getX(), getY(), decorators);
        enemy.setEnemyType(enemyType);
        enemy.setupAnimationComponents();

        // TODO: REPLACE THIS HARDCODING
        // GET FROM SPAWN POINT IN MAP
        enemy.setupBrain(Temperament.SCARED);

        return enemy;
        //enemy.prepareToDraw();
        //return enemy;
    }

    public Item spawnItem() {
        Item item = null;
        // TODO: SPAWN SELECTION BASED ON WHAT TILED SAYS
        switch (itemType) {
            case ACCELERATION:
                item = new SpeedBoostItem(screen, getX(), getY());
                break;
            case INVINCIBILITY:
                item = new InvincibilityItem(screen, getX(), getY());
                break;
        }
        return item;
    }

    public void setItemType(ItemType itemType) { this.itemType = itemType; }
    public void setEnemyType(EnemyType enemyType) { this.enemyType = enemyType; }
    // TODO: SEPARATE STRING INTO ARRAY
    public void setDecorators(String decorators) { this.decorators = decorators; }

    public float getX() { return posX + 16 / 2 / HunterOfPoke.PPM; }
    public float getY() { return posY + 16 / 2 / HunterOfPoke.PPM; }
    public SpawnType getType() { return type; }

    @Override
    public void dispose() {

    }
}
