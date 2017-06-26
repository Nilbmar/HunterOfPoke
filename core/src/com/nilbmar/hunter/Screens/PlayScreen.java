package com.nilbmar.hunter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nilbmar.hunter.Entities.Bullets.Bullet;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Entities.Spawns;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Scenes.Hud;
import com.nilbmar.hunter.Tools.B2WorldCreator;
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

    private Box2DDebugRenderer b2dr;
    private boolean viewRenderLines;

    // Allow toggling of HUD On and Off
    private Hud hud;
    private boolean viewHUD;

    // Input Handler
    private InputHandler input;

    // Bullets
    private BulletCreator bulletCreator;
    private BulletPatternHandler bulletPatterns;

    // Sprite Variables
    private TextureAtlas playerAtlas;
    private TextureAtlas enemyAtlas;
    private TextureAtlas bulletAtlas;
    private TextureAtlas itemAtlas;

    public PlayScreen(HunterOfPoke game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(HunterOfPoke.V_WIDTH / HunterOfPoke.PPM, HunterOfPoke.V_HEIGHT / HunterOfPoke.PPM, gameCam);
        hud = new Hud(game.batch);
        viewHUD = false;

        // Load Sprites
        playerAtlas = new TextureAtlas("sprites/charFem.pack");
        enemyAtlas = new TextureAtlas("sprites/characters.pack");
        bulletAtlas = new TextureAtlas("sprites/ammo_and_effects.pack");
        itemAtlas = new TextureAtlas("sprites/pickups.pack");

        //enemies = new Array<Enemy>();
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
        viewRenderLines = false;


        bulletCreator = new BulletCreator(this);
        bulletPatterns = new BulletPatternHandler(bulletCreator);

        player = new Player(this, worldCreator.getPlayerSpawnX(), worldCreator.getPlayerSpawnY());
        hud.setPlayerName(player.getName());

        input = new InputHandler(this);

        world.setContactListener(new WorldContactListener());
    }



    public TextureAtlas getPlayerAtlas() { return playerAtlas; }
    public TextureAtlas getEnemyAtlas() { return enemyAtlas; }
    public TextureAtlas getBulletAtlas() { return bulletAtlas; }
    public TextureAtlas getItemAtlas() { return itemAtlas; }
    public World getWorld() { return world; }
    public TiledMap getMap() { return map; }
    public Hud getHUD() { return hud; }
    public B2WorldCreator getWorldCreator() { return worldCreator; }
    public BulletCreator getBulletCreator() { return bulletCreator; }
    public BulletPatternHandler getBulletPatterns() { return bulletPatterns; }
    public Player getPlayer() { return player; }
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

        for (Spawns spawn : worldCreator.getAllSpawnsArray()) {
            if (player.getB2Body().getPosition().x > spawn.getX()) {
                if (spawn.getType() == SpawnType.ENEMY) {
                    //entities.add(spawn.spawnEnemy());
                    //entities.add(spawn.getMonster());
                    entities.add(spawn.getEnemy());
                    Gdx.app.log("Enemy Spawn", "X: " + spawn.getX() + " Y: " + spawn.getY());
                } else if (spawn.getType() == SpawnType.ITEM) {
                    entities.add(spawn.spawnItem());
                    Gdx.app.log("Item Spawn", "X: " + spawn.getX() + " Y: " + spawn.getY());
                }
                worldCreator.getAllSpawnsArray().removeValue(spawn, true);
                spawn.dispose();
            }
        }

        // TODO: SEPARATE INTO UPDATES FOR ENEMIES/ITEMS/ETC..
        // Will allow better control of layering items
        for (Entity entity : entities) {
            entity.update(deltaTime);
        }

        /*for (Enemy enemy : enemies) {
            enemy.update(deltaTime);
        }*/

        // Update for all bullets
        for (Bullet bullet : bulletCreator.getAllBulletsArray()) {
            bullet.update(deltaTime);
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

        player.draw(game.batch);

        game.batch.end();


        // Set batch to now draw what the HUD camera sees
        // ONLY if viewHUD is enabled (H key) - default is false
        if (viewHUD) {
            game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
            hud.stage.draw();
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
        playerAtlas.dispose();
        bulletAtlas.dispose();
        enemyAtlas.dispose();
        itemAtlas.dispose();
        //sound.dispose();
    }
}
