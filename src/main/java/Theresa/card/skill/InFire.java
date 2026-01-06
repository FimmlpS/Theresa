package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import Theresa.power.buff.HatePower;
import Theresa.power.buff.InFirePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InFire extends AbstractTheresaCard {
    public static final String ID = "theresa:InFire";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public InFire() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(upgraded){
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HatePower(abstractPlayer,magicNumber),magicNumber));
        }
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new InFirePower(abstractPlayer,1),1));
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




