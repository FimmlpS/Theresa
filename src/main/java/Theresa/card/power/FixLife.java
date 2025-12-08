package Theresa.card.power;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.FixLifePower;
import Theresa.power.buff.YoreLingerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class FixLife extends AbstractTheresaCard {
    public static final String ID = "theresa:FixLife";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public FixLife() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new WeakPower(abstractPlayer,magicNumber,false),magicNumber));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new FrailPower(abstractPlayer,magicNumber,false),magicNumber));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new FixLifePower(abstractPlayer)));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}





