package Theresa.action;

import Theresa.patch.DustPatch;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DustToPileAction extends AbstractGameAction {
    public DustToPileAction(AbstractCard card, CardGroup.CardGroupType type) {
        startDuration = duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
        this.gType = type;
    }

    public DustToPileAction setRandom(){
        this.random = true;
        return this;
    }

    public DustToPileAction setPrev(){
        this.prev = true;
        return this;
    }

    public DustToPileAction idealReflection(){
        this.idealReflection = true;
        return this;
    }


    boolean idealReflection = false;
    boolean prev = false;
    boolean random = false;
    AbstractCard card;
    CardGroup.CardGroupType gType;

    @Override
    public void update() {
        if (duration == startDuration) {
            if(prev && !DustPatch.dustManager.dustCards.isEmpty()){
                this.card = DustPatch.dustManager.dustCards.get(0);
            }
            else if(random && !DustPatch.dustManager.dustCards.isEmpty()){
                this.card = DustPatch.dustManager.dustCards.get(AbstractDungeon.cardRandomRng.random(DustPatch.dustManager.dustCards.size() - 1));
            }
            if(!DustPatch.dustManager.containsCard(card)){
                this.isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmp.addToTop(card);
            if(gType == CardGroup.CardGroupType.DRAW_PILE){
                DustPatch.dustManager.removeCard(card);
                tmp.moveToDeck(card,true);
            }
            else if(gType == CardGroup.CardGroupType.HAND){
                if(AbstractDungeon.player.hand.size()<10){
                    DustPatch.dustManager.removeCard(card);
                    tmp.moveToHand(card);
                }
                else {
                    AbstractDungeon.player.createHandIsFullDialog();
                    DustPatch.dustManager.removeCard(card);
                    tmp.moveToDiscardPile(card);
                }
            }
            else if(gType == CardGroup.CardGroupType.DISCARD_PILE){
                DustPatch.dustManager.removeCard(card);
                tmp.moveToDiscardPile(card);
            }
            else if(gType == CardGroup.CardGroupType.EXHAUST_PILE){
                DustPatch.dustManager.removeCard(card);
                tmp.moveToExhaustPile(card);
            }
            if(idealReflection){
                AbstractCard copy = card.makeStatEquivalentCopy();
                CardModifierManager.addModifier(copy,new ExhaustMod());
                addToBot(new MakeTempCardInDustAction(copy,1,true));
            }
        }
        this.tickDuration();
    }
}
