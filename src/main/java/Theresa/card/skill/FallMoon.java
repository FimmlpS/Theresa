package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.card.status.Stop;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FallMoon extends AbstractTheresaCard {
    public static final String ID = "theresa:FallMoon";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public FallMoon() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseBlock = block = 5;
        cardsToPreview = new Stop();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new MakeTempCardInHandAction(new Stop()));
        if(upgraded) {
            addToBot(new DrawCardAction(1));
        }
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




