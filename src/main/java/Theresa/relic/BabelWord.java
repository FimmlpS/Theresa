package Theresa.relic;

import Theresa.action.DustToPileAction;
import Theresa.helper.StringHelper;
import Theresa.modifier.ForgetMod;
import Theresa.patch.DustPatch;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class BabelWord extends CustomRelic {
    public static final String ID = "theresa:BabelWord";

    public BabelWord() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atTurnStart() {
        int sub = DustPatch.dustManager.dustUpLimit - DustPatch.dustManager.dustCards.size();
        if(sub>0){
            this.flash();
            addToBot(new DrawCardAction(sub));
        }
    }

    @Override
    public void onPlayerEndTurn() {
        for(AbstractCard c:DustPatch.dustManager.lingeredThisTurn){
            if(DustPatch.dustManager.containsCard(c)){
                c.flash();
                for(int i =0;i<5;i++)
                    CardModifierManager.addModifier(c,new ForgetMod());
                addToBot(new DustToPileAction(c, CardGroup.CardGroupType.DISCARD_PILE));
            }
        }
    }

    @Override
    public void obtain() {
        updateDescription(AbstractDungeon.player.chosenClass);
        if(AbstractDungeon.player.hasRelic(BabelRelic.ID)){
            for(int i =0;i<AbstractDungeon.player.relics.size();i++){
                if((AbstractDungeon.player.relics.get(i)).relicId.equals(BabelRelic.ID)){
                    this.instantObtain(AbstractDungeon.player,i,true);
                    break;
                }
            }
        }
        else{
            super.obtain();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}



