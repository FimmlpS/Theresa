package Theresa.power.buff;

import Theresa.card.status.Decide;
import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class PromiseDiePower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:PromiseDiePower";

    boolean upgraded = false;
    boolean triggered = false;
    boolean gained = false;

    public PromiseDiePower(AbstractCreature owner, boolean upgraded){
        super(POWER_ID,owner);
        this.upgraded = upgraded;
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if(triggered)
            return;
        if(owner.currentHealth*2<owner.maxHealth){
            triggered = true;
            addToTop(new ApplyPowerAction(owner,owner,new IntangiblePlayerPower(owner,1),1));
        }
    }

    @Override
    public void atEndOfRound() {
        if(triggered && !gained){
            gained = true;
            addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(owner,10),10));
        }
    }

    @Override
    public void onInitialApplication() {
        AbstractCard de = new Decide();
        if(upgraded){
            de.upgrade();
        }
        addToBot(new MakeTempCardInDrawPileAction(de,1,true,true));
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[upgraded?1:0];
    }
}
