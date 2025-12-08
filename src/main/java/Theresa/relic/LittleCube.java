package Theresa.relic;

import Theresa.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.ArrayList;

public class LittleCube extends CustomRelic {
    public static final String ID = "theresa:LittleCube";

    public LittleCube() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atPreBattle() {
        counter = 0;
    }

    @Override
    public void onTrigger() {
        counter++;
        this.flash();
        if(counter == 1){
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainBlockAction(AbstractDungeon.player,5));
        }
        else if(counter == 2){
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DrawCardAction(1));
        }
        else if(counter>=3){
            counter = 0;
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            ArrayList<AbstractCard> groupCopy = new ArrayList<>();
            for(AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                    groupCopy.add(c);
                }
            }
            for(CardQueueItem i : AbstractDungeon.actionManager.cardQueue) {
                if (i.card != null) {
                    groupCopy.remove(i.card);
                }
            }
            AbstractCard c = null;
            if (!groupCopy.isEmpty()) {
                c = (AbstractCard)groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            }
            if (c != null) {
                c.setCostForTurn(c.costForTurn-1);
            }
        }
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


