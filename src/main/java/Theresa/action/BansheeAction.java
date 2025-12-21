package Theresa.action;

import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import Theresa.silk.TearSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class BansheeAction extends AbstractGameAction {
    public BansheeAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(AbstractDungeon.player.drawPile.group);
        cards.addAll(AbstractDungeon.player.discardPile.group);
        Collections.shuffle(cards,AbstractDungeon.cardRandomRng.random);
        while(amount>0 && !cards.isEmpty()){
            AbstractCard c = cards.remove(0);
            AbstractSilk tear = new TearSilk();
            AbstractSilk silk = SilkPatch.SilkCardField.silk.get(c);
            if(tear.canSetWhenSet(c) && (silk == null || tear.canReplace(silk))){
                if(silk!=null && tear.canReplace(silk))
                    tear.amount = silk.amount;
                SilkPatch.setSilk(c, tear);
                tear.applyPowers();
                amount--;
            }
        }
        this.isDone = true;
    }
}
