package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Game extends ApplicationAdapter {
	private OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	Texture ghost1Img;
	private Rectangle ghost1;

	private Array<Rectangle> raindrops;
	private long lastDropTime;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");

		ghost1Img = new Texture("jon2.jpg");

		ghost1 = new Rectangle();
		ghost1.x = 800 / 2 - 64 / 2;
		ghost1.y = 20;
		ghost1.width = 64;
		ghost1.height = 64;

		raindrops = new Array<Rectangle>();

		spawnRaindrop();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(ghost1Img, ghost1.x, ghost1.y);
		for(Rectangle raindrop: raindrops) {
			batch.draw(ghost1Img, raindrop.x, raindrop.y);
		}
		batch.end();

		ghost1.y -= 100 * Gdx.graphics.getDeltaTime();

		//sprite moves left and right with L R keys
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) ghost1.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ghost1.x += 200 * Gdx.graphics.getDeltaTime();

		//sprite moves up and down
		//sprite moves left and right with L R keys
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) ghost1.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) ghost1.y -= 200 * Gdx.graphics.getDeltaTime();

		//sprites stay inside screen boundaries
		if(ghost1.x < 0) ghost1.x = 0;
		if(ghost1.x > 800 - 64) ghost1.x = 800 - 64;
		if(ghost1.y < 0) ghost1.y = 0;
		if(ghost1.y > 800 - 64) ghost1.y = 800 - 64;

//		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();



	}
	
	@Override
	public void dispose () {
		batch.dispose();
		ghost1Img.dispose();
	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

}
