package Theresa.relic;

import Theresa.action.DelayActionAction;
import Theresa.action.RandomSilkAction;
import Theresa.helper.StringHelper;
import Theresa.patch.SilkPatch;
import Theresa.silk.MindSilk;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;

public class BraveBlade extends CustomRelic {
    public static final String ID = "theresa:BraveBlade";

    public BraveBlade() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
        tips.add(new PowerTip(DESCRIPTIONS[1],DESCRIPTIONS[2]));
    }

    @Override
    public void atTurnStartPostDraw() {
        this.flash();
        addToBot(new DelayActionAction(new RandomSilkAction(new MindSilk(),false,true).setTypeOnly(AbstractCard.CardType.ATTACK)));
    }

    @Override
    public float atDamageModify(float damage, AbstractCard c) {
        if(SilkPatch.SilkCardField.silk.get(c) != null){
            int times = 1;
            int multi = SilkPatch.SilkCardField.silkTriggerTimes.get(c);
            if(multi>1)
                times = multi;
            return damage * (1F + (float) times/3F);
        }
        return super.atDamageModify(damage, c);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


