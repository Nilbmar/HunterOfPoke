package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Enemies.Monster;
import com.nilbmar.hunter.Entities.Items.InvincibilityItem;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Entities.Items.SpeedBoostItem;
import com.nilbmar.hunter.Enums.EnemyType;
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

    public Spawns(PlayScreen screen, TiledMap map, float posX, float posY, SpawnType type) {
        this.screen = screen;
        this.posX = posX / HunterOfPoke.PPM;
        this.posY = posY / HunterOfPoke.PPM;
        this.type = type;
    }

    public Enemy getEnemy() {
        String file = "json/" + enemyType.getName() + ".json";

        EntityLoader loader = new EntityLoader(screen, getX(), getY(), file, EntityType.ENEMY);
        Enemy enemy = (Enemy) loader.load();
        //enemy.finalize();
        return enemy;
    }

    public Monster getMonster() {
        String file = "json/" + enemyType.getName() + ".json";

        Monster monster = new Monster(screen, getX(), getY());
        monster.loadJson(file);
        monster.finalize();

        return monster;
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

    public float getX() { return posX + 16 / 2 / HunterOfPoke.PPM; }
    public float getY() { return posY + 16 / 2 / HunterOfPoke.PPM; }
    public SpawnType getType() { return type; }

    @Override
    public void dispose() {

    }
}
