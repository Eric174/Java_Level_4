package com.dune.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Circle {
    private Vector2 pos;
    private Texture texture;

    public Circle() {
        changePos();
        this.texture = new Texture("circle.png"); // размер текстуры 100 * 100
    }

    public void changePos() {
        float x = (float)(Math.random() * 1180); //чтобы влезало по оси x
        float y = (float)(Math.random() * 620); // по оси y
        pos =  new Vector2(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPosition() {
        return pos;
    }
}
