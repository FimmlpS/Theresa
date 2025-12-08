package Theresa.card.skill;

import Theresa.action.SadTimeAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SadTime extends AbstractTheresaCard {
    public static final String ID = "theresa:SadTime";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SadTime() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SadTimeAction(this,magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeMagicNumber(1);
        }
    }
}





