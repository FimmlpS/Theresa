package Theresa.relic;

import Theresa.helper.StringHelper;
import Theresa.interfaces.LockedIt;
import Theresa.power.buff.HatePower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class BaMissUsWord extends CustomRelic {
    public static final String ID = "theresa:BaMissUsWord";


    public BaMissUsWord() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if(targetCard.type == AbstractCard.CardType.ATTACK){
            this.flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HatePower(AbstractDungeon.player,1),1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}





