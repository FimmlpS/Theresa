package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import Theresa.power.buff.EnlightenmentPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;

public class Enlightenment extends AbstractTheresaCard {
    public static final String ID = "theresa:Enlightenment";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Enlightenment() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ScryAction(magicNumber));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new EnlightenmentPower(abstractPlayer)));
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




