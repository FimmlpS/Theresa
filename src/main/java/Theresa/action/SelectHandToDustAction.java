package Theresa.action;

import Theresa.helper.StringHelper;
import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class SelectHandToDustAction extends AbstractGameAction {
    ArrayList<AbstractCard> cardsRemoved = new ArrayList<>();

    public SelectHandToDustAction(int amount) {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    private void returnCards(){
        for(AbstractCard c : cardsRemoved){
            AbstractDungeon.player.hand.addToHand(c);
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.applyPowers();
    }

    @Override
    public void update() {
        if(duration == startDuration) {
            for(AbstractCard c : AbstractDungeon.player.hand.group) {
                if(c.hasTag(OtherEnum.Theresa_Darkness) || (c.type!= AbstractCard.CardType.ATTACK && c.type!=AbstractCard.CardType.SKILL)) {
                    cardsRemoved.add(c);
                }
            }
            for(AbstractCard c : cardsRemoved) {
                AbstractDungeon.player.hand.group.remove(c);
            }
            if(this.amount > AbstractDungeon.player.hand.group.size()) {
                this.amount = AbstractDungeon.player.hand.group.size();
            }
            if(this.amount == 0){
                returnCards();
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(StringHelper.OPERATION.TEXT[6],this.amount,false,false);
        }
        else if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                //2025/12/22 新增微尘绝对上限15
                if(DustPatch.dustManager.dustCards.size()>=DustPatch.ABSOLUTELY_TOP_LIMIT)
                    return;
                DustPatch.dustManager.addCard(c);
            }
            returnCards();
        }
        this.tickDuration();
    }
}
