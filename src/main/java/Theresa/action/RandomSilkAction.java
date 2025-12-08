package Theresa.action;

import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class RandomSilkAction extends AbstractGameAction {
    public RandomSilkAction(AbstractSilk silk, boolean mustReplace, boolean canReplace) {
        this(AbstractDungeon.player.hand.group, silk, mustReplace, canReplace);
    }

    public RandomSilkAction setTypeOnly(AbstractCard.CardType type) {
        this.typeOnly = true;
        this.cardType = type;
        return this;
    }

    public RandomSilkAction(ArrayList<AbstractCard> cards, AbstractSilk silk, boolean mustReplace, boolean canReplace){
        this.cards = cards;
        this.silk = silk;
        this.mustReplace = mustReplace;
        this.canReplace = canReplace;
    }

    ArrayList<AbstractCard> cards;

    @Override
    public void update() {
        //2025/10/28 改：优先无丝线牌，然后有丝线牌（替换）
        ArrayList<AbstractCard> cardsTo = new ArrayList<>();
        ArrayList<AbstractCard> replaced = new ArrayList<>();
        for(AbstractCard c : cards) {
            if(typeOnly && c.type != cardType)
                continue;
            if(!silk.canSetWhenSet(c))
                continue;
            AbstractSilk silkToReplace = SilkPatch.SilkCardField.silk.get(c);
            if(mustReplace || silkToReplace==null) {
                cardsTo.add(c);
            }
            else if(canReplace&&silk.canReplace(silkToReplace)){
                replaced.add(c);
            }
        }

        if(!cardsTo.isEmpty()){
            Collections.shuffle(cardsTo,AbstractDungeon.cardRandomRng.random);
            AbstractCard c = cardsTo.get(0);
            addToTop(new SetSilkAction(c,this.silk,this.mustReplace,this.canReplace));
        }
        else if(!replaced.isEmpty()){
            Collections.shuffle(replaced,AbstractDungeon.cardRandomRng.random);
            AbstractCard c = replaced.get(0);
            addToTop(new SetSilkAction(c,this.silk,this.mustReplace,this.canReplace));
        }

        this.isDone = true;
    }

    AbstractSilk silk;
    boolean mustReplace;
    boolean canReplace;
    boolean typeOnly = false;
    AbstractCard.CardType cardType = null;
}
