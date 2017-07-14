package com.nilbmar.hunter.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.nilbmar.hunter.Commands.AccelerationCommand;
import com.nilbmar.hunter.Commands.FireCommand;
import com.nilbmar.hunter.Commands.MoveCommand;
import com.nilbmar.hunter.Commands.SetPlayerPackCommand;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;

/**
 * Created by sysgeek on 4/27/17.
 *
 * Tool: InputHandler
 * Get key presses/etc...
 * and call appropriate Commands
 */

public class InputHandler {
    private PlayScreen screen;
    private Player player;
    private Vector2 direction;
    private int acceleration = 1;

    private AccelerationCommand acclCommand;
    private MoveCommand moveCommand;
    private FireCommand singleShot;
    private FireCommand twinShot;


    public InputHandler(PlayScreen screen) {
        this.screen = screen;
        this.player = screen.getPlayer();
        direction = new Vector2();

        acclCommand = new AccelerationCommand(player, acceleration);
        moveCommand = new MoveCommand();
        singleShot = new FireCommand(screen.getBulletPatterns(), BulletType.BALL, ShotType.SINGLE);
        twinShot = new FireCommand(screen.getBulletPatterns(), BulletType.BALL, ShotType.TWIN);
    }

    private void accelerate(float deltaTime) {
        // Speed Boost while holding Shift
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            acclCommand.setAcceleration(acceleration);
            acclCommand.execute(player);
        } else {
            acclCommand.undo(player);
        }
    }

    private void movement(float deltaTime) {
        direction.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direction.y += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction.y -= 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction.x += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction.x -= 1;
        }

        moveCommand.setMovement(direction);
        moveCommand.execute(player);
    }

    private void useItem(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            if (player.getHoldItem() != null) {
                player.useItem();
            }
        }
    }



    private void fireWeapon(float deltaTime) {
        // TODO: THIS IS JUST TESTING COLLISION BUT IT ISN'T SETUP FOR GROUND YET
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            //player.resetCollision();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            twinShot.setType(BulletType.BALL);
            twinShot.execute(player);
        }

        // Single Shot
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            singleShot.setType(BulletType.FIRE);
            singleShot.execute(player);
        }
    }

    // Swap between TextureAtlas packs with the TAB key
    private void setPlayerPack() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            SetPlayerPackCommand setPack = new SetPlayerPackCommand(screen.getAssetsHandler());
            setPack.execute(player);
            player.setUpdateTextureAtlas(true);
        }
    }

    // AUTO-WALK AND AUTO-FIRE - for testing
    private void auto(int accl, int moveX, int moveY, boolean awake, ShotType type) {
        acceleration = accl;
        acclCommand.setAcceleration(acceleration);
        acclCommand.execute(player);

        direction.set(moveX, moveY);

        moveCommand.setMovement(direction);
        moveCommand.execute(player);

        player.getB2Body().setAwake(awake);
        if (type == ShotType.SINGLE) {
            singleShot.execute(player);
        } else if (type == ShotType.TWIN) {
            twinShot.execute(player);
        }
    }

    // Toggles Render Lines On or Off
    private void viewRenderLines() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            boolean view = !screen.getViewRenderLines();
            screen.setViewRenderLines(view);
        }
    }

    // Toggles Heads Up Display On or Off
    private void viewHUD() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            boolean view = !screen.getViewHUD();
            screen.setViewHUD(view);
        }
    }

    public void update(float deltaTime) {
        int auto = 0;       // value sets acceleration speed in auto moveComponent
        if (auto <= 0) {
            //accelerate(deltaTime);
            movement(deltaTime);
            useItem(deltaTime);
            fireWeapon(deltaTime);
            setPlayerPack();
        } else {
            int moveX = 1;
            int moveY = -1;
            boolean awake = false;
            ShotType type = ShotType.SINGLE;
            auto(auto, moveX, moveY, awake, type); // parameter is currentAcceleration
        }

        // Toggle extra parts of the screen
        viewHUD();
        viewRenderLines();
    }
}
