package Theresa.card.skill;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import Theresa.silk.MemorySilk;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EternalDust extends AbstractTheresaCard {
    public static final String ID = "theresa:EternalDust";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public EternalDust() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.RARE,CardTarget.NONE);
        baseMagicNumber = magicNumber = 1;
        MemorySilk ms = new MemorySilk();
        ms.baseAmount = ms.amount = 2;
        SilkPatch.setSilkWithoutTrigger(this,ms);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(abstractPlayer,magicNumber));
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            AbstractSilk silk = SilkPatch.SilkCardField.silk.get(this);
            if(silk != null) {
                silk.baseAmount++;
                silk.amount = silk.baseAmount;
            }
        }
    }
}



