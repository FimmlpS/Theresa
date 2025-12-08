package Theresa.action;

import Theresa.patch.DustPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class AnswerAction extends AbstractGameAction {
    public AnswerAction(AbstractCreature target,boolean upgraded) {
        this.target = target;
        this.startDuration = duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DISCARD;
        this.upgraded = upgraded;
    }

    boolean upgraded;

    @Override
    public void update() {
        if(startDuration==duration){
            ArrayList<AbstractCard> dusts = new ArrayList<>(DustPatch.dustManager.dustCards);
            ArrayList<AbstractCard> attacks = new ArrayList<>();
            int drawMany = 0;
            for(AbstractCard card : dusts){
                if(upgraded||card.type == AbstractCard.CardType.ATTACK){
                    DustPatch.dustManager.removeCard(card);
                    //CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    //tmp.addToTop(card);
                    //tmp.moveToDiscardPile(card);
                    attacks.add(card);
                    drawMany++;
                }
            }
            for(AbstractCard card : attacks){
                //AbstractCard copy = card.makeSameInstanceOf();
                addToBot(new PlayCardAction(card,target,false));
            }
            if(upgraded && drawMany>0){
                addToBot(new DrawCardAction(drawMany));
            }
        }
        this.tickDuration();
    }
}


