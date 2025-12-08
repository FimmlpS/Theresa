package Theresa.relic;

import Theresa.helper.StringHelper;
import Theresa.interfaces.LockedIt;
import Theresa.power.buff.EndPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class EyeSpy extends CustomRelic {
    public static final String ID = "theresa:EyeSpy";
    boolean lost = false;

    public EyeSpy() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)),RelicTier.BOSS,LandingSound.MAGICAL);
        this.counter = -1;
    }

    @Override
    public void atBattleStartPreDraw() {
        this.stopPulse();
        lost = false;
        this.flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new EndPower(AbstractDungeon.player,1),1));
    }

    @Override
    public void atTurnStart() {
        stopPulse();
        if(lost){
            lost = false;
            addToBot(new LoseEnergyAction(2));
        }
    }

    @Override
    public void wasHPLost(int damageAmount) {
        if(damageAmount>0){
            lost = true;
            this.beginLongPulse();
        }
    }

    @Override
    public void onVictory() {
        stopPulse();
        lost = false;
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


