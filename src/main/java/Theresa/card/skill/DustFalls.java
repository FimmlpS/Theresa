package Theresa.card.skill;

import Theresa.action.DustAction;
import Theresa.action.DustToPileAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.power.buff.HopePower;
import Theresa.silk.WishSilk;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DustFalls extends AbstractTheresaCard {
    public static final String ID = "theresa:DustFalls";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DustFalls() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.RARE,CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        SilkPatch.setSilkWithoutTrigger(this,new WishSilk());
        shouldLocked = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HopePower(abstractPlayer,magicNumber),magicNumber));
        if(upgraded){
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HopePower(abstractPlayer,1),1));
        }
    }

//    @Override
//    public void atTurnEndIfDust() {
//        this.flash();
//        addToBot(new DustAction(1));
//        addToBot(new DustToPileAction(this, CardGroup.CardGroupType.DISCARD_PILE));
//    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            //upgradeMagicNumber(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}


