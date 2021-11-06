package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class GameObject {
    protected Sprite sprite;
    protected float x;
    protected float y;

    public GameObject(Sprite sprite, float x, float y) {
        this.sprite = sprite;
        setPosition(x,y);
    }

    public void setY(float y){
        this.y = y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        sprite.setPosition(x,y);
    }

    public void changeSprite(String filename) {
        Sprite change = new Sprite(new Texture(filename));
        sprite.set(change);
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    public boolean isCollide(GameObject object) {
        return x < object.x + object.getWidth() &&
                x + getWidth() > object.x &&
                y < object.y + object.getHeight() &&
                y + getHeight() > object.y;
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }
}
