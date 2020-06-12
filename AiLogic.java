package com.dune.game.core.users_logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.dune.game.core.BattleMap;
import com.dune.game.core.GameController;
import com.dune.game.core.units.AbstractUnit;
import com.dune.game.core.units.BattleTank;
import com.dune.game.core.units.Harvester;
import com.dune.game.core.units.types.Owner;
import com.dune.game.core.units.types.UnitType;

import java.util.ArrayList;
import java.util.List;

public class AiLogic extends BaseLogic {
    private float timer;

    private List<BattleTank> tmpAiBattleTanks;
    private List<Harvester> tmpAiHarvesters;
    private List<Harvester> tmpPlayerHarvesters;
    private List<BattleTank> tmpPlayerBattleTanks;
    private List<Vector2> resourceList;
    private Vector2 destination;

    public AiLogic(GameController gc) {
        this.gc = gc;
        this.money = 1000;
        this.unitsCount = 10;
        this.unitsMaxCount = 100;
        this.ownerType = Owner.AI;
        this.tmpAiBattleTanks = new ArrayList<>();
        this.tmpAiHarvesters = new ArrayList<>();
        this.tmpPlayerHarvesters = new ArrayList<>();
        this.tmpPlayerBattleTanks = new ArrayList<>();
        this.resourceList = new ArrayList<>();
        this.destination = new Vector2();
        this.timer = 10000.0f;
    }

    public void update(float dt) {
        timer += dt;
        if (timer > 2.0f) {
            timer = 0.0f;
            gc.getUnitsController().collectTanks(tmpAiBattleTanks, gc.getUnitsController().getAiUnits(), UnitType.BATTLE_TANK);
            gc.getUnitsController().collectTanks(tmpAiHarvesters, gc.getUnitsController().getAiUnits(), UnitType.HARVESTER);
            gc.getUnitsController().collectTanks(tmpPlayerHarvesters, gc.getUnitsController().getPlayerUnits(), UnitType.HARVESTER);
            gc.getUnitsController().collectTanks(tmpPlayerBattleTanks, gc.getUnitsController().getPlayerUnits(), UnitType.BATTLE_TANK);
            for (int i = 0; i < tmpAiBattleTanks.size(); i++) {
                BattleTank aiBattleTank = tmpAiBattleTanks.get(i);
                aiBattleTank.commandAttack(findNearestTarget(aiBattleTank, tmpPlayerBattleTanks));
            }

            //for Harvesters work
            resourceList = gc.getMap().getResourceList();
            for (int i = 0; i < tmpAiHarvesters.size(); i++) {
                Harvester harvester = tmpAiHarvesters.get(i);
                if (harvester.getContainer() < 5) {
                    moveToNearestResource(harvester);
                } else {
                    harvester.commandMoveTo(gc.getMap().getBuildingCoordinates(this));
                }
            }
        }
    }

    public <T extends AbstractUnit> T findNearestTarget(AbstractUnit currentTank, List<T> possibleTargetList) {
        T target = null;
        float minDist = 1000000.0f;
        for (int i = 0; i < possibleTargetList.size(); i++) {
            T possibleTarget = possibleTargetList.get(i);
            float currentDst = currentTank.getPosition().dst(possibleTarget.getPosition());
            if (currentDst < minDist) {
                target = possibleTarget;
                minDist = currentDst;
            }
        }
        return target;
    }

    public void moveToNearestResource(Harvester harvester) {
        float minDist = 1000000.0f;
        destination.set(harvester.getPosition());
        for (int i = 0; i < resourceList.size(); i++) {
            Vector2 tmpV = resourceList.get(i);
            float currentDst = harvester.getPosition().dst(tmpV);
            if (currentDst < minDist) {
                destination.set(tmpV);
                minDist = currentDst;
            }
        }
        harvester.commandMoveTo(destination);
    }
}
