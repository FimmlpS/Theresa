package Theresa.blight;

import Theresa.action.BlightAboveCreatureAction;
import Theresa.power.buff.HatredTimePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HatredTime extends AbstractBlight {
    public static final String ID = "theresa:HatredTime";
    private static final RelicStrings relicStrings;

    public HatredTime(){
        super(ID, relicStrings.NAME, relicStrings.DESCRIPTIONS[0],"maze.png",true);
        this.img = ImageMaster.loadImage("TheresaResources/img/blights/HatredTime.png");
        this.outlineImg = ImageMaster.loadImage("TheresaResources/img/blights/HatredTime_O.png");
        this.counter = -1;
    }

    @Override
    public void onCreateEnemy(AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new BlightAboveCreatureAction(m,this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,m,new HatredTimePower(m)));
    }

    static {
        relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    }
}

