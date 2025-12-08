package Theresa.card.skill;

import Theresa.action.IncreaseMaxDustAction;
import Theresa.action.SetNewCostAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import Theresa.power.buff.SilkPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhenItIsBye extends AbstractTheresaCard {
    public static final String ID = "theresa:WhenItIsBye";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public WhenItIsBye() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        this.tags.add(OtherEnum.Theresa_Darkness);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new IncreaseMaxDustAction(-1));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new SilkPower(abstractPlayer,1),1));
        addToBot(new SetNewCostAction(this,1));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            this.isEthereal = true;
        }
    }
}







