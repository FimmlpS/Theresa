package Theresa.power.buff;

import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.apache.commons.lang3.StringUtils;

public class HeroAndKingPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:HeroAndKingPower";

    public HeroAndKingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
        setAmountDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        boolean flash = false;
        if(StringUtils.containsIgnoreCase(card.rawDescription,powerStrings.DESCRIPTIONS[2])){
            flash = true;
            addToBot(new ApplyPowerAction(owner,owner,new HopePower(owner,amount),amount));
        }
        if(StringUtils.containsIgnoreCase(card.rawDescription,powerStrings.DESCRIPTIONS[3])){
            flash = true;
            addToBot(new ApplyPowerAction(owner,owner,new HatePower(owner,amount),amount));
        }
        if(flash){
            this.flash();
        }
    }
}
