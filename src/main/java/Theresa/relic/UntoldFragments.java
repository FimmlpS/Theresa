package Theresa.relic;

import Theresa.action.TriggerDustSilkAction;
import Theresa.helper.StringHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class UntoldFragments extends CustomRelic {
    public static final String ID = "theresa:UntoldFragments";

    public UntoldFragments() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void onPlayerEndTurn() {
        int energy = Math.min(AbstractDungeon.player.energy.energy, EnergyPanel.getCurrentEnergy());
        if(energy > 0) {
            this.flash();
        }
        for(int i = 0;i<energy;i++) {
            addToBot(new TriggerDustSilkAction());
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}



