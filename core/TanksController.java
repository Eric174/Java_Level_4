package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TanksController extends ObjectPool<Tank> {
    private GameController gc;
    private Tank selectedTank;

    @Override
    protected Tank newObject() {
        return new Tank(gc);
    }

    public TanksController(GameController gc) {
        this.gc = gc;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void setup(float x, float y, Tank.Owner ownerType) {
        Tank t = getActiveElement();
        t.setup(ownerType, x, y);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if (activeList.get(i).getPosition().dst(Gdx.input.getX(), 720 - Gdx.input.getY()) < 35 &&
                    activeList.get(i).getOwnerType() == Tank.Owner.PLAYER) {
                    if (selectedTank != null && selectedTank != activeList.get(i)) {
                        selectedTank.setSelected(false);
                    }
                    selectedTank = activeList.get(i);
                    selectedTank.setSelected(true);
                } else {
                    if (selectedTank == activeList.get(i)) {
                        selectedTank.setSelected(false);
                    }
                }
            }
        }
        checkPool();
    }
}
