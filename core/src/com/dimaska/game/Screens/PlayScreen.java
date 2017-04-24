package com.dimaska.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dimaska.game.Cockroach;
import com.dimaska.game.GameConst;
import com.dimaska.game.GameInput;
import com.dimaska.game.MyGame;
import com.dimaska.game.Observer;
import com.dimaska.game.States.PlayScreenState;
import com.dimaska.game.Subject;
import com.dimaska.game.World;

import java.io.IOException;

/**
 * Created by dimaska on 08.02.17.
 */

public class PlayScreen implements Screen, Observer {

    private World world;
    private MyGame game;
    private Label labelScore;
    private Label labelCombo;
    private Label labelPause;
    private Label labelWave;
    private Label labelLvl;
    private Dialog winDialog;//!
    private Dialog loseDialog;//!
    private SpriteBatch batch;
    private ShapeRenderer shape;
    private int lvl;
    private float tempDelta;
    private float currentTimeForlabel;

    private Stage UI;
    private PlayScreenState lastState;
    private DefaultStateMachine<PlayScreen, PlayScreenState> stateMachine;

    PlayScreen(MyGame game){
        this.game=game;
        world=new World(this);
        stateMachine = new DefaultStateMachine<PlayScreen, PlayScreenState>(this, PlayScreenState.FirstStartLvl);
        labelScore=new Label("",game.skin);
        labelCombo=new Label("",game.skin);
        labelPause=new Label("Pause",game.skin);
        labelLvl = new Label("New Level!", game.skin);
        labelWave = new Label("New Wave!", game.skin);
        winDialog = new Dialog("You win!", game.skin);
        loseDialog = new Dialog("You lose :(", game.skin);
        batch=new SpriteBatch();
        shape=new ShapeRenderer();
        lvl = game.user.getInteger("lastLvl", -1);
        UI=new Stage();
        currentTimeForlabel = 0;
    }

    @Override
    public void show() {
        //pause=false;
        TextButton button=new TextButton("| |",game.skin);
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (getPause()) {
                    setPause(false);
                } else {
                    setPause(true);
                }
                return false;
            }
        });

        winDialog.setVisible(false);
        loseDialog.setVisible(false);


        Stage winUI = new Stage();
        Stage loseUI = new Stage();


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
        labelPause.setVisible(getPause());

        labelLvl.setHeight(GameConst.LabelScore_Height);
        labelLvl.setWidth(GameConst.LabelScore_Width);
        labelLvl.setFontScale(2);
        labelLvl.setPosition(GameConst.X / 2 - GameConst.LabelScore_Width / 2, GameConst.Y / 2 - GameConst.LabelScore_Height / 2);
        labelLvl.setColor(1, 0, 0, 1);

        labelWave.setHeight(GameConst.LabelScore_Height);
        labelWave.setWidth(GameConst.LabelScore_Width);
        labelWave.setFontScale(2);
        labelWave.setPosition(GameConst.X / 2 - GameConst.LabelScore_Width / 2, GameConst.Y / 2 - GameConst.LabelScore_Height / 2);
        labelWave.setColor(1, 0, 0, 1);

        button.setWidth(100);
        button.setPosition(0,GameConst.Y-button.getHeight());

        loseDialog.setPosition(0, 0);

        loseDialog.setWidth(GameConst.X);
        loseDialog.setHeight(GameConst.Y - labelCombo.getY());

        UI.addActor(button);
        UI.addActor(labelScore);
        UI.addActor(labelCombo);
        UI.addActor(labelPause);
        UI.addActor(labelLvl);
        UI.addActor(labelWave);
        UI.addActor(loseDialog);
        UI.addActor(winDialog);

        TextButton nextForWin = new TextButton("Next!", game.skin);
        nextForWin.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                nextLevel();
                return false;
            }
        });
        winUI.addActor(nextForWin);
        TextButton restartForWin = new TextButton("Restart", game.skin);
        restartForWin.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                restartLevel();
                return false;
            }
        });
        winUI.addActor(restartForWin);
        TextButton leaveForWin = new TextButton("leave", game.skin);
        leaveForWin.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leave();
                return false;
            }
        });
        winUI.addActor(leaveForWin);

        TextButton restartForLose = new TextButton("Restart", game.skin);
        restartForLose.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                restartLevel();
                return false;
            }
        });
        loseUI.addActor(restartForLose);
        TextButton leaveForLose = new TextButton("leave", game.skin);
        leaveForLose.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leave();
                return false;
            }
        });
        loseUI.addActor(leaveForLose);

        camera.setToOrtho(false,GameConst.X,GameConst.Y);

        Viewport viewportUI = UI.getViewport();
        viewportUI.setCamera(camera);

        Viewport viewportWin = winUI.getViewport();
        viewportWin.setCamera(camera);

        Viewport viewportLose = loseUI.getViewport();
        viewportLose.setCamera(camera);



        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(multiplexer);
        stateMachine.changeState(PlayScreenState.FirstStartLvl);
        restartLevel();
    }

    @Override
    public void render(float delta) {
        tempDelta = delta;
        stateMachine.update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        setPause(true);
    }

    @Override
    public void resume() {
        setPause(false);
    }

    @Override
    public void hide() {
        setPause(true);
    }

    @Override
    public void dispose() {
        world.dispose();
        UI.dispose();
        batch.dispose();
    }

    public boolean getPause(){
        return stateMachine.getCurrentState() == PlayScreenState.Pause;
    }

    private void setPause(boolean pause) {
        if (pause) {
            lastState = stateMachine.getCurrentState();
            stateMachine.changeState(PlayScreenState.Pause);
        } else {
            stateMachine.changeState(lastState);
        }
    }

    public MyGame getGame(){
        return game;
    }

    private void nextLevel() {
        lvl++;
        XmlReader reader = new XmlReader();
        try {
            XmlReader.Element lvls = reader.parse("level" + lvl + ".xml");
            world.setLvl(lvls);
        } catch (Exception e) {
            Gdx.app.log("Exception", "Can't load file: level" + lvl + ".xml");
            e.printStackTrace();
        }
    }

    private void restartLevel() {
        XmlReader reader = new XmlReader();
        FileHandle files = Gdx.files.internal("level" + lvl + ".xml");
        try {
            XmlReader.Element lvls = reader.parse(files);
            world.restartLvl(lvls);
        } catch (IOException e) {
            Gdx.app.log("Exception", "Can't load file: level" + lvl + ".xml");
            e.printStackTrace();
        }

    }

    private void leave() {
        game.user.putInteger("lastLvl", lvl);
        game.setScreen(new MainMenuScreen(game));
    }

    public float getTempDelta() {
        return tempDelta;
    }

    public World getWorld() {
        return world;
    }

    public Label getLabelScore() {
        return labelScore;
    }

    public Label getLabelCombo() {
        return labelCombo;
    }

    public Label getLabelPause() {
        return labelPause;
    }

    public Label getLabelWave() {
        return labelWave;
    }

    public Label getLabelLvl() {
        return labelLvl;
    }

    public Dialog getWinDialog() {
        return winDialog;
    }

    public Dialog getLoseDialog() {
        return loseDialog;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getShape() {
        return shape;
    }

    public int getLvl() {
        return lvl;
    }

    public Stage getUI() {
        return UI;
    }

    public DefaultStateMachine<PlayScreen, PlayScreenState> getStateMachine() {
        return stateMachine;
    }

    @Override
    public void onNotify(Cockroach cockroach, String event) {

    }

    @Override
    public void onNotify(float x, float y, String event) {
        if (event.equals("Lose")) {
            stateMachine.changeState(PlayScreenState.Lose);
        } else if (event.equals("Win")) {
            stateMachine.changeState(PlayScreenState.Win);
        } else if (event.equals("NewWave")) {
            stateMachine.changeState(PlayScreenState.NewWave);
        }
    }
}
