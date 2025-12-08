package Theresa.card.skill;

import Theresa.action.PastCallFutureAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PastCallFuture extends AbstractTheresaCard {
    public static final String ID = "theresa:PastCallFuture";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public PastCallFuture() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new PastCallFutureAction(this));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}





