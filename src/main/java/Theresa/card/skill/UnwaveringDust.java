package Theresa.card.skill;

import Theresa.action.MakeTempCardInDustAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import Theresa.patch.SilkPatch;
import Theresa.silk.AbstractSilk;
import Theresa.silk.MindSilk;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UnwaveringDust extends AbstractTheresaCard {
    public static final String ID = "theresa:UnwaveringDust";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public UnwaveringDust() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.NONE);
        exhaust = true;
        MindSilk ms = new MindSilk();
        ms.baseAmount = ms.amount = 2;
        SilkPatch.setSilkWithoutTrigger(this,ms);
        tags.add(OtherEnum.Theresa_Silk_Cannot_Replaced);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainEnergyAction(1));
        addToBot(new MakeTempCardInDustAction(this,1,true).setOverMake().notToHand());
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





