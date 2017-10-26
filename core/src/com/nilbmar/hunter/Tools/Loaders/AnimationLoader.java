package com.nilbmar.hunter.Tools.Loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 9/25/17.
 *
 * Purpose: Load variables for sprite animation from JSON
 */

public class AnimationLoader implements Loader {
    private Entity entity;
    private AnimationData data;
    private String file;

    // TODO: SHOULD THIS BE ACCEPTING ENTITY TO LOAD INTO OR FRAMESCOMPONENT OR ANIMATIONCOMPONENT

    public void setEntity(Entity entity) { this.entity = entity; }
    @Override
    public void setFile(String file) { this.file = file; }

    public AnimationData getData() { return data; }

    @Override
    public void load() {
        FileHandle handle = Gdx.files.internal(file);
        String fileContent = handle.readString();
        Json json = new Json();
        data = json.fromJson(AnimationData.class, fileContent);

    }
}
