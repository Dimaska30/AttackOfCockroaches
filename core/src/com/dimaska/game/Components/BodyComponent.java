package com.dimaska.game.Components;

import com.badlogic.gdx.math.Rectangle;

import javax.swing.plaf.basic.BasicDirectoryModel;

/**
 * Created by dimaska on 06.03.17.
 */

public class BodyComponent {
    float paddingTop,paddingBottom,paddingLeft,paddingRight;

    public BodyComponent(float pB, float pT, float pL, float pR){
        paddingBottom=pB;
        paddingTop=pT;
        paddingLeft=pL;
        paddingRight=pR;
    }

    public Rectangle getBody(float x, float y, int width, int height){
         return new Rectangle(x+paddingLeft*width,
                        y+paddingTop*height,
                        width*(1-paddingRight),
                        height*(1-paddingBottom));
    }
}
