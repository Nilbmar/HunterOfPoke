package com.nilbmar.hunter.Commands;

import com.badlogic.gdx.Gdx;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Items.Item;
import com.nilbmar.hunter.Enums.PlayerType;
import com.nilbmar.hunter.Tools.AssetHandler;

/**
 * Created by sysgeek on 7/14/17.
 */

public class SetPlayerPackCommand implements Command {
    AssetHandler assetHandler;
    PlayerType playerType;

    public SetPlayerPackCommand(AssetHandler assetHandler) {
        this.assetHandler = assetHandler;

        Gdx.app.log("SetPlayerPackCommand", "Current Pack: " + assetHandler.getCurrentPlayerType());
        switch(assetHandler.getCurrentPlayerType()) {
            case FEMALE:
                playerType = PlayerType.MALE;
                break;
            case MALE:
                playerType = PlayerType.ALIEN;
                break;
            case ALIEN:
                playerType = PlayerType.FEMALE;
                break;
        }

        Gdx.app.log("SetPlayerPackCommand", "Set pack to " + playerType);
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    @Override
    public void execute(Entity entity) {
        assetHandler.setPlayerPack(playerType);
    }
}
