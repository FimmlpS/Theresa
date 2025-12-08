package Theresa.card.skill;

import Theresa.action.DelayActionAction;
import Theresa.action.SpecialAnimationAction;
import Theresa.action.TriggerDustSilkAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.helper.AttackHelper;
import Theresa.patch.SilkPatch;
import Theresa.power.buff.SilkPower;
import Theresa.silk.NormalSilk;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheStrategist extends AbstractTheresaCard {
    public static final String ID = "theresa:TheStrategist";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public TheStrategist() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SpecialAnimationAction(AttackHelper.ForCharacter.Strategist));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new SilkPower(abstractPlayer,magicNumber),magicNumber));
        addToBot(new TriggerDustSilkAction());
        addToBot(new DelayActionAction(new ReducePowerAction(abstractPlayer,abstractPlayer,SilkPower.POWER_ID,magicNumber)));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            SilkPatch.setSilkWithoutTrigger(this,new NormalSilk());
        }
    }
}






