package com.nilbmar.hunter.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Enums.EnemyType;
import com.nilbmar.hunter.Enums.ItemType;
import com.nilbmar.hunter.Scenes.Hud;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.Layers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nilbmar.hunter.Entities.Spawns;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Enums.SpawnType;

/**
 * Created by sysgeek on 4/9/17.
 *
 * Tool: B2WorldCreator
 * Purpose: Take a Tiled map and load objects based on layers
 */

public class B2WorldCreator {
    private PlayScreen screen;
    private Array<Spawns> spawns;

    private float playerSpawnX;
    private float playerSpawnY;


    public B2WorldCreator(PlayScreen screen, Hud hud) {
        this.screen = screen;
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        spawns = new Array<Spawns>();

        // Creating Bodies for Box2D
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        int indexMapLayer;

        indexMapLayer = Layers.GROUND.getIndex();
        for (MapObject object : map.getLayers().get(indexMapLayer)
                .getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2)  / HunterOfPoke.PPM,
                    (rect.getY() + rect.getHeight() / 2) / HunterOfPoke.PPM);

            body = world.createBody(bDef);

            shape.setAsBox(rect.getWidth() / 2 / HunterOfPoke.PPM, rect.getHeight() / 2 / HunterOfPoke.PPM);

            fDef.filter.categoryBits = HunterOfPoke.GROUND_BIT;
            fDef.filter.maskBits = (short) HunterOfPoke.PLAYER_BIT | HunterOfPoke.ENEMY_BIT
                    | HunterOfPoke.BULLET_BIT;

            fDef.shape = shape;

            body.createFixture(fDef);
        }

        // USED FOR PLACING PICKUP AND ENEMY SPAWNS
        float posX;
        float posY;

        // Create spawn points for ITEMS on PICKUPS layer
        indexMapLayer = Layers.PICKUPS.getIndex();
        for (MapObject object : map.getLayers().get(indexMapLayer)
                .getObjects().getByType(RectangleMapObject.class)) {
            posX = ((RectangleMapObject) object).getRectangle().getX();
            posY = ((RectangleMapObject) object).getRectangle().getY();

            // Used for checking if ItemType exists
            String item = object.getProperties().get("ItemType", String.class);

            Spawns spawn = new Spawns(screen, map, posX, posY, SpawnType.ITEM);

            // Check if ItemType exists and create spawn if so
            if (ItemType.contains(item) != null) {
                spawn.setItemType(ItemType.contains(item));
                spawns.add(spawn);
            }
        }

        // Create spawn points for enemies on SPAWN layer
        indexMapLayer = Layers.SPAWN.getIndex();
        for (MapObject object : map.getLayers().get(indexMapLayer)
                .getObjects().getByType(RectangleMapObject.class)) {

            posX = ((RectangleMapObject) object).getRectangle().getX();
            posY = ((RectangleMapObject) object).getRectangle().getY();

            // Used for checking if EnemyType exists
            String enemy = object.getProperties().get("EnemyType", String.class);
            String decorators = object.getProperties().get("Decorators", String.class);
            String bulletProperties = object.getProperties().get("BulletProperties", String.class);

            Spawns spawn = new Spawns(screen, map, posX, posY, SpawnType.ENEMY);

            // Check if EnemyType exists and create spawn if so
            if (EnemyType.contains(enemy) != null) {
                spawn.setEnemyType(EnemyType.contains(enemy));
                spawn.setDecorators(decorators + ":" + bulletProperties);
                spawns.add(spawn);
            }
        }

        indexMapLayer = Layers.PLAYER.getIndex();
        for (MapObject object : map.getLayers().get(indexMapLayer)
                .getObjects().getByType(RectangleMapObject.class)) {

            playerSpawnX = ((RectangleMapObject) object).getRectangle().getX();
            playerSpawnY = ((RectangleMapObject) object).getRectangle().getY();
        }
    }

    public Array<Spawns> getAllSpawnsArray() { return spawns; }
    public float getPlayerSpawnX() { return playerSpawnX / HunterOfPoke.PPM; }
    public float getPlayerSpawnY() { return playerSpawnY / HunterOfPoke.PPM; }
}
