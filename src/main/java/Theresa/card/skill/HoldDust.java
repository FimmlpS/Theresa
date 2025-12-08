package Theresa.card.skill;

import Theresa.action.SelectHandToDustAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HoldDust extends AbstractTheresaCard {
    public static final String ID = "theresa:HoldDust";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HoldDust() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new SelectHandToDustAction(1));
    }


    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}






