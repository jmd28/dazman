package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

public class StartGame implements Screen {

    final Game game;
    OrthographicCamera camera;
    Texture bucketImage;
    Rectangle bucket;

    public StartGame(final Game game){
        bucket = new Rectangle();
        bucket.x = 150;
        bucket.y = 50;
        bucket.width = 500;
        bucket.height = 300;
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        bucketImage = new Texture(Gdx.files.internal("../../../sprites/main.png"));
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}