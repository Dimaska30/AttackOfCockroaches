package com.dimaska.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dimaska.game.GameConst;
import com.dimaska.game.MyGame;

/**
 * Created by Администратор on 31.03.2017.
 */

public class MainMenuScreen implements Screen {

    private MyGame game;
    private Stage UI;

    public MainMenuScreen(MyGame game){
        this.game=game;
        UI=new Stage();
    }

    @Override
    public void show() {
        TextButton buttonPlay=new TextButton("Play",game.skin);
        buttonPlay.setPosition(GameConst.X/2-buttonPlay.getWidth()/2,GameConst.Y*5/6-buttonPlay.getHeight()/2);
        buttonPlay.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayScreen(game));
                return false;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {}
        });

        TextButton buttonExit=new TextButton("Exit",game.skin);
        buttonExit.setPosition(GameConst.X/2-buttonPlay.getWidth()/2,GameConst.Y/6-buttonPlay.getHeight()/2);
        buttonExit.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return false;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {}
        });

        TextButton buttonScores=new TextButton("Scores",game.skin);
        buttonScores.setPosition(GameConst.X/2-buttonPlay.getWidth()/2,GameConst.Y*4/6-buttonPlay.getHeight()/2);
        buttonScores.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ScoresScreen(game));
                return false;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {}
        });

        TextButton buttonCredits=new TextButton("Credits",game.skin);
        buttonCredits.setPosition(GameConst.X/2-buttonPlay.getWidth()/2,GameConst.Y*2/6-buttonPlay.getHeight()/2);
        buttonCredits.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return false;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {}
        });

        TextButton buttonSettings=new TextButton("Settings",game.skin);
        buttonSettings.setPosition(GameConst.X/2-buttonPlay.getWidth()/2,GameConst.Y*3/6-buttonPlay.getHeight()/2);
        buttonSettings.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return false;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {}
        });

        OrthographicCamera camera=new OrthographicCamera();
        camera.setToOrtho(false,GameConst.X,GameConst.Y);
        Viewport viewport=UI.getViewport();
        viewport.setCamera(camera);

        UI.addActor(buttonPlay);
        UI.addActor(buttonScores);
        UI.addActor(buttonSettings);
        UI.addActor(buttonCredits);
        UI.addActor(buttonExit);

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
