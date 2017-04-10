package com.dimaska.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dimaska.game.GameConst;
import com.dimaska.game.MyGame;

/**
 * Created by Администратор on 01.04.2017.
 */

public class ScoresScreen implements Screen {

    private Stage UI;
    private MyGame game;

    ScoresScreen(MyGame game){
        UI=new Stage();
        this.game=game;
    }

    @Override
    public void show() {
        for(int i=0;i<10;i++){
            int temp=game.user.getInteger("Score"+i,-1);
            Label label=new Label(temp+"",game.skin);
            label.setPosition(GameConst.X/2-label.getWidth()/2,GameConst.Y*(10-i)/11-label.getHeight()/2);
            label.setColor(0,0,0,1);
            UI.addActor(label);
        }

        OrthographicCamera camera=new OrthographicCamera();
        camera.setToOrtho(false,GameConst.X,GameConst.Y);
        Viewport viewport=UI.getViewport();
        viewport.setCamera(camera);

        Gdx.input.setInputProcessor(UI);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        UI.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        UI.dispose();
    }
}
