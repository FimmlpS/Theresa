package Theresa.power.debuff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class DyingBurstPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:DyingBurstPower";

    public DyingBurstPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setDebuff();
        //setAmountDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + 5*amount + powerStrings.DESCRIPTIONS[2];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        addToBot(new LoseHPAction(owner,owner,5*amount));
        if(this.amount>1){
            addToBot(new ReducePowerAction(owner,owner,this,1));
        }
        else
            addToBot(new RemoveSpecificPowerAction(owner,owner,this));
    }

    @Override
    public void onInitialApplication() {
        AbstractPower p = owner.getPower(DyingPower.POWER_ID);
        if(p instanceof DyingPower){
            ((DyingPower) p).reCheck = true;
        }
    }

    @Override
    public void onRemove() {
        AbstractPower p = owner.getPower(DyingPower.POWER_ID);
        if(p instanceof DyingPower){
            ((DyingPower) p).reCheck = true;
        }
    }
}
