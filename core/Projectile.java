package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private Vector2 position;
    private Vector2 velocity;
    private boolean inMove;
    private TextureRegion texture;

    public Projectile(TextureAtlas atlas) {
        inMove = false;
        position = new Vector2();
        velocity = new Vector2();
        texture = new TextureRegion(atlas.findRegion("bullet"));
    }

    public void setup(Vector2 startPosition, float angle) {
        velocity.set(250.0f * MathUtils.cosDeg(angle), 250.0f * MathUtils.sinDeg(angle));
        position.x = 1;
        position.y = 0;
        position.rotate(angle);
        position.scl(20);
        position.add(startPosition);
    }

    public void update(float dt) {
        // position.x += velocity.x * dt;
        // position.y += velocity.y * dt;
        position.mulAdd(velocity, dt);
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setMove(boolean move) {
        inMove = move;
    }

    public boolean getMove() {
        return inMove;
    }
}
