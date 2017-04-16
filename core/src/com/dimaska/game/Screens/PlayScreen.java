package com.dimaska.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dimaska.game.GameConst;
import com.dimaska.game.GameInput;
import com.dimaska.game.MyGame;
import com.dimaska.game.World;

/**
 * Created by dimaska on 08.02.17.
 */

public class PlayScreen implements Screen {

    private World world;
    private MyGame game;
    private Label labelScore;
    private Label labelCombo;
    private Label labelPause;
    private Label labelWave;
    private Label labelLvl;
    private float currentTimeForLabel;
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private int lvl;

    private Stage UI;
    private boolean pause;

    PlayScreen(MyGame game){
        this.game=game;
        currentTimeForLabel = 0;
        world=new World(this);
        labelScore=new Label("",game.skin);
        labelCombo=new Label("",game.skin);
        labelPause=new Label("Pause",game.skin);
        labelLvl = new Label("New Level!", game.skin);
        labelWave = new Label("New Wave!", game.skin);
        batch=new SpriteBatch();
        shape=new ShapeRenderer();
        UI=new Stage();
        lvl = game.user.getInteger("lastLvl");

    }

    @Override
    public void show() {
        pause=false;
        TextButton button=new TextButton("| |",game.skin);
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                pause=!pause;
                labelPause.setVisible(pause);
                return false;
            }
        });

        OrthographicCamera camera=new OrthographicCamera();
        GameInput gameInput =new GameInput(world,this);
        InputMultiplexer multiplexer=new InputMultiplexer(UI, gameInput);

        labelCombo.setHeight(GameConst.LabelScore_Height);
        labelCombo.setWidth(GameConst.LabelScore_Width);
        labelCombo.setFontScale(2);
        labelCombo.setPosition(GameConst.X-GameConst.LabelScore_Width,GameConst.Y-GameConst.LabelScore_Height*2);

        labelScore.setHeight(GameConst.LabelScore_Height);
        labelScore.setWidth(GameConst.LabelScore_Width);
        labelScore.setFontScale(2);
        labelScore.setPosition(GameConst.X-GameConst.LabelScore_Width,GameConst.Y-GameConst.LabelScore_Height);

        labelPause.setHeight(GameConst.LabelScore_Height);
        labelPause.setWidth(GameConst.LabelScore_Width);
        labelPause.setFontScale(2);
        labelPause.setPosition(GameConst.X/2-GameConst.LabelScore_Width/2,GameConst.Y/2-GameConst.LabelScore_Height/2);
        labelPause.setVisible(pause);

        button.setWidth(100);
        button.setPosition(0,GameConst.Y-button.getHeight());

        UI.addActor(button);
        UI.addActor(labelScore);
        UI.addActor(labelCombo);
        UI.addActor(labelPause);
        camera.setToOrtho(false,GameConst.X,GameConst.Y);
        Viewport viewport=UI.getViewport();
        viewport.setCamera(camera);
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.BACK) ){
            pause=!pause;
        }
        world.update(!pause,delta);
        labelScore.setText("Score:"+world.scope.getScore());
        labelCombo.setText("x"+world.scope.getCombo());
        batch.begin();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(0,0,0,255/255.f);
        if(!pause) {
            shape.rect(0, labelCombo.getY(), GameConst.X, GameConst.Y-labelCombo.getY());
        }else{
            shape.rect(0, 0, GameConst.X, GameConst.Y);
        }
        shape.end();
        UI.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        pause=true;
    }

    @Override
    public void resume() {
        pause=false;
    }

    @Override
    public void hide() {
        pause=true;
    }

    @Override
    public void dispose() {
        world.dispose();
        UI.dispose();
        batch.dispose();
    }

    public boolean getPause(){
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public MyGame getGame(){
        return game;
    }

    public void nextLevel() {

    }

    public void restartLevel() {

    }
}
