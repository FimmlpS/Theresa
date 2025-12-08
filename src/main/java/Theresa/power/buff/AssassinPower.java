package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class AssassinPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:AssassinPower";

    public AssassinPower(AbstractCreature owner) {
        super(POWER_ID, owner, 1);
    }

    @Override
    public void atEndOfRound() {
        this.amount = 1;
        this.fontScale = 8.0F;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(amount>0){
            if(info.type == DamageInfo.DamageType.NORMAL){
                this.amount = 0;
                this.fontScale = 8.0F;
                this.flashWithoutSound();
                addToBot(new ApplyPowerAction(owner,owner,new StrongStrikePower(owner)));
            }
            return (int)(damageAmount * 0.6F);
        }
        return super.onAttackedToChangeDamage(info, damageAmount);
    }
}
