package Theresa.card.status;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.GiftPower;
import Theresa.power.buff.HatePower;
import Theresa.power.buff.HopePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheGift extends AbstractTheresaCard {
    public static final String ID = "theresa:TheGift";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheGift() {
        super(ID,cardStrings.NAME,-2,cardStrings.DESCRIPTION,CardType.STATUS,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    boolean isSpecial = false;

    public void setSpecial() {
        isSpecial = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        initializeTitle();
        initializeDescription();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        if(c instanceof TheGift){
            if(isSpecial){
                ((TheGift) c).setSpecial();
            }
        }
        return c;
    }

    @Override
    public void triggerWhenDrawn() {
        this.flash();
        if(isSpecial){
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HopePower(AbstractDungeon.player,1),1));
        }
        else {
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new HatePower(AbstractDungeon.player,1),1));
        }
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new GiftPower(AbstractDungeon.player,magicNumber),magicNumber));
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}





