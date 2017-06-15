package com.nilbmar.hunter.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nilbmar.hunter.HunterOfPoke;

import java.util.Locale;

/**
 * Created by sysgeek on 4/7/17.
 */

public class Hud implements Disposable {
    public Stage stage;

    // Don't want Hud to move with other viewport
    private Viewport viewport;

    private Integer worldTimer;
    private Integer score;
    private String levelName = "CHANGE";
    private String playerName = "DOOPSY";
    private String userInfo = "";
    private float timeCount;

    // TODO: PASS LEVEL AND PLAYER NAMES THROUGH CONSTRUCTOR

    private Label countdownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label playerNameLabel;
    private Label userInfoLabel;

    public Hud(SpriteBatch spriteBatch) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        playerName = playerName.toUpperCase();
        levelName = levelName.toLowerCase(); // Change this to actual lvl formatting

        viewport = new FitViewport(HunterOfPoke.V_WIDTH,
                HunterOfPoke.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, spriteBatch);



        /* CREATE AND FORMAT LABELS */
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // "%06d" is how many digits long = 6 digits
        countdownLabel = new Label(String.format(Locale.US, "%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format(Locale.US, "%06d", score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // TODO: CHANGE LEVEL NAME AND PLAYER NAME, ADD TO Hud(PARAMETERS)
        levelLabel = new Label(levelName,
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerNameLabel = new Label(playerName,
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        userInfoLabel = new Label(userInfo,
                new Label.LabelStyle(new BitmapFont(), Color.CHARTREUSE));
        // TODO: DROP SHADOWS

        /* ADD LABELS TO TABLE */
        // expandX() so labels take up equal portion of a row
        Table topRows = new Table();
        topRows.top();
        topRows.setFillParent(true);
        topRows.add(playerNameLabel).expandX().padTop(10);
        topRows.add(worldLabel).expandX().padTop(10);
        topRows.add(timeLabel).expandX().padTop(10);

        topRows.row();
        topRows.add(scoreLabel).expandX();
        topRows.add(levelLabel).expandX();
        topRows.add(countdownLabel).expandX();

        Table userInfo = new Table();
        userInfo.bottom();
        userInfo.setFillParent(true);
        userInfo.add(userInfoLabel).expandX().padBottom(25);

        /*
        Table table = new Table();
        table.top(); // Aligns center top of table
        table.setFillParent(true);

        table.add(topRows).expandX();
        table.row();
        table.add(userInfo).expandY();

        stage.addActor(table);
        */

        stage.addActor(topRows);
        stage.addActor(userInfo);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public void update(float deltaTime) {
        playerNameLabel.setText(playerName);
        userInfoLabel.setText(userInfo);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
