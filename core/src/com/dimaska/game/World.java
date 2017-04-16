package com.dimaska.game;

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
    private TrajectoryComponent[] trajectoryComponent;
    private PlayScreen screen;
    private int[][] waves;
    private int currentWave;


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
        trajectoryComponent=new TrajectoryComponent[3];
        for(int index=0;index<3;index++)
        trajectoryComponent[index]=new TrajectoryComponent(0,GameConst.Cockroach_Speed+100*index,100,350);

        OrthographicCamera camera=new OrthographicCamera();
        camera.setToOrtho(false,GameConst.X,GameConst.Y);
        batch.setProjectionMatrix(camera.combined);
        addObserver(scope);
    }

    public void update(boolean isLogic,float delta){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(isLogic){
            isLose();
            onCreateRandomCockroach();
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


    private void onCreateRandomCockroach(){
        if(Math.random()<GameConst.Cockroach_CreateChance){
            float powerChange=(float)(Math.random()*2);
            Cockroach temp;
            if(powerChange<=0.2){
                BumState state=BumState.Live;
                temp = new Cockroach((float) (Math.random() * (GameConst.X - GameConst.Cockroach_Width)),
                        GameConst.Y + GameConst.Cockroach_Height,
                        GameConst.BumCockroach_Width,
                        GameConst.BumCockroach_Height,
                        bumGrapicComponent,
                        bodyComponent,
                        new BumPowerComponent(),
                        trajectoryComponent[(int) (Math.random() * 3)],
                        state);
            }else if(powerChange<=1) {
                NormallState state=Live;
                temp = new Cockroach((float) (Math.random() * (GameConst.X - GameConst.Cockroach_Width)),
                        GameConst.Y + GameConst.Cockroach_Height,
                        GameConst.Cockroach_Width,
                        GameConst.Cockroach_Height,
                        graphicComponent,
                        bodyComponent,
                        new TeleportPowerComponent(2),
                        trajectoryComponent[(int) (Math.random() * 3)],
                        state);
            }else{
                NormallState state=Live;
                temp = new Cockroach((float) (Math.random() * (GameConst.X - GameConst.Cockroach_Width)),
                        GameConst.Y + GameConst.Cockroach_Height,
                        GameConst.Cockroach_Width,
                        GameConst.Cockroach_Height,
                        graphicComponent,
                        bodyComponent,
                        new NullPowerComponent(),
                        trajectoryComponent[(int) (Math.random() * 3)],
                        state);
            }
            temp.powerComponent.setCockroach(temp);
            cockroaches.add(temp);
        }
    }

    private void isLose() {
        for (int index = 0; index < cockroaches.size(); index++) {
            Cockroach temp = cockroaches.get(index);
            if(temp.getY()+temp.getHeight()*0.8<=0){
                //Событие проигрыша
                notify(0,0,"Lose");
                cockroaches.clear();
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
                        notify(copy,"Release");
                        copy.Released();
                        //Gdx.app.log("Game", "Score +" + 100 + ". Current account: " + scope.getScore());
                        return true;
                    }
                }
            }
            //notify(screenX,screenY,"Miss");
        }
        return false;
    }

    public void dispose(){
        background.dispose();
        batch.dispose();
        deleteObject();
    }

    public void restartLvl(XmlReader.Element lvl) {

    }

    public void setLvl(XmlReader.Element lvl) {

    }

    /*
    public boolean isSlime() {
        return isSlime;
    }

    public void setSlime(boolean slime) {
        isSlime = slime;
    }
    */
}
