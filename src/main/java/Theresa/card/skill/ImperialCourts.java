package Theresa.card.skill;

import Theresa.action.CourtsAction;
import Theresa.action.LostHPAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ImperialCourts extends AbstractTheresaCard {
    public static final String ID = "theresa:ImperialCourts";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ImperialCourts() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(!isInAutoplay)
            addToBot(new LostHPAction(abstractPlayer,abstractPlayer,3));
        //addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new FreeAttackPower(abstractPlayer,magicNumber),magicNumber));
        addToBot(new CourtsAction(upgraded));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}







