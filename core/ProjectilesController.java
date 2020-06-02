package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ProjectilesController extends ObjectPool<Projectile> {
    private GameController gc;
    private TextureRegion projectileTexture;

    @Override
    protected Projectile newObject() {
        return new Projectile(gc);
    }

    public ProjectilesController(GameController gc) {
        this.gc = gc;
        this.projectileTexture = Assets.getInstance().getAtlas().findRegion("bullet");
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void setup(Vector2 srcPosition, float angle, Tank tank, int damage) {
        Projectile p = activateObject();
        p.setup(srcPosition, angle, projectileTexture, tank, damage);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            Projectile projectile = activeList.get(i);
            projectile.update(dt);
            for (int j = 0; j < gc.getTanksController().activeSize(); j++) {
                Tank tank = gc.getTanksController().activeList.get(j);
                if (projectile.getOwnerTank() != tank && projectile.getPosition().dst(tank.position) < 30) {
                    tank.takeDamage(projectile.getDamage());
                    if (tank.getHp() <= 0) {
                        projectile.getOwnerTank().removeTarget();
                    }
                    projectile.deactivate();
                }
            }
        }
        checkPool();
    }
}
