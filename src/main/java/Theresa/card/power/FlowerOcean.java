package Theresa.card.power;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.FlowerOceanPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlowerOcean extends AbstractTheresaCard {
    public static final String ID = "theresa:FlowerOcean";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public FlowerOcean() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.COMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new FlowerOceanPower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            selfRetain = true;
        }
    }
}





