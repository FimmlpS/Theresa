package Theresa.card.power;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.HeroAndKingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HeroAndKing extends AbstractTheresaCard {
    public static final String ID = "theresa:HeroAndKing";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HeroAndKing() {
        super(ID,cardStrings.NAME,3,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.RARE,CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HeroAndKingPower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}







