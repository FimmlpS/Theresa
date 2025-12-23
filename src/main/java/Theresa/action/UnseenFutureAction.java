package Theresa.action;

import Theresa.patch.DustPatch;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class UnseenFutureAction extends AbstractGameAction {
    public UnseenFutureAction(boolean upgrade) {
        this.upgrade = upgrade;
    }

    boolean upgrade;

    @Override
    public void update() {
        int amt = 0;
        for(AbstractCard c : DustPatch.dustManager.dustCards){
            if(c.rarity == AbstractCard.CardRarity.BASIC){
                if(!upgrade){
                    amt++;
                }
                CardModifierManager.addModifier(c,new ExhaustMod());
            }
            if(upgrade && c.exhaust){
                amt++;
            }
        }
        if(amt>0){
            if(amt>3){
                amt = 3;
            }
            addToTop(new DustAction(amt));
        }
        this.isDone = true;
    }
}
