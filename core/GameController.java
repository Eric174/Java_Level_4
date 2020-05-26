package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameController {
    private BattleMap map;
    private ProjectilesController projectilesController;
    private Tank tank;

    public Tank getTank() {
        return tank;
    }

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    public BattleMap getMap() {
        return map;
    }

    // Инициализация игровой логики
    public GameController() {
        Assets.getInstance().loadAssets();
        this.tank = new Tank(this, 200, 200);
        this.map = new BattleMap(this);
        this.projectilesController = new ProjectilesController(this);
    }

    public void update(float dt) {
        tank.update(dt);
        projectilesController.update(dt);
        checkCollisions(dt);
    }

    public void checkCollisions(float dt) {
        // здесь кода по "сбору ресурсов" не так много получилось
        // проверяем - если центр танка заехал в тайл и в нем есть ресурс, то "очищаем" матрицу ресурсов для данных координат
        int i = (int)tank.getPosition().x / 80;
        int j = (int)tank.getPosition().y / 80;
        map.pickResource(i, j);
    }
}
