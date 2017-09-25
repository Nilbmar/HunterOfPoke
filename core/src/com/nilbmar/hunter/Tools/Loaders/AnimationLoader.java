package com.nilbmar.hunter.Tools.Loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Created by sysgeek on 9/25/17.
 *
 * Purpose: Load variables for sprite animation from JSON
 */

public class AnimationLoader implements Loader {
    private String file;


    @Override
    public void setFile(String file) { this.file = file; }

    @Override
    public void load() {
        FileHandle handle = Gdx.files.internal(file);
        String fileContent = handle.readString();
        Json json = new Json();
        AnimationData data = json.fromJson(AnimationData.class, fileContent);

    }
}
