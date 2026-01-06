package Theresa.relic;

import Theresa.action.SetSilkAction;
import Theresa.helper.StringHelper;
import Theresa.patch.SilkPatch;
import Theresa.silk.MindSilk;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;

public class IreShyWord extends CustomRelic {
    public static final String ID = "theresa:IreShyWord";

    public IreShyWord() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
        tips.add(new PowerTip(DESCRIPTIONS[1],DESCRIPTIONS[2]));
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        SilkPatch.triggerSilk(SilkPatch.TriggerType.ALL,targetCard, CardGroup.CardGroupType.HAND);
        if(SilkPatch.SilkCardField.silk.get(targetCard)==null){
            addToBot(new SetSilkAction(targetCard, new MindSilk()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}






