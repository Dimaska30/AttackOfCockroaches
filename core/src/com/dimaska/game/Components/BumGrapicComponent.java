package com.dimaska.game.Components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dimaska.game.Cockroach;
import com.dimaska.game.GameConst;
import com.dimaska.game.States.BumState;


/**
 * Created by Администратор on 05.04.2017.
 */

public class BumGrapicComponent implements GraphicComponent {

    Texture texture;
    Texture bum;
    Cockroach temp;

    public BumGrapicComponent(Texture texture,Texture bum){
        this.texture=texture;
        this.bum=bum;
    }


    @Override
    public void draw(float x, float y, int width, int height, SpriteBatch sprite) {
        if(temp.stateMachine.getCurrentState()== BumState.Live){
            sprite.draw(texture,x,y,width,height);
        }else if(temp.stateMachine.getCurrentState()==BumState.Crashed){
            sprite.draw(bum,0,0, GameConst.X,GameConst.Y-200);
        }
    }

    @Override
    public void setCocroach(Cockroach temp) {
        this.temp=temp;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
