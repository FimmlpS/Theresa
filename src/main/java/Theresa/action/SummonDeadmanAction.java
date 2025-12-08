package Theresa.action;

import Theresa.monster.Deadman;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SummonDeadmanAction extends AbstractGameAction {
    public SummonDeadmanAction(int index,float lastTime, int realIndex, int strength) {
        this.index = index;
        this.duration = startDuration = lastTime;
        this.realIndex = realIndex;
        this.strength = strength;
    }

    int index;
    int realIndex;
    int strength;

    @Override
    public void update() {
        if(duration == startDuration) {
            Deadman deadman = new Deadman(index,false).setRealIndex(realIndex).setStrength(strength);
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onSpawnMonster(deadman);
            }
            deadman.animX = 1500F;
            deadman.state.setAnimation(0,Deadman.MOVE,true);
            deadman.init();
            deadman.applyPowers();
            int position = 0;

            for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (deadman.drawX > mo.drawX) {
                    ++position;
                }
            }
            AbstractDungeon.getCurrRoom().monsters.addMonster(position,deadman);
            deadman.useUniversalPreBattleAction();
            deadman.usePreBattleAction();
            deadman.rollMove();
        }

        this.tickDuration();
    }
}
