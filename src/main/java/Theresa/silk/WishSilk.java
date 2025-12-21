package Theresa.silk;

import Theresa.action.DustAction;
import Theresa.action.DustToPileAction;
import Theresa.helper.ImageHelper;
import Theresa.helper.TheresaHelper;
import Theresa.patch.DustPatch;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.power.buff.SilkPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WishSilk extends AbstractSilk{
    public static final String ID = "theresa:WishSilk";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID);

    public WishSilk() {
        super(ID);
        this.name = powerStrings.NAME;
        this.baseAmount = 3;
        this.amount = 3;
        this.img = ImageHelper.imgE;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnEnd(CardGroup.CardGroupType type) {
        if(type == OtherEnum.Theresa_Dust && card!=null){
            AbstractDungeon.actionManager.addToBottom(new DustAction(1));
            AbstractDungeon.actionManager.addToBottom(new DustToPileAction(card, CardGroup.CardGroupType.DISCARD_PILE));
            triggeredOnce();
        }
    }

    @Override
    public boolean canSetWhenSet(AbstractCard card) {
        if(card.hasTag(OtherEnum.Theresa_Silk_Cannot_Replaced))
            return false;
        if(!TheresaHelper.canBecomeDust(card,true))
            return false;
        int amt = 0;
        int max = baseAmount;
        AbstractPower power = AbstractDungeon.player.getPower(SilkPower.POWER_ID);
        if(power!=null && power.amount>0)
            max += power.amount;
        for(AbstractCard c: AbstractDungeon.player.drawPile.group){
            if(SilkPatch.SilkCardField.silk.get(c) instanceof WishSilk)
                amt++;
            if(amt>=max)
                return false;
        }
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(SilkPatch.SilkCardField.silk.get(c) instanceof WishSilk)
                amt++;
            if(amt>=max)
                return false;
        }
        for(AbstractCard c: AbstractDungeon.player.discardPile.group){
            if(SilkPatch.SilkCardField.silk.get(c) instanceof WishSilk)
                amt++;
            if(amt>=max)
                return false;
        }
        for(AbstractCard c: DustPatch.dustManager.dustCards){
            if(SilkPatch.SilkCardField.silk.get(c) instanceof WishSilk)
                amt++;
            if(amt>=max)
                return false;
        }
        return true;
    }

    @Override
    public AbstractSilk makeCopy() {
        return new WishSilk();
    }
}
