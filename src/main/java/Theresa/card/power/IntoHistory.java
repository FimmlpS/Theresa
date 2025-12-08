package Theresa.card.power;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.IntoHistoryPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IntoHistory extends AbstractTheresaCard {
    public static final String ID = "theresa:IntoHistory";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public IntoHistory() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        //baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new IntoHistoryPower(abstractPlayer,1)));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}






