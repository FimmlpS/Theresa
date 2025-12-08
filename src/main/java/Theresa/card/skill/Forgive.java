package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.power.buff.HopePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Forgive extends AbstractTheresaCard {
    public static final String ID = "theresa:Forgive";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Forgive() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
        selfRetain = true;
        shouldLocked = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for(int i = 0; i < this.baseMagicNumber; i++) {
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HopePower(abstractPlayer,1),1));
        }
    }

    @Override
    public void tookDamage() {
        this.baseMagicNumber++;
        this.magicNumber = this.baseMagicNumber;
        applyPowers();
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}





