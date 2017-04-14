package com.dimaska.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input;
import com.dimaska.game.Screens.PlayScreen;

/**
 * Created by dimaska on 08.02.17.
 */

public class GameInput implements InputProcessor {

    private World world;
    private PlayScreen screen;

    public GameInput(World world,PlayScreen screen){
        this.world=world;
        this.screen=screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("Press","X: "+screenX+" Y: "+screenY+";");
        return world.Press(screenX,screenY);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("Release","X: "+screenX+" Y: "+screenY+";");
        return world.Release(screenX,screenY);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
