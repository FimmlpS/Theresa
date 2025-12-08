package Theresa.card.skill;

import Theresa.action.HandToTopAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.TheresaHelper;
import Theresa.power.buff.EndPower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EndDust extends AbstractTheresaCard {
    public static final String ID = "theresa:EndDust";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public EndDust() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        exhaust = true;
        shouldLocked = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new EndPower(abstractPlayer,1),1));
    }

    @Override
    public void triggerWhenDrawn() {
        if(!TheresaHelper.drawnAsDust(this)) {
            this.flash();
            addToBot(new DiscardSpecificCardAction(this));
            addToBot(new GainBlockAction(AbstractDungeon.player,magicNumber));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}



