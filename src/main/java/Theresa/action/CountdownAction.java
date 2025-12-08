package Theresa.action;

import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CountdownAction extends AbstractGameAction {
    public CountdownAction() {}

    @Override
    public void update() {
        int amt = 0;
        AbstractPower hope = AbstractDungeon.player.getPower(HopePower.POWER_ID);
        if (hope != null) {
            amt = hope.amount;
        }
        for(int i = 0;i<amt;i++) {
            addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HatePower(AbstractDungeon.player,1),1));
        }
        addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,HopePower.POWER_ID));
        this.isDone = true;
    }
}
