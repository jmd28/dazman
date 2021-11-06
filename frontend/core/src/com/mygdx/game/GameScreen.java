package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Arrays;
import java.util.Iterator;

public class GameScreen implements Screen {
    final Game game;

     Texture dropImage;
    Texture bucketImage;
    Texture wallImage;
//    Sound dropSound;
//    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;

    char[][] mapModel;
    Array<Rectangle> mapGrid;

    public GameScreen(final Game game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("steve.jpeg"));
        bucketImage = new Texture(Gdx.files.internal("jon2.jpg"));


        // load the drop sound effect and the rain background "music"
//        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
//        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
//        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 226;
        bucket.height = 300;

        Map map = new Map();
        mapModel = map.generate(5,9);
        int cellW = (int) camera.viewportWidth / mapModel[0].length;
        int cellH = (int) camera.viewportHeight / mapModel.length;

        Pixmap pixmap2 = new Pixmap(Gdx.files.internal("Map Sprites/csBlueDark.png"));
        Pixmap pixmap1 = new Pixmap(cellW, cellH, pixmap2.getFormat());
        pixmap1.drawPixmap(pixmap2,
                0, 0, pixmap2.getWidth(), pixmap2.getHeight(),
                0, 0, pixmap1.getWidth(), pixmap1.getHeight()
        );

        wallImage = new Texture(pixmap1);

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<>();
        spawnRaindrop();

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

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 313;
        raindrop.height = 300;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
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

//        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        drawMap(game.batch);
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
//                dropSound.play();
                iter.remove();
            }
        }
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
        dropImage.dispose();
        bucketImage.dispose();
//        dropSound.dispose();
//        rainMusic.dispose();
    }

}