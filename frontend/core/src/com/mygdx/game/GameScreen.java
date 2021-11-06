package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    private boolean gameOver;
    private float timeState;
    OrthographicCamera camera;
    char[][] mapModel;
    Texture wallImage;
    int cellW;
    int cellH;
//    Array<Rectangle> map;

    public GameScreen(final Game game) {
        this.game = game;
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        mapModel = new Map().generate(5,9);
        cellW = (int) camera.viewportWidth / mapModel[0].length;
        cellH = (int) camera.viewportHeight / mapModel.length;

        Sprite ghostSprite = new Sprite(new Texture("jon2.jpg"));
        ghost1 = new Ghost(ghostSprite, 100,100);
        Pixmap pixmap2 = new Pixmap(Gdx.files.internal("Map Sprites/csBlueDark.png"));
        Pixmap pixmap1 = new Pixmap(cellW, cellH, pixmap2.getFormat());
        pixmap1.drawPixmap(pixmap2,
                0, 0, pixmap2.getWidth(), pixmap2.getHeight(),
                0, 0, pixmap1.getWidth(), pixmap1.getHeight()
        );

        wallImage = new Texture(pixmap1);
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
        drawMap(game.batch);
//        ghost1.draw(game.batch);
        game.batch.draw(ghost1.sprite, ghost1.x, ghost1.y, cellW, cellH);
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
        if(timeState >= 0.02){
            //move ghost
//            ghost1.y -= 200 * Gdx.graphics.getDeltaTime();
            ghost1.move();
            timeState = 0;
        }
    }










    private void drawMap(Batch batch) {

//        mapGrid = new Array<>();
        Array<Rectangle> walls = new Array<>();
        float squareWidth = camera.viewportWidth / mapModel[0].length;
        float squareHeight = camera.viewportHeight / mapModel.length;

        for (int i = 0; i< mapModel.length; i++) {
            for (int j = 0; j<mapModel[0].length; j++) {
                if  (mapModel[i][j]==Tetris.WALL) {
                    Rectangle wall = new Rectangle();
                    wall.setWidth(squareWidth);
                    wall.setHeight(squareHeight);
                    batch.draw(wallImage,j*squareWidth, i*squareHeight);
                }
            }
        }

    }
}