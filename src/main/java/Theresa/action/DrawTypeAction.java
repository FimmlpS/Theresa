package Theresa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.Collections;

public class DrawTypeAction extends AbstractGameAction {
    public DrawTypeAction(AbstractCard.CardType type, int amount) {
        this.cardType = type;
        actionType = ActionType.DRAW;
        this.amount = amount;
    }

    AbstractCard.CardType cardType;

    @Override
    public void update() {
        if(this.amount<=0){
            this.isDone = true;
            return;
        }

        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> cardsToDraw = new ArrayList<>();
        ArrayList<AbstractCard> drawPile = new ArrayList<>(p.drawPile.group);
        Collections.reverse(drawPile);
        for(AbstractCard c : drawPile) {
            if(cardsToDraw.size() == amount)
                break;
            if(c.type == cardType) {
                cardsToDraw.add(c);
            }
        }
        if(cardsToDraw.size() < amount){
            ArrayList<AbstractCard> cardsToDrawDiscard = new ArrayList<>();
            for(AbstractCard c:p.discardPile.group){
                if(c.type == cardType) {
                    cardsToDrawDiscard.add(c);
                }
            }
            if(cardsToDrawDiscard.size()>1){
                Collections.shuffle(cardsToDrawDiscard,AbstractDungeon.shuffleRng.random);
            }
            int remains = amount-cardsToDraw.size();
            for(int i = 0; i<remains && i<cardsToDrawDiscard.size(); i++){
                cardsToDraw.add(cardsToDrawDiscard.get(i));
            }
            for(AbstractRelic r : AbstractDungeon.player.relics) {
                r.onShuffle();
            }
        }
        int size = cardsToDraw.size();
        if(size>0){
            Collections.reverse(cardsToDraw);
            for(AbstractCard c : cardsToDraw) {
                if(p.drawPile.contains(c)) {
                    p.drawPile.removeCard(c);
                }
                else if(p.discardPile.contains(c)) {
                    p.discardPile.removeCard(c);
                }
                p.drawPile.addToTop(c);
            }
            addToTop(new DrawCardAction(size));
        }

        this.isDone = true;
    }
}
