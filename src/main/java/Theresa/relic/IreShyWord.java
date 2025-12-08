package Theresa.relic;

import Theresa.helper.StringHelper;
import Theresa.interfaces.LockedIt;
import Theresa.patch.SilkPatch;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class IreShyWord extends CustomRelic {
    public static final String ID = "theresa:IreShyWord";

    public IreShyWord() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        SilkPatch.triggerSilk(SilkPatch.TriggerType.ALL,targetCard, CardGroup.CardGroupType.HAND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}






