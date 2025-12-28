package Theresa.action;

import Theresa.helper.StringHelper;
import Theresa.power.buff.DiscordPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MindOceanAction extends AbstractGameAction {
    public MindOceanAction(DiscordPower power, int amt) {
        this.power = power;
        this.amount = amt;
        startDuration = duration = Settings.ACTION_DUR_FAST;
    }

    DiscordPower power;
    private boolean selected = false;

    private void playCard(AbstractCard card) {
        AbstractDungeon.player.drawPile.group.remove(card);
        AbstractDungeon.getCurrRoom().souls.remove(card);
        AbstractDungeon.player.limbo.group.add(card);
        card.current_y = -200.0F * Settings.scale;
        card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = (float)Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        this.addToTop(new NewQueueCardAction(card, true, false, true));
        this.addToTop(new UnlimboAction(card));
        //this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
    }

    @Override
    public void update() {
       if(startDuration == duration){
           ArrayList<AbstractCard> tmp = new ArrayList<>();
           for(AbstractCard c : AbstractDungeon.player.drawPile.group){
               if(c.costForTurn>=0 && c.costForTurn<amount){
                   tmp.add(c);
               }
           }

           if(!tmp.isEmpty()){
               power.flashWithoutSound();
               if(tmp.size() == 1){
                   playCard(tmp.get(0));
                   this.isDone = true;
                   return;
               }
               else {
                   CardGroup tmpG = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                   tmpG.group.addAll(tmp);
                   AbstractDungeon.gridSelectScreen.open(tmpG,1,false, StringHelper.OPERATION.TEXT[0]+StringHelper.OPERATION.TEXT[2]+1+StringHelper.OPERATION.TEXT[12]);
               }
           }
           else {
               this.isDone = true;
               return;
           }
       }
       else if(!selected){
           selected = true;
           for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
               playCard(c);
           }
           AbstractDungeon.gridSelectScreen.selectedCards.clear();
       }

       this.tickDuration();
    }
}
