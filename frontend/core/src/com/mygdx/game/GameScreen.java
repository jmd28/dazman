package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
    final Game game;
    private static final int WIDTH = Gdx.graphics.getWidth();
    private static final int HEIGHT = Gdx.graphics.getHeight();

    Ghost ghost1;
    Ghost ghost2;
    private boolean gameOver;
    private float timeState;
    OrthographicCamera camera;

    Array<Rectangle> map;

    public GameScreen(final Game game) {
        this.game = game;
        Sprite ghostSprite1 = new Sprite(new Texture("jon2.jpg"));
        Sprite ghostSprite2 = new Sprite(new Texture("al.jpeg"));

        ghost1 = new Ghost(ghostSprite1, 100,100, false);
        ghost2 = new Ghost(ghostSprite2, 250,250, true);
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();


        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        ghost1.draw(game.batch);
        ghost2.draw(game.batch);
        update(Gdx.graphics.getDeltaTime());
        game.batch.end();


    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
//        rainMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

//        dropSound.dispose();
//        rainMusic.dispose();
    }

    public void update(float delta){
        timeState+= delta;
        ghost1.handleEvents();
        ghost2.handleEvents();
        if(timeState >= 0.2){
            //move ghost
//            ghost1.y -= 200 * Gdx.graphics.getDeltaTime();
            ghost1.move();
            ghost2.move();
            timeState = 0;
        }
    }

}