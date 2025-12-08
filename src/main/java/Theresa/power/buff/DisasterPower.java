package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DisasterPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:DisasterPower";
    public DisasterPower(AbstractCreature owner) {
        super(POWER_ID, owner);
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        return (int)(0.8F * damageAmount);
    }
}
