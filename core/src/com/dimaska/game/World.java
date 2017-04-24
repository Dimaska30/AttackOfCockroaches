package com.dimaska.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.XmlReader;
import com.dimaska.game.Components.BodyComponent;
import com.dimaska.game.Components.BumGrapicComponent;
import com.dimaska.game.Components.BumPowerComponent;
import com.dimaska.game.Components.NullPowerComponent;
import com.dimaska.game.Components.TeleportPowerComponent;
import com.dimaska.game.Components.TrajectoryComponent;
import com.dimaska.game.Components.UsualGraphicComponent;
import com.dimaska.game.Screens.PlayScreen;
import com.dimaska.game.States.BumState;
import com.dimaska.game.States.NormallState;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.dimaska.game.States.NormallState.Live;

/**
 * Created by dimaska on 08.02.17.
 */

public class World extends Subject{

    private Texture background;
    private SpriteBatch batch;
    private ArrayList<Cockroach> cockroaches;
    public ScoreController scope;
    private UsualGraphicComponent graphicComponent;
    private BumGrapicComponent bumGrapicComponent;
    private BodyComponent bodyComponent;
    private PlayScreen screen;
    private int[][] waves;
    private int currentWave;
    private XmlReader.Element lvl;


    public World(PlayScreen screen){
        this.screen=screen;
        cockroaches=new ArrayList<Cockroach>();
        scope=new ScoreController(screen.getGame());
        background=new Texture("KitchenTile.png");
        Texture live=new Texture("Cockroach.png");
        Texture crash=new Texture("CrashedCockroach.png");
        Texture bumTextures=new Texture("bumCockroach.png");
        Texture slime=new Texture("bum.png");
        bumGrapicComponent=new BumGrapicComponent(bumTextures,slime);

        batch=new SpriteBatch();
        graphicComponent=new UsualGraphicComponent(live,crash);

        bodyComponent=new BodyComponent(0.2f,0.1f,0.2f,0.1f);

        OrthographicCamera camera=new OrthographicCamera();
        camera.setToOrtho(false,GameConst.X,GameConst.Y);
        batch.setProjectionMatrix(camera.combined);
        addObserver(scope);
        addObserver(screen);

        currentWave = 0;
    }

    public void update(boolean isLogic,float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (cockroaches.isEmpty()) {
            nextWave();
        }
        if(isLogic){
            isLose();
        }
        batch.begin();
        batch.draw(background,0,0,GameConst.X,GameConst.Y);
        batch.enableBlending();
        for(int i=0;i<cockroaches.size();i++) {
            Cockroach temp = cockroaches.get(i);
            if(isLogic) {
                temp.update(delta);
            }
            if(temp.stateMachine.getCurrentState()== NormallState.Dead){
                cockroaches.remove(i);

            }else{
                temp.draw(batch);
            }
        }
        batch.end();
    }

    private void isLose() {
        for (int index = 0; index < cockroaches.size(); index++) {
            Cockroach temp = cockroaches.get(index);
            if (temp.getY() + temp.getHeight() * 0.8 <= 0) {//Событие проигрыша
                cockroaches.clear();
                notify(0, 0, "Lose");

            }
        }
    }

    boolean Press(int screenX, int screenY){
        int GameY=((Gdx.graphics.getHeight()-screenY)*GameConst.Y)/Gdx.graphics.getHeight();
        int GameX= (screenX*GameConst.X/Gdx.graphics.getWidth());
        Rectangle touch=new Rectangle(GameX,GameY,1,1);
        Gdx.app.log("Game","X: "+GameX+" Y: "+GameY+";");
        if(!screen.getPause()) {
            for (int i = 0; i < cockroaches.size(); i++) {
                Cockroach copy = cockroaches.get(i);
                if (copy.getPowerComponent().MayClick()) {
                    if (copy.getBody().contains(touch)) {
                        Gdx.app.log("Game", "Cockroach is touch");
                        notify(copy,"Press");
                        copy.Pressed();
                        Gdx.app.log("Game", "Score +" + 100 + ". Current account: " + scope.getScore());
                        return true;
                    }
                }
            }
            notify(screenX,screenY,"Miss");
        }
        return false;
    }

    boolean Release(int screenX, int screenY){
        int GameY=((Gdx.graphics.getHeight()-screenY)*GameConst.Y)/Gdx.graphics.getHeight();
        int GameX= (screenX*GameConst.X/Gdx.graphics.getWidth());
        Rectangle touch=new Rectangle(GameX,GameY,1,1);
        Gdx.app.log("Game","X: "+GameX+" Y: "+GameY+";");
        if(!screen.getPause()) {
            for (int i = 0; i < cockroaches.size(); i++) {
                Cockroach copy = cockroaches.get(i);
                if (copy.getPowerComponent().MayClick()) {
                    if (copy.getBody().contains(touch)) {
                        Gdx.app.log("Game", "Cockroach is touch");
                        copy.Released();
                        notify(copy, "Release");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void dispose(){
        background.dispose();
        batch.dispose();
        deleteObject();
    }

    public void restartLvl(XmlReader.Element lvl) {
        setLvl(lvl);
    }

    public void setLvl(XmlReader.Element lvl) {
        this.lvl = lvl;
        waves = new int[lvl.getChildCount()][3];
        cockroaches.clear();
        currentWave = 0;
        CreateArray();
    }

    private void nextWave() {
        if (currentWave + 1 > waves.length) {
            notify(0, 0, "Win");
        }
        currentWave++;
        CreateArray();
        notify(0, 0, "NewWave");
    }

    private void CreateArray() {
        com.badlogic.gdx.utils.Array<XmlReader.Element> wave = lvl.getChildrenByName("wave");
        com.badlogic.gdx.utils.Array<XmlReader.Element> tempCockroach = wave.get(currentWave).getChildrenByName("cockroach");
        for (int numberCockroach = 0; numberCockroach < wave.get(currentWave).getChildCount(); numberCockroach++) {
            XmlReader.Element temp = tempCockroach.get(numberCockroach);
            Cockroach cockroach = readFromFile(temp);
            cockroach.getPowerComponent().setCockroach(cockroach);
            cockroaches.add(cockroach);

        }
    }

    private Cockroach readFromFile(XmlReader.Element cockroach) {
        String type = cockroach.getAttribute("type");
        if (type.equals("bum")) {
            TrajectoryComponent component = new TrajectoryComponent(
                    cockroach.getFloatAttribute("vx"),
                    cockroach.getFloatAttribute("vy"),
                    cockroach.getFloatAttribute("maxVx"),
                    cockroach.getFloatAttribute("maxVy"),
                    cockroach.getFloatAttribute("ax"),
                    cockroach.getFloatAttribute("ay")
            );
            return new Cockroach(cockroach.getFloatAttribute("x"),
                    cockroach.getFloatAttribute("y"),
                    GameConst.BumCockroach_Width,
                    GameConst.BumCockroach_Height,
                    bumGrapicComponent,
                    bodyComponent,
                    new BumPowerComponent(),
                    component,
                    BumState.Live
            );
        } else if (type.equals("teleport")) {
            TrajectoryComponent component = new TrajectoryComponent(
                    cockroach.getFloatAttribute("vx"),
                    cockroach.getFloatAttribute("vy"),
                    cockroach.getFloatAttribute("maxVx"),
                    cockroach.getFloatAttribute("maxVy"),
                    cockroach.getFloatAttribute("ax"),
                    cockroach.getFloatAttribute("ay")
            );
            return new Cockroach(cockroach.getFloatAttribute("x"),
                    cockroach.getFloatAttribute("y"),
                    GameConst.Cockroach_Width,
                    GameConst.Cockroach_Height,
                    graphicComponent,
                    bodyComponent,
                    new TeleportPowerComponent(cockroach.getIntAttribute("points")),
                    component,
                    NormallState.Live
            );
        } else {
            TrajectoryComponent component = new TrajectoryComponent(
                    cockroach.getFloatAttribute("vx"),
                    cockroach.getFloatAttribute("vy"),
                    cockroach.getFloatAttribute("maxVx"),
                    cockroach.getFloatAttribute("maxVy"),
                    cockroach.getFloatAttribute("ax"),
                    cockroach.getFloatAttribute("ay")
            );
            return new Cockroach(cockroach.getFloatAttribute("x"),
                    cockroach.getFloatAttribute("y"),
                    GameConst.Cockroach_Width,
                    GameConst.Cockroach_Height,
                    graphicComponent,
                    bodyComponent,
                    new NullPowerComponent(),
                    component,
                    NormallState.Live
            );
        }
    }
}
