package Theresa.power.debuff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DeadDustPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:DeadDustPower";
    public DeadDustPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setDebuff();
        setAmountDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL && damageAmount>0) {
            int amt = damageAmount * amount;
            addToTop(new ApplyPowerAction(owner, owner,new DyingPower(owner,amt),amt));
            return 0;
        }
        return super.onAttacked(info, damageAmount);
    }
}
