package com.nilbmar.hunter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nilbmar.hunter.AI.Utils.Vision;
import com.nilbmar.hunter.Entities.Boxes.Box;
import com.nilbmar.hunter.Entities.Bullets.Bullet;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Entities.Spawns;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Scenes.Hud;
import com.nilbmar.hunter.Tools.AssetHandler;
import com.nilbmar.hunter.Tools.B2WorldCreator;
import com.nilbmar.hunter.Tools.BoxCreator;
import com.nilbmar.hunter.Tools.BoxPatternHandler;
import com.nilbmar.hunter.Tools.BulletCreator;
import com.nilbmar.hunter.Tools.BulletPatternHandler;
import com.nilbmar.hunter.Enums.Borders;
import com.nilbmar.hunter.Enums.SpawnType;
import com.nilbmar.hunter.Tools.InputHandler;
import com.nilbmar.hunter.Tools.WorldContactListener;

/**
 * Created by sysgeek on 4/7/17.
 *
 * Basic battle screen for game
 * TODO: Currently using hardcoded map
 */

public class PlayScreen implements Screen {
    private HunterOfPoke game;

    // Camera Variables
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private float playerPosX;
    private float playerPosY;



    // Player Variables
    private Player player;
    private Array<Enemy> enemies;
    private Array<Entity> entities;

    // Map Variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d Variables
    private World world;
    private B2WorldCreator worldCreator;

    // Measure distance to spawn Entities before they're on screen
    private Double distanceToPlayer = 0.0;
    private Double spawnWhenUnder = 2.5;
    private Vision visionComponent;



    private Box2DDebugRenderer b2dr;
    private boolean viewRenderLines = false;

    // Allow toggling of HUD On and Off
    private Hud hud;
    private boolean viewHUD;

    // Input Handler
    private InputHandler input;

    // Bullets
    private BulletCreator bulletCreator;
    private BulletPatternHandler bulletPatterns;

    // Boxes
    private BoxCreator boxCreator;
    private BoxPatternHandler boxPatterns;

    private AssetHandler assets = new AssetHandler();


    public PlayScreen(HunterOfPoke game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(HunterOfPoke.V_WIDTH / HunterOfPoke.PPM, HunterOfPoke.V_HEIGHT / HunterOfPoke.PPM, gameCam);

        // Load Assets
        assets.loadImages();
        assets.manager.finishLoading();
        assets.setTextureAtlases();

        enemies = new Array<Enemy>();
        entities = new Array<Entity>();

        // Load Maps
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / HunterOfPoke.PPM);

        // Center game cam on character
        playerPosX = gamePort.getWorldWidth() / 2;
        playerPosY = gamePort.getWorldHeight() / 2;
        gameCam.position.set(playerPosX, playerPosY, 0);

        // Box2D World
        world = new World(new Vector2(0, 0), true);
        worldCreator = new B2WorldCreator(this, hud);

        // Box2D Render Lines - Default Not to View
        b2dr = new Box2DDebugRenderer();

        bulletCreator = new BulletCreator(this);
        bulletPatterns = new BulletPatternHandler(bulletCreator);

        boxCreator = new BoxCreator(this);
        boxPatterns = new BoxPatternHandler(boxCreator);

        player = new Player(this, worldCreator.getPlayerSpawnX(), worldCreator.getPlayerSpawnY());
        hud = new Hud(game.batch, player);
        viewHUD = true;
        player.setupHudObservers(hud);

        input = new InputHandler(this);

        visionComponent = new Vision(this);
        visionComponent.setupRaycast(player);

        world.setContactListener(new WorldContactListener());
    }



    public AssetHandler getAssetsHandler() { return assets; }
    public World getWorld() { return world; }
    public TiledMap getMap() { return map; }
    public Hud getHUD() { return hud; }
    public B2WorldCreator getWorldCreator() { return worldCreator; }
    public BoxCreator getBoxCreator() { return boxCreator; }
    public BoxPatternHandler getBoxPatterns() { return boxPatterns; }
    public BulletCreator getBulletCreator() { return bulletCreator; }
    public BulletPatternHandler getBulletPatterns() { return bulletPatterns; }
    public Player getPlayer() { return player; }
    public Array<Entity> getEntities() { return entities; }
    public Array<Enemy> getEnemies() { return enemies; }
    public boolean getViewRenderLines() { return viewRenderLines; }
    public boolean getViewHUD() { return viewHUD; }

    public void setViewRenderLines(boolean viewRenderLines) { this.viewRenderLines = viewRenderLines; }
    public void setViewHUD(boolean viewHUD) {
        this.viewHUD = viewHUD;
    }

    public void update(float deltaTime) {
        input.update(deltaTime);

        // How often Box2D calculates per second
        // Higher numbers mean more precise collision but slower
        // timeStep, velocityIterations, positionIterations
        world.step(1 / 60f, 6, 2);

        player.update(deltaTime);

        bulletCreator.update(deltaTime);
        boxCreator.update(deltaTime);


        // Spawn Enemy or Items based on string in map
        for (Spawns spawn : worldCreator.getAllSpawnsArray()) {
            distanceToPlayer = visionComponent.getDistance(player.getPosition(), spawn.getPosition());

            if (distanceToPlayer <= spawnWhenUnder) {
                // Spawn Enemy
                if (spawn.getType() == SpawnType.ENEMY) {
                    Enemy enemy = spawn.spawnEnemy();
                    enemy.setSteeringAI();
                    //entities.add(enemy); //spawn.spawnEnemy());
                    entities.add(enemy);
                    enemies.add(enemy);
                } else if (spawn.getType() == SpawnType.ITEM) {
                    // Spawn Item
                    entities.add(spawn.spawnItem());
                }
                worldCreator.getAllSpawnsArray().removeValue(spawn, true);
                spawn.dispose();
            }
        }

        // TODO: SEPARATE INTO UPDATES FOR ENEMIES/ITEMS/ETC..
        // Will allow better control of layering items
        for (Entity entity : entities) {
            entity.update(deltaTime);

            // Set LoS for Enemy->Player within distance
            if (entity.getEntityType() == EntityType.ENEMY) {
                ((Enemy) entity).setHasLoStoPlayer(
                        visionComponent.hasLoS(entity, player, ((Enemy) entity).getDistanceForLOS()));
                ((Enemy) entity).setIsFacingPlayer(
                        visionComponent.isFacing(entity, player));
            }
        }

        // Update for all bullets
        for (Bullet bullet : bulletCreator.getAllBulletsArray()) {
            bullet.update(deltaTime);
        }

        for (Box box : boxCreator.getAllBoxesArray()) {
            box.update(deltaTime);
        }

        // CENTER CAMERA ON PLAYER
        // WITH OFFSETS IF TOO CLOSE TO BORDERS
        playerPosX = player.getB2Body().getPosition().x;
        playerPosY = player.getB2Body().getPosition().y;
        gameCam.position.y = playerPosY;

        // X offset
        if (playerPosX >= Borders.LEFT.value()
                && playerPosX <= Borders.RIGHT.value()) {
            gameCam.position.x = playerPosX;
        } else if (playerPosX < Borders.LEFT.value()) {
            gameCam.position.x = Borders.LEFT.value();
        } else if (playerPosX > Borders.RIGHT.value()) {
            gameCam.position.x = Borders.RIGHT.value();
        }

        // Y offset
        if (playerPosY >= Borders.BOTTOM.value()
                && playerPosY <= Borders.TOP.value()) {
            gameCam.position.y = playerPosY;
        } else if (playerPosY < Borders.BOTTOM.value()) {
            gameCam.position.y = Borders.BOTTOM.value();
        } else if (playerPosY > Borders.TOP.value()) {
            gameCam.position.y = Borders.TOP.value();
        }

        if (viewHUD) {
            hud.update(deltaTime);
        }

        gameCam.update();

        // Only render what the gameCam can see
        renderer.setView(gameCam);

    }

    /* START OF IMPLEMENTED METHODS */
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        // TODO: possibly remove when game is finished
        // Toggle on and off render lines
        if (viewRenderLines) {
            b2dr.render(world, gameCam.combined);
        }

        // Only render what the camera can see
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        /* TODO: SEPARATE OUT INTO ENEMIES/ITEMS/ETC...
                Will allow better control of what is layered
                on top of each other
         */

        for (Entity entity : entities) {
            entity.draw(game.batch);
        }

        /*for (Enemy enemy : enemies) {
            enemy.draw(game.batch);
        }*/

        /* TODO: CHECK PLAYER'S DIRECTION
                Will allow rendering on top of player
                if facing down
                Behind player, if facing up
         */
        for (Bullet bullets : bulletCreator.getAllBulletsArray()) {
            bullets.draw(game.batch);
        }

        for (Box box : boxCreator.getAllBoxesArray()) {
            box.draw(game.batch);
        }

        player.draw(game.batch);

        game.batch.end();


        // Set batch to now draw what the HUD camera sees
        // ONLY if viewHUD is enabled (H key) - default is false
        if (viewHUD) {
            game.batch.setProjectionMatrix(hud.getStage().getCamera().combined);
            hud.getStage().draw();
        }

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
        world.dispose();
        map.dispose();
        b2dr.dispose();
        hud.dispose();
        assets.dispose();
        //sound.dispose();
    }
}
