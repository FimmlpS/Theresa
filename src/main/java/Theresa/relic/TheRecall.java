package Theresa.relic;

import Theresa.helper.StringHelper;
import Theresa.interfaces.LockedIt;
import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class TheRecall extends CustomRelic implements LockedIt {
    public static final String ID = "theresa:TheRecall";

    public TheRecall() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atTurnStartPostDraw() {
        this.flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        int hopeAmt = 0;
        int hateAmt = 0;
        if(AbstractDungeon.player.getPower(HopePower.POWER_ID)!=null)
            hopeAmt = AbstractDungeon.player.getPower(HopePower.POWER_ID).amount;
        if(AbstractDungeon.player.getPower(HatePower.POWER_ID)!=null)
            hateAmt = AbstractDungeon.player.getPower(HatePower.POWER_ID).amount;
        if(hopeAmt<=hateAmt)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HopePower(AbstractDungeon.player, 1), 1));
        if(hateAmt<=hopeAmt)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HatePower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


