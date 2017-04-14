package com.dimaska.game;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dimaska.game.MyGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Gdx.input.setCatchBackKey(true);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass=false;
		config.useAccelerometer=false;
		config.useGyroscope=false;
		initialize(new MyGame(), config);
	}
}
