package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ResetIdealAction extends AbstractGameAction {
    public ResetIdealAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if(card!= null){
            card.baseMagicNumber = card.magicNumber = 1;
            card.isMagicNumberModified = false;
        }

        this.isDone = true;
    }

    AbstractCard card;
}
