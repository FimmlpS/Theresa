package Theresa.relic;

import Theresa.action.RandomSilkAction;
import Theresa.helper.StringHelper;
import Theresa.interfaces.LockedIt;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import Theresa.silk.NormalSilk;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;

public class TheEnd extends CustomRelic implements LockedIt {
    public static final String ID = "theresa:TheEnd";

    boolean triggered = false;

    public TheEnd() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
        tips.add(new PowerTip(DESCRIPTIONS[1],DESCRIPTIONS[2]));
    }

    @Override
    public void atPreBattle() {
        triggered = false;
    }

    @Override
    public void atTurnStartPostDraw() {
        if(!triggered) {
            triggered = true;
            this.flash();
            addToBot(new RandomSilkAction(new NormalSilk(),false,true));
            addToBot(new RandomSilkAction(new NormalSilk(),false,true));
            addToBot(new RandomSilkAction(new NormalSilk(),false,true));
        }
    }

    public void onTriggerSilk(AbstractCard c){
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        //SilkPatch.triggerSilk(SilkPatch.TriggerType.ALL,c, OtherEnum.Theresa_Dust);
        addToBot(new GainBlockAction(AbstractDungeon.player,2,true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

