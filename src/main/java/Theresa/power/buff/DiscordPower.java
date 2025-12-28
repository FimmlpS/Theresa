package Theresa.power.buff;

import Theresa.action.MindOceanAction;
import Theresa.power.AbstractTheresaPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DiscordPower extends AbstractTheresaPower {
    public static final String POWER_ID = "theresa:DiscordPower";

    public DiscordPower(AbstractCreature owner) {
        super(POWER_ID, owner);
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        int theCost = card.costForTurn;
        if(card.cost == -1){
            theCost = card.energyOnUse;
        }
        if(!card.isInAutoplay && theCost>0){
            addToBot(new MindOceanAction(this,theCost));
        }
    }
}
