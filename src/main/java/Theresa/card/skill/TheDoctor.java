package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheDoctor extends AbstractTheresaCard {
    public static final String ID = "theresa:TheDoctor";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static String getName(String before,String after){
        if(CardCrawlGame.playerName!=null)
            return before+CardCrawlGame.playerName;
        return after;
    }

    public TheDoctor() {
        super(ID,getName(cardStrings.EXTENDED_DESCRIPTION[0],cardStrings.NAME),1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        this.isInnate = true;
        shouldLocked = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(2));
    }

    @Override
    public void triggerWhenBecomeDust() {
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public void triggerWhenNoLongerDust() {
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






