package Theresa.card.skill;

import Theresa.action.FutureStopAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.card.status.Stop;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FutureStop extends AbstractTheresaCard {
    public static final String ID = "theresa:FutureStop";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public FutureStop() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        exhaust = true;
        this.cardsToPreview = new Stop();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new FutureStopAction(new Stop()));
        if(!isInAutoplay)
            addToBot(new PressEndTurnButtonAction());
    }


    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}





