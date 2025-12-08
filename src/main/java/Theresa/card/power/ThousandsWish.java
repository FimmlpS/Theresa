package Theresa.card.power;

import Theresa.action.RandomSilkAction;
import Theresa.action.ThousandsWishAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.power.buff.ThousandsWishPower;
import Theresa.silk.WishSilk;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThousandsWish extends AbstractTheresaCard {
    public static final String ID = "theresa:ThousandsWish";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ThousandsWish() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.RARE,CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        SilkPatch.setSilkForPreview(this,new WishSilk());
        shouldLocked = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ThousandsWishAction(1));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new ThousandsWishPower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            isInnate = true;
        }
    }
}






