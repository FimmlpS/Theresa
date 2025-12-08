package Theresa.card.power;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.DyingRosePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DyingRose extends AbstractTheresaCard {
    public static final String ID = "theresa:DyingRose";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DyingRose() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.RARE,CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DyingRosePower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}







