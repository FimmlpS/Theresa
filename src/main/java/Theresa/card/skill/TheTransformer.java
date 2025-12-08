package Theresa.card.skill;

import Theresa.action.ChooseDustToPileAction;
import Theresa.action.SpecialAnimationAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.AttackHelper;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheTransformer extends AbstractTheresaCard {
    public static final String ID = "theresa:TheTransformer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheTransformer() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        baseMagicNumber = magicNumber = 2;
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SpecialAnimationAction(AttackHelper.ForCharacter.Transformer));
        addToBot(new ChooseDustToPileAction(CardGroup.CardGroupType.HAND,1).setCopy(magicNumber,true,true));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}






