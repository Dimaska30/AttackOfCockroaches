package com.dimaska.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dimaska.game.Screens.MainMenuScreen;
import com.dimaska.game.Screens.PlayScreen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyGame extends Game {

	public Skin skin;
    public Preferences user;

	@Override
	public void create() {
		skin=new Skin(Gdx.files.internal("Skin/skin/glassy-ui.json"),new TextureAtlas(Gdx.files.internal("Skin/skin/glassy-ui.atlas")));
        user=Gdx.app.getPreferences("User");
        boolean isFirst=user.getBoolean("isFirst",true);
        if(isFirst){
            ifFirst();
        }
        user.flush();
        setScreen(new MainMenuScreen(this));
	}

	private void ifFirst(){
        for(int i=0;i<10;i++){
            user.putInteger("Score"+i,0);
        }
        user.putBoolean("isFirst",false);
        user.putInteger("lastLvl", 1);
    }
}
