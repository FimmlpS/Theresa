package Theresa.card.skill;

import Theresa.action.MyCatAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.MindSilk;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheCat extends AbstractTheresaCard {
    public static final String ID = "theresa:TheCat";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheCat() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        this.isInnate = true;
        shouldLocked = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new MyCatAction());
    }

    @Override
    public void triggerWhenBecomeDust() {
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void triggerWhenNoLongerDust() {
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            SilkPatch.setSilkWithoutTrigger(this,new MindSilk());
        }
    }
}





