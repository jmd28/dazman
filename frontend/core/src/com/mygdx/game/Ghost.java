package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import sun.jvm.hotspot.debugger.win32.coff.TestDebugInfo;

import java.util.Stack;

public class Ghost extends GameObject{
    private final int SCALE = 100;
    private final int VELOCITY = 5;
    private Stack<GameObject> lives;
    private Direction dir; //direction of movement
    private boolean isChaser;
    private boolean isWASD;
    private int offsetLives;
//    private Texture texture;

    public Ghost(Sprite sprite, float x, float y, boolean isGhost) {
        super(sprite, x, y);
        if(isGhost) {
            this.offsetLives = 300;
        }
        else {
            this.offsetLives = 0;
        }
        this.isWASD = isGhost;
        lives = new Stack<GameObject>();
        this.dir = Direction.RIGHT;
        this.isChaser = isGhost;
        setSize(SCALE,SCALE);
//        this.texture = new Texture("jon2.jpg");
        restoreHealth();
    }

    public void restoreHealth() {
        for (int i = 0; i < 5; i++) {
            GameObject life = new GameObject(new Sprite(new Texture("tom.jpeg")), 10 + i*35 + offsetLives, 0);
            life.setSize(25, 25);
            lives.add(life);
        }
    }

    public void setChaser(boolean isChaser) {
        this.isChaser = isChaser;
    }

    public void takeLife() {
        lives.pop();
    }

    public boolean isAlive() {
        return !lives.isEmpty();
    }

    public boolean getIsChaser() {
        return isChaser;
    }

    public Stack<GameObject> getLives() {
        return lives;
    }

    public void move(){
        setDirection(dir);
    }

    public void handleEvents() {

        if(isWASD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) dir = Direction.UP;
            else if (Gdx.input.isKeyJustPressed(Input.Keys.S))
                dir = Direction.DOWN;
            else if (Gdx.input.isKeyJustPressed(Input.Keys.A))
                dir = Direction.LEFT;
            else if (Gdx.input.isKeyJustPressed(Input.Keys.D))
                dir = Direction.RIGHT;
        }
        else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) dir = Direction.UP;
            else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
                dir = Direction.DOWN;
            else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
                dir = Direction.LEFT;
            else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                dir = Direction.RIGHT;
        }
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
