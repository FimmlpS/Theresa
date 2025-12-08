package Theresa.action;

import Theresa.power.debuff.DyingBurstPower;
import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class CheckDyingPowerAction extends AbstractGameAction {
    public CheckDyingPowerAction(AbstractCreature source,AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.source = source;
    }

    @Override
    public void update() {
        AbstractPower dying = this.target.getPower(DyingPower.POWER_ID);
        if(dying instanceof DyingPower) {
            if(((DyingPower) dying).reachMax()){
                //addToTop(new ApplyPowerAction(target,source,new VulnerablePower(target,1,!source.isPlayer),1));
                addToTop(new ApplyPowerAction(target,source,new DyingBurstPower(target,amount),amount));
            }
        }

        this.isDone = true;
    }
}
