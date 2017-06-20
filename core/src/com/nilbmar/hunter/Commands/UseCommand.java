package com.nilbmar.hunter.Commands;

import com.badlogic.gdx.Gdx;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Items.Item;

/**
 * Created by sysgeek on 6/19/17.
 */

public class UseCommand implements Command {
    private Entity entity;
    private Item item;

    public UseCommand(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Entity entity) {
        this.entity = entity;

        if (item != null) {
            item.use(entity);
        } else {
            Gdx.app.log("Use", "item is null.");
        }
    }
}