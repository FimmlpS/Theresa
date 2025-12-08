package Theresa.power.buff;

import Theresa.action.CheckDyingPowerAction;
import Theresa.power.AbstractTheresaPower;
import Theresa.power.debuff.DyingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class DyingRosePower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:DyingRosePower";
    private int checkAmount = 0;

    public DyingRosePower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        //setAmountDescription();
        checkAmount = 1;
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        checkAmount++;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + checkAmount + powerStrings.DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo,this.owner,new DyingPower(mo, this.amount),this.amount));
                addToBot(new ApplyPowerAction(mo,this.owner,new VulnerablePower(mo,1,false),1));
                addToBot(new CheckDyingPowerAction(this.owner, mo,this.checkAmount));
            }
        }
    }
}
