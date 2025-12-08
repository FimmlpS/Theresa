package Theresa.relic;

import Theresa.helper.StringHelper;
import Theresa.stances.Disaster;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class LiteratureBegins extends CustomRelic {
    public static final String ID = "theresa:LiteratureBegins";

    public LiteratureBegins() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public void atBattleStartPreDraw() {
        addToBot(new ChangeStanceAction(new Disaster()));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}



