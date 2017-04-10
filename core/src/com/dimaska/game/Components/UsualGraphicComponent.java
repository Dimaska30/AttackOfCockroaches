package com.dimaska.game.Components;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dimaska.game.Cockroach;
import com.dimaska.game.States.NormallState;
import com.dimaska.game.States.OldState;

/**
 * Created by Администратор on 08.03.2017.
 */

public class UsualGraphicComponent implements GraphicComponent {

    private Texture live;
    private Texture crashed;
    private Cockroach temp;

    public UsualGraphicComponent(Texture live, Texture crashed) {
        this.live=live;
        this.crashed=crashed;
    }


    @Override
    public void draw(float x, float y, int width, int height, SpriteBatch sprite) {
        if(temp.stateMachine.getCurrentState() == NormallState.Live){
            sprite.draw(live,x,y,width,height);
        }else if(temp.stateMachine.getCurrentState() == NormallState.Crashed){
            sprite.draw(crashed,x,y,width,height);
        }
    }

    @Override
    public void setCocroach(Cockroach temp) {
        this.temp=temp;
    }


    @Override
    public void dispose(){
        live.dispose();
        crashed.dispose();
    }
}
