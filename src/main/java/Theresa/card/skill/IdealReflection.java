package Theresa.card.skill;

import Theresa.action.DustAction;
import Theresa.action.DustToPileAction;
import Theresa.action.IdealReflectionAction;
import Theresa.action.ResetIdealAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IdealReflection extends AbstractTheresaCard {
    public static final String ID = "theresa:IdealReflection";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public IdealReflection() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        baseBlock = block = 8;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        for(int i =0;i<magicNumber;i++)
            addToBot(new DustToPileAction(null, CardGroup.CardGroupType.DISCARD_PILE).setPrev().idealReflection());
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(1);
            upgradeMagicNumber(1);
        }
    }
}




