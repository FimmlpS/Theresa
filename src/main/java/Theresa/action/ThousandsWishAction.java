package Theresa.action;

import Theresa.helper.StringHelper;
import Theresa.helper.TheresaHelper;
import Theresa.patch.SilkPatch;
import Theresa.silk.WishSilk;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ThousandsWishAction extends AbstractGameAction {
    public ThousandsWishAction(int amt) {
        this.amount = amt;
        actionType = ActionType.CARD_MANIPULATION;
        startDuration = duration = Settings.ACTION_DUR_FAST;
    }

    boolean hasSelected = false;

    @Override
    public void update() {
        if(startDuration==duration){
            ArrayList<AbstractCard> cards = new ArrayList<>(AbstractDungeon.player.drawPile.group);
            cards.removeIf(c -> !TheresaHelper.canBecomeDust(c,true) || SilkPatch.SilkCardField.silk.get(c)!=null);
            if(amount>cards.size()){
                amount=cards.size();
            }
            if(amount==0){
                this.isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmp.group = cards;
            AbstractDungeon.gridSelectScreen.open(tmp,amount,false, StringHelper.OPERATION.TEXT[0]+amount+StringHelper.OPERATION.TEXT[11]);
        }
        else if(!hasSelected){
            hasSelected = true;
            for(AbstractCard c:AbstractDungeon.gridSelectScreen.selectedCards){
                SilkPatch.setSilk(c,new WishSilk());
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.tickDuration();
    }
}
