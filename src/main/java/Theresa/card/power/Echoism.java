package Theresa.card.power;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.EchoismPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Echoism extends AbstractTheresaCard {
    public static final String ID = "theresa:Echoism";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Echoism() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new EchoismPower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(3);
            upgradeMagicNumber(1);
        }
    }
}




