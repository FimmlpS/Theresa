package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class StrongStrikePower extends AbstractTheresaPower {
    public static final String  POWER_ID = "theresa:StrongStrikePower";

    public StrongStrikePower(AbstractCreature owner) {
        super(POWER_ID, owner);
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL) {
            return damage * 2F;
        }
        return super.atDamageFinalGive(damage, type);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.type == DamageInfo.DamageType.NORMAL) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }
}
