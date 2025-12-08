package Theresa.card.skill;

import Theresa.action.DustAction;
import Theresa.action.DustToPileAction;
import Theresa.action.SetNewCostAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FallFromMemory extends AbstractTheresaCard {
    public static final String ID = "theresa:FallFromMemory";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public FallFromMemory() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DustAction(1));
        addToBot(new SetNewCostAction(this, 1));
    }

    @Override
    public void triggerWhenLingered() {
        addToBot(new DustToPileAction(this, CardGroup.CardGroupType.HAND));
        addToBot(new SetNewCostAction(this,-magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}




