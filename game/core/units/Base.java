package com.dune.game.core.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.Assets;
import com.dune.game.core.GameController;
import com.dune.game.core.Targetable;

public class Base {

    private Vector2 position;
    private TextureRegion texture;

    public Base(float x, float y) {
        this.texture = Assets.getInstance().getAtlas().findRegion("barracks80");
        this.position = new Vector2(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 40, position.y - 40, 40, 40, 80, 80, 1, 1, 0);
    }

    public Vector2 getPosition() {
        return position;
    }
}
