package Theresa.card.skill;

import Theresa.action.DustToPileAction;
import Theresa.action.TowardEndfieldAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TowardEndfield extends AbstractTheresaCard {
    public static final String ID = "theresa:TowardEndfield";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TowardEndfield() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
        this.baseMagicNumber = magicNumber = 1;
        tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for(int i =0;i<magicNumber;i++)
            addToBot(new DustToPileAction(null, CardGroup.CardGroupType.HAND).setPrev());
        addToBot(new TowardEndfieldAction(magicNumber));
    }


    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}




