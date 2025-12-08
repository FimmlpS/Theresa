package Theresa.card.skill;

import Theresa.action.CountdownAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FinalCountdown extends AbstractTheresaCard {
    public static final String ID = "theresa:FinalCountdown";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public FinalCountdown() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.RARE,CardTarget.SELF);
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new CountdownAction());
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}





