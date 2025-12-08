package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DiscordPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:DiscordPower";

    public DiscordPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
        setPriority(-5);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        int decrease = Math.min(damageAmount,this.amount);
        if(decrease>0){
            this.flash();
            if (info.owner instanceof AbstractMonster) {
                info.owner.heal(decrease);
            } else if (owner.isPlayer) {
                AbstractMonster m = AbstractDungeon.getRandomMonster();
                if (m != null) {
                    m.heal(decrease);
                }
            } else {
                if (AbstractDungeon.player != null) {
                    AbstractDungeon.player.heal(decrease, true);
                }
            }
        }
        this.amount -= decrease;
        updateDescription();
        if(this.amount <= 0){
            addToTop(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
        return damageAmount-decrease;
    }
}
