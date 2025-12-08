package Theresa.action;

import Theresa.patch.DustPatch;
import Theresa.silk.MindSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MindOAction extends AbstractGameAction {
    public MindOAction(AbstractCard c) {
        this.c = c;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(DustPatch.dustManager.dustCards);
        cards.addAll(AbstractDungeon.player.hand.group);
        cards.addAll(AbstractDungeon.player.drawPile.group);
        cards.addAll(AbstractDungeon.player.discardPile.group);
        for (AbstractCard tmp : cards) {
            if(c.cardID == tmp.cardID){
                addToTop(new SetSilkAction(tmp,new MindSilk(),false,true));
            }
        }

        this.isDone = true;
    }

    AbstractCard c;
}
