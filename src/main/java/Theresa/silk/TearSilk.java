package Theresa.silk;

import Theresa.action.SilkFlashAction;
import Theresa.helper.ImageHelper;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class TearSilk extends AbstractSilk{
    public static final String ID = "theresa:TearSilk";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);


    public TearSilk() {
        super(ID);
        this.name = powerStrings.NAME;
        this.baseAmount = 2;
        this.amount = 2;
        this.img = ImageHelper.imgB;
        this.updateDescription();
    }

    @Override
    public void atTurnEnd(CardGroup.CardGroupType type) {
        if (type == CardGroup.CardGroupType.HAND || type == OtherEnum.Theresa_Dust) {
            AbstractDungeon.actionManager.addToBottom(new SilkFlashAction(this));
            triggeredOnce();
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1] + this.amount + powerStrings.DESCRIPTIONS[2];
    }

    @Override
    public boolean canReplace(AbstractSilk silkToReplace) {
        if(silkToReplace.silkID == NormalSilk.ID || silkToReplace.silkID == MindSilk.ID)
            return true;
        return super.canReplace(silkToReplace);
    }

    @Override
    public AbstractSilk makeCopy() {
        return new TearSilk();
    }
}

