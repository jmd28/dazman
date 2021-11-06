package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Stack;

public class Ghost extends GameObject{
    private final int SCALE = 100;
    private final int VELOCITY = 5;
    private Direction dir; //direction of movement
//    private Texture texture;

    public Ghost(Sprite sprite, float x, float y) {
        super(sprite, x, y);
        this.dir = Direction.RIGHT;
        setSize(SCALE,SCALE);
//        this.texture = new Texture("jon2.jpg");
        init();
    }

    private void init(){

    }

    public void move(){
        setDirection(dir);
    }

    public void handleEvents() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) dir = Direction.UP;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            dir = Direction.DOWN;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            dir = Direction.LEFT;
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            dir = Direction.RIGHT;
    }


    public void setDirection(Direction dir) {
        if (dir == Direction.UP) {
//            sprite.setRotation(90);
            setPosition(x, y + VELOCITY);
        } else if (dir == Direction.DOWN) {
//            sprite.setRotation(-90);
            setPosition(x, y - VELOCITY);
        } else if (dir == Direction.LEFT) {
//            sprite.setRotation(180);
            setPosition(x - VELOCITY, y);
        } else if (dir == Direction.RIGHT) {
//            sprite.setRotation(0);
            setPosition(x + VELOCITY, y);
        }
    }

}
