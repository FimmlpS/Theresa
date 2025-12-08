package Theresa.card.skill;

import Theresa.action.DustToPileAction;
import Theresa.action.MakeTempCardInDustAction;
import Theresa.action.SetNewCostAction;
import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InsideCrystal extends AbstractTheresaCard {
    public static final String ID = "theresa:InsideCrystal";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public InsideCrystal() {
        super(ID,cardStrings.NAME,4,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        this.cardsToPreview = new Originium();
        shouldLocked = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new MakeTempCardInDustAction(new Originium(),1).setOverMake());
    }

    @Override
    public void triggerWhenBecomeDust() {
        addToBot(new DustToPileAction(this, CardGroup.CardGroupType.HAND));
        addToBot(new SetNewCostAction(this,-1));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(3);
        }
    }
}




