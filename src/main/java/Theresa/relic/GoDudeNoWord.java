package Theresa.relic;

import Theresa.action.TriggerDustSilkAction;
import Theresa.helper.StringHelper;
import Theresa.interfaces.LockedIt;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class GoDudeNoWord extends CustomRelic {
    public static final String ID = "theresa:GoDudeNoWord";

    public GoDudeNoWord() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atPreBattle() {
        counter = 0;
    }

    @Override
    public void atBattleStart() {
        this.flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new PlatedArmorPower(AbstractDungeon.player,6),6));
    }

    @Override
    public void onTrigger() {
        counter++;
        while (counter>=10){
            this.counter -= 10;
            this.flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new PlatedArmorPower(AbstractDungeon.player,3),3));
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




