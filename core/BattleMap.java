package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.Random;

public class BattleMap {
    private GameController gc;
    private TextureRegion grassTexture;
    private TextureRegion crystalTexture;
    private ArrayList<Coordinates> list; // свободные координаты (тайлы), в которые можно поставить (нарисовать) ресурс

    private boolean[][] data;

    public BattleMap(GameController gc) {
        this.gc = gc;
        this.grassTexture = Assets.getInstance().getAtlas().findRegion("grass");
        this.crystalTexture = Assets.getInstance().getAtlas().findRegion("crystal");
        data = new boolean[16][9];
        list = new ArrayList<>(16*9);
        initializeList();
    }

    private void initializeList() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                list.add(new Coordinates(i, j));
            }
        }
        int x = (int)gc.getTank().getPosition().x / 80;
        int y = (int)gc.getTank().getPosition().y / 80;
        Coordinates temp = new Coordinates(x, y);
        list.remove(temp); // в том месте, где стоит танк не может появиться ресурс
        Random random = new Random();
        int crystalCount = random.nextInt(16*9/4) + 16*9/4; // от 25 до 50% всех клеток на поле будут ресурсами
        for (int i = 0; i < crystalCount; i++) {
            int indexInList = random.nextInt(list.size() - 1);
            temp = list.get(indexInList);
            data[temp.x][temp.y] = true;
            list.remove(temp);
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                batch.draw(grassTexture, i * 80, j * 80);
                if (data[i][j]) {
                    batch.draw(crystalTexture,i * 80, j* 80);
                }
            }
        }
    }

    public void pickResource(int i, int j) {
        if (data[i][j]) {
            data[i][j] = false;
        }
    }
}
