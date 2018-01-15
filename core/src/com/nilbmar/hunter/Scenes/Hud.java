package com.nilbmar.hunter.Scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Observers.Observer;
import com.nilbmar.hunter.Observers.Subject;
import com.nilbmar.hunter.Scenes.HudPieces.LabelHUD;
import com.nilbmar.hunter.Scenes.HudPieces.LevelHUD;
import com.nilbmar.hunter.Scenes.HudPieces.LifeHUD;
import com.nilbmar.hunter.Scenes.HudPieces.NameHUD;
import com.nilbmar.hunter.Scenes.HudPieces.CountDownHUD;
import com.nilbmar.hunter.Scenes.HudPieces.ScoreHUD;
import com.nilbmar.hunter.Scenes.HudPieces.UserInfoHUD;

/**
 * Created by sysgeek on 4/7/17.
 *
 * Purpose: Scene to display information for the user
 */

public class Hud implements Disposable {
    private Player player;
    private Stage stage;


    // Don't want Hud to move with other viewport
    private Viewport viewport;

    private int worldTimer;
    private int score;
    private String levelName = "CHANGE";
    private String playerName = "DOOPSY";
    private String userInfo = "";
    private float timeCount;

    /* HUD PIECES */
    private LabelHUD worldHUD;
    private LabelHUD timeHUD;
    private CountDownHUD countDownHUD;
    private NameHUD nameHUD;
    private LevelHUD levelHUD;
    // TODO: PASS LEVEL AND PLAYER NAMES THROUGH CONSTRUCTOR
    // Observable HUD Pieces
    public enum HudObservers { USER_INFO, LIFE, SCORE }
    private UserInfoHUD userInfoHUD;
    private LifeHUD lifeHUD;
    private ScoreHUD scoreHUD;

    public Hud(SpriteBatch spriteBatch, Player player) {
        this.player = player;
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        playerName = playerName.toUpperCase();
        levelName = levelName.toLowerCase(); // Change this to actual lvl formatting

        viewport = new FitViewport(HunterOfPoke.V_WIDTH,
                HunterOfPoke.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, spriteBatch);

        /* CREATE HUD PIECES (currently all labels) */
        // TODO: CHANGE LEVEL NAME AND PLAYER NAME, ADD TO Hud(PARAMETERS)
        nameHUD = new NameHUD(playerName);
        levelHUD = new LevelHUD(levelName);
        lifeHUD = new LifeHUD(0, 0, player);
        userInfoHUD = new UserInfoHUD(userInfo);
        worldHUD = new LabelHUD("WORLD");   // Currently uses default LabelHUD
        timeHUD = new LabelHUD("TIME");     // Currently uses default LabelHUD

        countDownHUD = new CountDownHUD(worldTimer + "");
        scoreHUD = new ScoreHUD(score + "");

        // TODO: CHANGE THESE TO ACCEPTING HudPiece's instead of labels
        /* ADD LABELS TO TABLE */
        // expandX() so labels take up equal portion of a row
        Table topRows = new Table();
        topRows.top();
        topRows.setFillParent(true);
        topRows.add(nameHUD.getLabel()).expandX().padTop(10);
        topRows.add(worldHUD.getLabel()).expandX().padTop(10);
        topRows.add(timeHUD.getLabel()).expandX().padTop(10);
        topRows.add(lifeHUD.getLabel()).expandX().padTop(10);


        topRows.row();
        topRows.add(scoreHUD.getLabel()).expandX();
        topRows.add(levelHUD.getLabel()).expandX();
        topRows.add(countDownHUD.getLabel()).expandX();

        Table userInfo = new Table();
        userInfo.bottom();
        userInfo.setFillParent(true);
        userInfo.add(userInfoHUD.getLabel()).expandX().padBottom(25);

        // Place everything on the stage
        stage.addActor(topRows);
        stage.addActor(userInfo);   // Set at the bottom of the screen
    }



    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Stage getStage() { return stage; }

    public Observer getObserver(HudObservers obsToGet) {
        Observer observer = null;
        // USER_INFO, LIFE, SCORE
        switch (obsToGet) {
            case LIFE:
                observer = lifeHUD;
                break;
            case USER_INFO:
                observer = userInfoHUD;
                break;
            case SCORE:
                observer = scoreHUD;
                break;
        }


        return observer;
    }

    public void update(float deltaTime) {
        nameHUD.setLabel(playerName);
        userInfoHUD.setLabel(userInfo);
        userInfoHUD.update();
        lifeHUD.update();
        countDownHUD.update();
        scoreHUD.update();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
