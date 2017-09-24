package com.nilbmar.hunter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nilbmar.hunter.Screens.PlayScreen;

public class HunterOfPoke extends Game {
    // TODO: ADJUST SCREEN WIDTH AND HEIGHT - Possibly 1.5 size 384x288
	public static final int V_WIDTH = 256;
	public static final int V_HEIGHT =	192;
	public static final float PPM = 100;

	// Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short BULLET_BIT = 4;
	public static final short ENEMY_BIT = 8;
	public static final short ITEM_BIT = 16;

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		// Delegates rendering to active Screen
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
