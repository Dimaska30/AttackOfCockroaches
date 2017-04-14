package com.dimaska.game.Components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dimaska.game.Cockroach;

/**
 * Created by dimaska on 06.03.17.
 */

public interface GraphicComponent {
    void draw(float x, float y, int width, int height, SpriteBatch sprite);
    void setCocroach(Cockroach temp);
    void dispose();
}
